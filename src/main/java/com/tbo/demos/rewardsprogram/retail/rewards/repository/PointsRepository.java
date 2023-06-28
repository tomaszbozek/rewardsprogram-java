package com.tbo.demos.rewardsprogram.retail.rewards.repository;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Points;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointsRepository extends MongoRepository<Points, String> {
}