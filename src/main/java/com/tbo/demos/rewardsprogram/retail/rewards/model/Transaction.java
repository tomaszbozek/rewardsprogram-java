package com.tbo.demos.rewardsprogram.retail.rewards.model;

import org.springframework.data.annotation.Id;

public record Transaction(@Id String id, String purchaseId, String paymentMethodId, Integer value, Status status) {

	public Transaction markAsPaid() {
		return new Transaction(
			this.id,
			this.purchaseId,
			this.paymentMethodId,
			this.value,
			Status.PAID
		);
	}

	public Transaction markAsError() {
		return new Transaction(
			this.id,
			this.purchaseId,
			this.paymentMethodId,
			this.value,
			Status.ERROR
		);
	}

    public enum Status {
        PENDING,
        PAID,
        ERROR
    }
}
