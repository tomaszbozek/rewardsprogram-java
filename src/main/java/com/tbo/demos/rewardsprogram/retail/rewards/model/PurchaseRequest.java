package com.tbo.demos.rewardsprogram.retail.rewards.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record PurchaseRequest(@Id String id, String userId, String itemId, Integer value) {

	public Transaction createTransaction() {
		return new Transaction(UUID.randomUUID().toString(), this.id, UUID.randomUUID().toString(), this.value(),
			Transaction.Status.PENDING);
	}
}
