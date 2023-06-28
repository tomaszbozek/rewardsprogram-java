package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PointsRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PaymentServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PointsCalculationServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentServiceImplTest {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private PointsRepository pointsRepository;
	@Autowired
	private PointsCalculationServiceImpl pointsCalculationService;
	@Autowired
	private PurchaseServiceImpl purchaseService;
	@Autowired
	private PaymentServiceImpl paymentService;

	@Disabled("This won't work untill replica set is enabled.")
	void should_mark_transaction_as_paid_when_purchase_is_available() {
		// given
		String purchaseId = UUID.randomUUID().toString();
		PurchaseRequestDto purchase = purchaseService.purchase(new PurchaseRequest(
			purchaseId,
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			120
		));
		List<Transaction> transactions = transactionRepository.findAllByPurchaseId(purchaseId);
		// when
		TransactionDto transactionDto = paymentService.markTransactionAsPaid(transactions.get(0).purchaseId(),
			transactions.get(0).id());

		// then
		assertThat(transactionDto).isNotNull();
		assertThat(transactionDto.status()).isEqualTo(Transaction.Status.PAID);
	}
}
