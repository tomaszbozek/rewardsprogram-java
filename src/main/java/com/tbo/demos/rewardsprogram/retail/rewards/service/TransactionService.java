package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
	List<TransactionDto> getPurchaseTransactions(String purchaseId);
}
