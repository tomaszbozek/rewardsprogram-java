package com.tbo.demos.rewardsprogram.retail.rewards.repository;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	List<Transaction> findAllByPurchaseId(String purchaseId);
}
