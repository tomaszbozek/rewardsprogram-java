package com.tbo.demos.rewardsprogram.retail.rewards.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Just for demo purposes. Supports optimistic locking by the usage of a version property.
 *
 * @param id      of an entity
 * @param version of an entity - this can change either by manual alteration or automatically by the framework.
 * @param userId
 * @param value
 */
public record UserPoints(@Id String id, @Version Long version, String userId, Long value) {
}
