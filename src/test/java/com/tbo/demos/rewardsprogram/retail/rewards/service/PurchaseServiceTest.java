package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PurchaseRequestRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


class PurchaseServiceTest {

    @Autowired
    private PurchaseRequestRepository purchaseRequestRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void purchase(){
        new PurchaseService(purchaseRequestRepository, transactionRepository).purchase(new PurchaseRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(), 2
        ));
    }
}