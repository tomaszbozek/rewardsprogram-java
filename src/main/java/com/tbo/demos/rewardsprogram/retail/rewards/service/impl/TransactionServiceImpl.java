package com.tbo.demos.rewardsprogram.retail.rewards.service.impl;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public List<TransactionDto> getPurchaseTransactions(String purchaseId) {
		Objects.requireNonNull(purchaseId, "purchaseId is null");
		return transactionRepository.findAllByPurchaseId(purchaseId).stream().map(transaction -> new TransactionDto(
			transaction.value(),
			transaction.status()
		)).toList();
	}
}
