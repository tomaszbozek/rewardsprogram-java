package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentService {
	@Transactional
	TransactionDto markTransactionAsPaid(String purchaseId, String transactionId);
}
