package com.tbo.demos.rewardsprogram.retail.rewards.service.impl;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.service.PointsCalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PointsCalculationServiceImpl implements PointsCalculationService {
	List<Map.Entry<Integer, Integer>> rewardLimitsMapEntries;

	public PointsCalculationServiceImpl(@Value("#{${rewardsprogram.points.reward_limits_map}}")
										Map<Integer, Integer> rewardLimitsMap) {
		this.rewardLimitsMapEntries = new ArrayList<>(rewardLimitsMap.entrySet())
			.stream().sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey())).toList();
	}

	@Override
	public Long calculate(List<Transaction> transactions) {
		Objects.requireNonNull(transactions, "transactions == null");
		if (transactions.isEmpty()) {
			throw new IllegalArgumentException("transaction list is empty");
		}
		long points = 0L;
		for (Transaction transaction : transactions) {
			points += calculate(transaction);
		}
		return points;
	}

	@Override
	public long calculate(Transaction transaction) {
		int points = 0;
		int transactionSum = transaction.value();
		for (Map.Entry<Integer, Integer> limit : rewardLimitsMapEntries) {
			if (transactionSum > limit.getKey()) {
				int overTheUpperLimit = transactionSum - limit.getKey();
				points += overTheUpperLimit * limit.getValue();
				transactionSum -= overTheUpperLimit;
			}
		}
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.entrySet();
		return points;
	}
}
