package com.collect.community_collect.service.impl;
import com.collect.community_collect.domain.CommunityCenter;
import com.collect.community_collect.domain.ExchangeHistory;
import com.collect.community_collect.domain.Resource;
import com.collect.community_collect.repository.CommunityCenterRepository;
import com.collect.community_collect.repository.ExchangeHistoryRepository;
import com.collect.community_collect.service.CommunityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityCenterServiceImpl implements CommunityCenterService {

    @Autowired
    private CommunityCenterRepository communityCenterRepository;

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;

    public CommunityCenter addCommunityCenter(CommunityCenter center) {
        return communityCenterRepository.save(center);
    }

    public CommunityCenter updateOccupancy(String centerId, int newOccupancy) {
        Optional<CommunityCenter> optionalCenter = communityCenterRepository.findById(centerId);
        if (optionalCenter.isPresent()) {
            CommunityCenter center = optionalCenter.get();
            center.setCurrentOccupancy(newOccupancy);
            if (newOccupancy >= center.getMaxCapacity()) {
                // Lógica para notificar sobre capacidade máxima atingida
            }
            return communityCenterRepository.save(center);
        }
        return null;
    }

    public List<CommunityCenter> getHighOccupancyCenters(int threshold) {
        return communityCenterRepository.findByCurrentOccupancyGreaterThan(threshold);
    }

    public ExchangeHistory exchangeResources(String fromCenterId, String toCenterId, List<Resource> resourcesSent, List<Resource> resourcesReceived) {
        // 1. Recuperar centros comunitários envolvidos
        Optional<CommunityCenter> fromCenterOptional = communityCenterRepository.findById(fromCenterId);
        Optional<CommunityCenter> toCenterOptional = communityCenterRepository.findById(toCenterId);

        if (!fromCenterOptional.isPresent() || !toCenterOptional.isPresent()) {
            throw new RuntimeException("Um ou ambos os centros comunitários não foram encontrados");
        }

        CommunityCenter fromCenter = fromCenterOptional.get();
        CommunityCenter toCenter = toCenterOptional.get();

        // 2. Calcular os pontos de ambos os recursos
        int totalPointsSent = resourcesSent.stream().mapToInt(Resource::getPoints).sum();
        int totalPointsReceived = resourcesReceived.stream().mapToInt(Resource::getPoints).sum();

        // 3. Verificar a ocupação dos centros e ajustar regras de intercâmbio
        boolean fromCenterOverloaded = fromCenter.getCurrentOccupancy() > 0.9 * fromCenter.getMaxCapacity();
        boolean toCenterOverloaded = toCenter.getCurrentOccupancy() > 0.9 * toCenter.getMaxCapacity();

        if (!fromCenterOverloaded && !toCenterOverloaded && totalPointsSent != totalPointsReceived) {
            throw new RuntimeException("Os pontos dos recursos enviados e recebidos devem ser iguais, a menos que um dos centros esteja sobrecarregado.");
        }

        // 4. Transferir recursos entre centros
        transferResources(fromCenter, toCenter, resourcesSent, resourcesReceived);

        // 5. Atualizar centros no repositório
        communityCenterRepository.save(fromCenter);
        communityCenterRepository.save(toCenter);

        // 6. Criar histórico de negociação
        ExchangeHistory exchangeHistory = new ExchangeHistory();
        exchangeHistory.setFromCenterId(fromCenterId);
        exchangeHistory.setToCenterId(toCenterId);
        exchangeHistory.setResourcesSent(resourcesSent);
        exchangeHistory.setResourcesReceived(resourcesReceived);
        exchangeHistory.setDate(new Date());

        return exchangeHistoryRepository.save(exchangeHistory);
    }

    private void transferResources(CommunityCenter fromCenter, CommunityCenter toCenter, List<Resource> resourcesSent, List<Resource> resourcesReceived) {
        // Remove os recursos enviados do centro que os enviou
        fromCenter.getResources().removeAll(resourcesSent);
        // Adiciona os recursos recebidos no centro que os enviou
        fromCenter.getResources().addAll(resourcesReceived);

        // Remove os recursos recebidos do centro que os enviou
        toCenter.getResources().removeAll(resourcesReceived);
        // Adiciona os recursos enviados no centro que os enviou
        toCenter.getResources().addAll(resourcesSent);
    }
}
