package com.tbo.demos.rewardsprogram.retail.rewards.repository;

import com.tbo.demos.rewardsprogram.retail.rewards.model.UserPoints;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPointsRepository extends MongoRepository<UserPoints, String> {
}
