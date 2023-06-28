package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PurchaseServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionServiceImplTest {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private PurchaseServiceImpl purchaseService;

	@Test
	void should_get_list_of_purchase_transactions_when_purchase_was_saved() {
		// given
		String purchaseId = UUID.randomUUID().toString();
		PurchaseRequestDto purchase = purchaseService.purchase(new PurchaseRequest(
			purchaseId,
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			120
		));
		// when
		List<TransactionDto> transactions = new TransactionServiceImpl(transactionRepository)
			.getPurchaseTransactions(purchaseId);
		// then
		assertThat(transactions).isNotNull().isNotEmpty().hasSize(1);
		assertThat(transactions.get(0).value()).isEqualTo(purchase.value());
		assertThat(transactions.get(0).status()).isEqualTo(Transaction.Status.PENDING);
	}
}
