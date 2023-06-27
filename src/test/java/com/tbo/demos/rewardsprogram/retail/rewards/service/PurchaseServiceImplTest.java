package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Points;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PointsRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PurchaseRequestRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PurchaseServiceImplTest {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private PointsRepository pointsRepository;
	@Autowired
	private PurchaseServiceImpl purchaseService;

	@Test
	void should_purchase_and_calculate_points_when_one_purchase_received() {
		// given
		PurchaseRequest purchaseRequest = new PurchaseRequest(
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(), 120
		);
		// when
		PurchaseRequestDto result = purchaseService.purchase(purchaseRequest);

		// then
		assertThat(result).isNotNull();
		assertThat(pointsRepository.findAll()).isEmpty();
	}

	@Test
	void should_purchase_and_calculate_points_when_multiple_purchases_received() {
		// given
		PurchaseRequest purchaseRequest1 = new PurchaseRequest(
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(), 120
		);
		PurchaseRequest purchaseRequest2 = new PurchaseRequest(
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(), 51
		);
		PurchaseRequest purchaseRequest3 = new PurchaseRequest(
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString(), 101
		);
		// when
		purchaseService.purchase(purchaseRequest1);
		purchaseService.purchase(purchaseRequest2);
		purchaseService.purchase(purchaseRequest3);

		// then
		List<Points> all = pointsRepository.findAll();
		assertThat((long) all.size()).isEqualTo(0);
	}
}
