package com.tbo.demos.rewardsprogram.retail.rewards.service.impl;


import com.tbo.demos.rewardsprogram.retail.rewards.dto.TransactionDto;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Points;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PointsRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	private final TransactionRepository transactionRepository;
	private final PointsRepository pointsRepository;

	private final PointsCalculationServiceImpl pointsCalculationService;

	public PaymentServiceImpl(TransactionRepository transactionRepository, PointsRepository pointsRepository, PointsCalculationServiceImpl pointsCalculationService) {
		this.transactionRepository = transactionRepository;
		this.pointsRepository = pointsRepository;
		this.pointsCalculationService = pointsCalculationService;
	}

	/**
	 * This is tricky part. Method responsible for updating transaction status.
	 * There are multiple choices:
	 * - denormalize schema as nosql suggests and keep one document with points and update actual points value there
	 * - add multiple documents transaction support as it's supported by MongoDb since 4.0
	 * - switch to relation db :)
	 * - switch to event driven architecture and e.g. kafka
	 * -
	 *
	 * @param purchaseId
	 * @param transactionId
	 * @return
	 */
	@Override
	@Transactional
	public TransactionDto markTransactionAsPaid(String purchaseId, String transactionId) {
		log.info("Pay transaction start; purchaseId={}; transactionId={}", purchaseId, transactionId);
		Transaction savedTransaction = transactionRepository.findById(transactionId)
			.orElseThrow(() ->
				new TransactionNotFoundException(String.format("Transaction with id=%s was not found.", transactionId)));
		try {
			log.debug("SavedTransaction={};", savedTransaction);
			// payment to external system
			log.info("Pay transaction success.");
			log.info("Save transaction into db.");
			savedTransaction = transactionRepository.save(savedTransaction.markAsPaid());
			List<Transaction> transactions = List.of(savedTransaction);
			log.info("List of saved transactions={}", transactions);
			Points points = new Points(UUID.randomUUID().toString(), savedTransaction.id(),
				pointsCalculationService.calculate(transactions));
			log.debug("Calculate points result={}", points);
			log.info("Save points into db.");
			pointsRepository.save(points);
		} catch (Exception e) {
			log.error("Exception during pay transaction: ", e);
			log.info("Pay transaction failure.");
			transactionRepository.save(savedTransaction.markAsError());
		}
		return new TransactionDto(savedTransaction.value(), savedTransaction.status());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	private static class TransactionNotFoundException extends RuntimeException {

		public TransactionNotFoundException(String message) {
			super(message);
		}
	}
}
