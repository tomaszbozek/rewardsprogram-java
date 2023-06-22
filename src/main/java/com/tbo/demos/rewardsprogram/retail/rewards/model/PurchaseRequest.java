package com.tbo.demos.rewardsprogram.retail.rewards.model;

import java.util.UUID;

public record PurchaseRequest(UUID id, UUID userId, UUID itemId, Integer value) {

    public Transaction createTransaction(){
        return new Transaction(UUID.randomUUID(), this.id, this.value());
    }
}
