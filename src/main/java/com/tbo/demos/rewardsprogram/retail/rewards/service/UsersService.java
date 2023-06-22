package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.model.User;

import java.util.UUID;

public class UsersService {

    private final PurchaseService purchaseService;

    public UsersService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    void purchase(PurchaseDto purchaseDto){
        User loggedUser = new User(UUID.randomUUID());
        PurchaseRequest purchaseRequest = new PurchaseRequest(UUID.randomUUID(), loggedUser.id(), UUID.randomUUID(),
                purchaseDto.value());
        purchaseService.purchase(purchaseRequest);
    }
}
