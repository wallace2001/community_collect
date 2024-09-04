package com.collect.community_collect.repository;

import com.collect.community_collect.domain.ExchangeHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ExchangeHistoryRepository extends MongoRepository<ExchangeHistory, String> {
    List<ExchangeHistory> findByFromCenterIdAndDateBetween(String centerId, Date start, Date end);
}