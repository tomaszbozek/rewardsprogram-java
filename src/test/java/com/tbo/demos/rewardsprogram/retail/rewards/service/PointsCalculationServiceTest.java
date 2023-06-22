package com.tbo.demos.rewardsprogram.retail.rewards.service;

import com.tbo.demos.rewardsprogram.retail.rewards.model.Point;
import com.tbo.demos.rewardsprogram.retail.rewards.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PointsCalculationServiceTest {

    @Test
    void should_return_2points_for_each_dolar_when_transaction_is_over_100_dolars(){
        // given
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 101, Transaction.Status.PENDING);

        // when
        Point point = new PointsCalculationService().calculate(List.of(transaction));

        // then
        assertThat(point.value()).isEqualTo(52L);
    }

    @Test
    void should_return_1point_for_each_dolar_when_transaction_over_50_dolars(){
        // given
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),  51, Transaction.Status.PENDING);

        // when
        Point point = new PointsCalculationService().calculate(List.of(transaction));

        // then
        assertThat(point.value()).isEqualTo(1L);
    }

    @Test
    void should_return_90points_for_120_dolar_purchase(){
        // given
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 120, Transaction.Status.PENDING);

        // when
        Point point = new PointsCalculationService().calculate(List.of(transaction));

        // then
        assertThat(point.value()).isEqualTo(90L);
    }

    @Test
    void should_return_proper_points_for_multiple_transactions(){
        // given
        Transaction transactionOne = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 120, Transaction.Status.PENDING);
        Transaction transactionTwo = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),  30, Transaction.Status.PENDING);
        Transaction transactionThree = new Transaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),  70, Transaction.Status.PENDING);

        // when
        Point point = new PointsCalculationService().calculate(List.of(transactionOne, transactionTwo, transactionThree));

        // (20 * 2 + 50 * 1) + (0*2 + 0 * 1) + (20 * 1)
        // then
        assertThat(point.value()).isEqualTo(110L);
    }
}