package com.collect.community_collect.service;

import com.collect.community_collect.domain.CommunityCenter;
import com.collect.community_collect.domain.ExchangeHistory;
import com.collect.community_collect.domain.Resource;

import java.util.List;

public interface CommunityCenterService {
    CommunityCenter addCommunityCenter(CommunityCenter center);
    CommunityCenter updateOccupancy(String centerId, int newOccupancy);
    List<CommunityCenter> getHighOccupancyCenters(int threshold);
    ExchangeHistory exchangeResources(String fromCenterId, String toCenterId, List<Resource> resourcesSent, List<Resource> resourcesReceived);
}
