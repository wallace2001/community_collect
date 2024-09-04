package com.collect.community_collect.controller;

import com.collect.community_collect.domain.CommunityCenter;
import com.collect.community_collect.domain.ExchangeHistory;
import com.collect.community_collect.request.ExchangeRequest;
import com.collect.community_collect.service.CommunityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community-centers")
public class CommunityCenterController {

    @Autowired
    private CommunityCenterService communityCenterService;

    @PostMapping
    public ResponseEntity<CommunityCenter> addCommunityCenter(@RequestBody CommunityCenter center) {
        return ResponseEntity.ok(communityCenterService.addCommunityCenter(center));
    }

    @PutMapping("/{id}/occupancy")
    public ResponseEntity<CommunityCenter> updateOccupancy(@PathVariable String id, @RequestParam int occupancy) {
        return ResponseEntity.ok(communityCenterService.updateOccupancy(id, occupancy));
    }

    @GetMapping("/high-occupancy")
    public ResponseEntity<List<CommunityCenter>> getHighOccupancyCenters(@RequestParam int threshold) {
        return ResponseEntity.ok(communityCenterService.getHighOccupancyCenters(threshold));
    }

    @PostMapping("/exchange")
    public ResponseEntity<ExchangeHistory> realizarIntercambio(@RequestBody ExchangeRequest request) {
        try {
            ExchangeHistory exchangeHistory = communityCenterService.exchangeResources(
                    request.getFromCenterId(),
                    request.getToCenterId(),
                    request.getResourcesSent(),
                    request.getResourcesReceived()
            );
            return ResponseEntity.ok(exchangeHistory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
