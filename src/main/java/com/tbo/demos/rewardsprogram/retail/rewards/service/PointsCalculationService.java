package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PointsCalculationService {
	@Value("${rewardsprogram.points.limit.first}")
	Integer firstLimit;
	@Value("${rewardsprogram.points.limit.second}")
	Integer secondLimit;

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

	private long calculate(Transaction transaction) {
		int points = 0;
		int transactionSum = transaction.value();
		if (transactionSum > secondLimit) {
			int overTheUpperLimit = transactionSum - secondLimit;
			points += overTheUpperLimit * 2L;
			transactionSum -= overTheUpperLimit;
		}
		if (transactionSum > firstLimit) {
			points += (transactionSum - firstLimit);
		}
		return points;
	}

	private long calculate1(Transaction transaction) {
		int points = 0;
		int transactionSum = transaction.value();
		Map<Integer, Integer> limits = Map.of(100, 2, 50, 1);
		for (Map.Entry<Integer, Integer> limit : limits.entrySet()) {
			int overTheUpperLimit = transactionSum - limit.getKey();
			points += overTheUpperLimit * limit.getValue();
			transactionSum -= overTheUpperLimit;
		}
		return points;
	}
}
