package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;

import java.util.List;

public interface PurchaseService {
	List<PurchaseRequestDto> getPurchases();

	PurchaseRequestDto getPurchase(String id);

	PurchaseRequestDto purchase(PurchaseRequest purchaseRequest);
}
