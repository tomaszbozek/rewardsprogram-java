package com.tbo.demos.rewardsprogram.retail.rewards.rest;

import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PaymentServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.PurchaseServiceImpl;
import com.tbo.demos.rewardsprogram.retail.rewards.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected PurchaseServiceImpl purchaseService;
	@MockBean
	protected TransactionServiceImpl transactionService;
	@MockBean
	protected PaymentServiceImpl paymentService;

	@BeforeEach
	public void setUp() {
		Mockito.reset(purchaseService, transactionService, paymentService);
	}

}
