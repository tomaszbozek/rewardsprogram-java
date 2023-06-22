package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Point;
import com.tbo.demos.rewardsprogram.retail.rewards.model.PurchaseRequest;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.PurchaseRequestRepository;
import com.tbo.demos.rewardsprogram.retail.rewards.repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

public class PurchaseService {

    public enum PaymentMethod {
        GOLD_AT_THE_END_OF_THE_RAINBOW
    }

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final TransactionRepository transactionRepository;

    public PurchaseService(PurchaseRequestRepository purchaseRequestRepository, TransactionRepository transactionRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.transactionRepository = transactionRepository;
    }

    public void purchase(PurchaseRequest purchaseRequest) {
        // save
        PurchaseRequest savedRequest = purchaseRequestRepository.save(purchaseRequest);
        Transaction savedTransaction = transactionRepository.save(savedRequest.createTransaction());
        // update transaction status
        tryWithTransaction(savedTransaction);
    }

    private void tryWithTransaction(Transaction savedTransaction) {
        try {
            UUID paymentMethodId = savedTransaction.paymentMethodId();
            PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
            tryToPay(savedTransaction, paymentMethod);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void tryToPay(Transaction savedTransaction, PaymentMethod paymentMethod) {
        pay(savedTransaction, paymentMethod);
    }

    private void pay(Transaction savedTransaction, PaymentMethod paymentMethod) {
        try {
            // payment to external system
            List<Transaction> transactions = List.of(transactionRepository.save(savedTransaction.markAsPaid()));
            Point points = new PointsCalculationService().calculate(transactions);
        }
        catch (Exception e){
            transactionRepository.save(savedTransaction.markAsError());
        }
    }

    private PaymentMethod getPaymentMethodById(UUID paymentMethodId) {
        return PaymentMethod.GOLD_AT_THE_END_OF_THE_RAINBOW;
    }
}
