package com.tbo.demos.rewardsprogram.retail.rewards.model;


import org.springframework.data.annotation.Id;

public record Points(@Id String id, String transactionId, Long value) {
}
