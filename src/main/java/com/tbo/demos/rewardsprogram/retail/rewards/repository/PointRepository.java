package com.tbo.demos.rewardsprogram.retail.rewards.repository;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Point;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointRepository extends MongoRepository<Point, String> {
}