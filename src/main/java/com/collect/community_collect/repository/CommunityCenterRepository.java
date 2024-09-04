package com.collect.community_collect.repository;

import com.collect.community_collect.domain.CommunityCenter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommunityCenterRepository extends MongoRepository<CommunityCenter, String> {
    List<CommunityCenter> findByCurrentOccupancyGreaterThan(int threshold);
}