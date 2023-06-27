package com.tbo.demos.rewardsprogram.retail.rewards.repository;

import com.tbo.demos.rewardsprogram.retail.rewards.model.UserPoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserPointsRepositoryTest {

	@Autowired
	UserPointsRepository userPointsRepository;

	@Test
	void should_throw_optimistic_locking_failure_exception_when_entity_with_same_version_is_being_saved() {
		// given
		UserPoints userPointsOne = new UserPoints(UUID.randomUUID().toString(), null,
			UUID.randomUUID().toString(), 10L);
		userPointsRepository.save(userPointsOne);
		UserPoints userPointsTwo = userPointsRepository.findById(userPointsOne.id()).orElseThrow();
		userPointsRepository.save(userPointsTwo);

		// when & then
		assertThatThrownBy(() -> userPointsRepository.save(userPointsTwo))
			.isInstanceOf(OptimisticLockingFailureException.class);
	}
}
