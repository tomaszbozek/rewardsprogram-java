package com.tbo.demos.rewardsprogram.retail.rewards.rest;

import com.tbo.demos.rewardsprogram.retail.rewards.dto.NewPurchaseDto;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PaymentServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PurchaseServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.TransactionServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Purchase Controller", tags = "Purchase")
@Controller
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

	private final PurchaseServiceImpl purchaseService;
	private final TransactionServiceImpl transactionService;
	private final PaymentServiceImpl paymentService;

	public PurchaseController(PurchaseServiceImpl purchaseService, TransactionServiceImpl transactionService, PaymentServiceImpl paymentService) {
		this.purchaseService = purchaseService;
		this.transactionService = transactionService;
		this.paymentService = paymentService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PurchaseRequestDto savePurchase(@RequestBody NewPurchaseDto newPurchaseDto) {
		return purchaseService.purchase(new PurchaseRequest(
			UUID.randomUUID().toString(),
			newPurchaseDto.userId(),
			newPurchaseDto.itemId(),
			newPurchaseDto.value()
		));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PurchaseRequestDto> getPurchases() {
		return purchaseService.getPurchases();
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PurchaseRequestDto getPurchase(@PathVariable String id) {
		return purchaseService.getPurchase(id);
	}

	@GetMapping(value = "/{id}/transactions")
	@ResponseStatus(HttpStatus.OK)
	public List<TransactionDto> getTransactions(@PathVariable String id) {
		return transactionService.getPurchaseTransactions(id);
	}

	@PostMapping(value = "/{id}/transactions/{transactionId}/paid")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDto markTransactionAsPaid(@PathVariable String id, @PathVariable String transactionId) {
		return paymentService.markTransactionAsPaid(id, transactionId);
	}
}
