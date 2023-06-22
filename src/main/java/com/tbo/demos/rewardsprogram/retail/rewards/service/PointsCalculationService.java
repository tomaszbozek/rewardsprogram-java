package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Point;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PointsCalculationService {

    public Point calculate(List<Transaction> transactions) {
        Objects.requireNonNull(transactions, "transactions == null");
        if(transactions.isEmpty()){
            throw new IllegalArgumentException("transaction list is empty");
        }
        long points = 0L;
        for(Transaction transaction : transactions){
            points = calculate(transaction);
        }
        return new Point(UUID.randomUUID(), points);
    }

    private static long calculate(Transaction transaction) {
        int points = 0;
        int transactionSum = transaction.value();
        if(transactionSum > 100){
            int overTheUpperLimit = transactionSum - 100;
            points += overTheUpperLimit * 2L;
            transactionSum -= overTheUpperLimit;
        }
        if(transactionSum > 50){
            points += (transactionSum - 50);
        }
        return points;
    }
}
