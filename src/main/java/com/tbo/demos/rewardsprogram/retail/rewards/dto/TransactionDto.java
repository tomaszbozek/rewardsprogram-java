package com.tbo.demos.rewardsprogram.retail.rewards.dto;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;

public record TransactionDto(Integer value, Transaction.Status status) {
}
