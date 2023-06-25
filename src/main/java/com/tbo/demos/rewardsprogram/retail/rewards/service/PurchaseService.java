package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PointsRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PurchaseRequestRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PurchaseService {

	private final PurchaseRequestRepository purchaseRequestRepository;
	private final TransactionRepository transactionRepository;

	public PurchaseService(PurchaseRequestRepository purchaseRequestRepository, TransactionRepository transactionRepository, PointsRepository pointsRepository) {
		this.purchaseRequestRepository = purchaseRequestRepository;
		this.transactionRepository = transactionRepository;
	}

	public List<PurchaseRequestDto> getPurchases() {
		return purchaseRequestRepository.findAll().stream()
			.map(purchaseRequest -> new PurchaseRequestDto(purchaseRequest.userId(), purchaseRequest.itemId(),
				purchaseRequest.value())).toList();
	}

	public PurchaseRequestDto getPurchase(String id) {
		Objects.requireNonNull(id, "purchaseId is null");
		return purchaseRequestRepository.findById(id).map(purchaseRequest -> new PurchaseRequestDto(purchaseRequest.userId(),
				purchaseRequest.itemId(), purchaseRequest.value()))
			.orElseThrow(() -> new PurchaseNotFoundException(String.format("Purchase with id=%s was not found.", id)));
	}

	public PurchaseRequestDto purchase(PurchaseRequest purchaseRequest) {
		// save
		PurchaseRequest savedRequest = purchaseRequestRepository.save(purchaseRequest);
		Transaction savedTransaction = transactionRepository.save(savedRequest.createTransaction());
		return purchaseRequestRepository.findById(savedRequest.id())
			.map(foundPurchaseRequest -> new PurchaseRequestDto(foundPurchaseRequest.userId(),
				foundPurchaseRequest.itemId(), foundPurchaseRequest.value()))
			.orElseThrow(() -> new PurchaseNotFoundException(String.format("Purchase with id=%s was not found.",
				savedRequest.id())));
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	private static class PurchaseNotFoundException extends RuntimeException {
		public PurchaseNotFoundException(String message) {
			super(message);
		}
	}
}
