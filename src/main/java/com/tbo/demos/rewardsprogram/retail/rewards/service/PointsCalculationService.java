package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;

import java.util.List;

public interface PointsCalculationService {
	Long calculate(List<Transaction> transactions);

	long calculate(Transaction transaction);
}
