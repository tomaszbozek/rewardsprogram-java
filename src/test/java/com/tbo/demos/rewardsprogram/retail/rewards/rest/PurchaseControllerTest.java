package com.tbo.demos.rewardsprogram.retail.rewards.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.NewPurchaseDto;
import com.tbo.demos.rewardsprogram.retail.rewards.dto.PurchaseRequestDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PurchaseControllerTest extends AbstractControllerTest {

	private static PurchaseRequestDto createPurchase(String userId, String itemId, Integer value) {
		return new PurchaseRequestDto(userId, itemId, value);
	}

	@Test
	void should_return_purchase_when_purchase_was_saved() throws Exception {
		// given
		String userId = UUID.randomUUID().toString();
		String itemId = UUID.randomUUID().toString();
		PurchaseRequestDto post = createPurchase(userId, itemId, 120);

		// when
		when(purchaseService.getPurchase("1")).thenReturn(post);

		// then
		mockMvc.perform(get("/purchases/1").accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$.value", is(120)))
			.andExpect(jsonPath("$.userId", is(userId)))
			.andExpect(jsonPath("$.itemId", is(itemId)));
	}

	@Test
	void should_return_not_found_when_purchase_is_not_present_by_id() throws Exception {
		// given
		String userId = UUID.randomUUID().toString();
		String itemId = UUID.randomUUID().toString();
		PurchaseRequestDto post = createPurchase(userId, itemId, 130);

		// when
		when(purchaseService.getPurchase("2")).thenReturn(post);

		// then
		mockMvc.perform(get("/purchases/1").accept(APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void should_return_list_with_one_purchase_when_one_purchases_is_available() throws Exception {
		// given
		String userId = UUID.randomUUID().toString();
		String itemId = UUID.randomUUID().toString();
		PurchaseRequestDto purchaseRequestDto = createPurchase(userId, itemId, 120);

		// when
		when(purchaseService.getPurchases()).thenReturn(List.of(
			purchaseRequestDto
		));

		// then
		mockMvc.perform(get("/purchases").accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$[0].value", is(120)))
			.andExpect(jsonPath("$[0].userId", is(userId)))
			.andExpect(jsonPath("$[0].itemId", is(itemId)));
	}

	@Test
	void should_save_purchase_and_return_save_purchase_when_one_purchases_is_available() throws Exception {
		// given
		String userId = UUID.randomUUID().toString();
		String itemId = UUID.randomUUID().toString();
		PurchaseRequestDto purchaseRequestDto = createPurchase(userId, itemId, 120);
		NewPurchaseDto purchaseRequest = new NewPurchaseDto(
			userId,
			itemId,
			120
		);

		// when
		when(purchaseService.purchase(any())).thenReturn(purchaseRequestDto);

		// then
		mockMvc.perform(post("/purchases")
				.content(new ObjectMapper().writeValueAsString(purchaseRequest))
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$.value", is(120)))
			.andExpect(jsonPath("$.userId", is(userId)))
			.andExpect(jsonPath("$.itemId", is(itemId)))
			.andExpect(status().isCreated());
	}
}
