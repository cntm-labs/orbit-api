package com.mrbt.orbit.ledger.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.ledger.api.request.CreateTransactionRequest;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import com.mrbt.orbit.ledger.core.port.in.CreateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetTransactionUseCase;
import com.mrbt.orbit.ledger.infrastructure.mapper.TransactionMapper;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TransactionMapper.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private CreateTransactionUseCase createTransactionUseCase;

	@MockitoBean
	private GetTransactionUseCase getTransactionUseCase;

	@Test
	void createTransaction_returns201() throws Exception {
		UUID txId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();

		when(createTransactionUseCase.createTransaction(any(Transaction.class))).thenAnswer(inv -> {
			Transaction t = inv.getArgument(0);
			t.setId(txId);
			t.setStatus(TransactionStatus.COMPLETED);
			t.setIsReviewed(false);
			t.setExchangeRate(BigDecimal.ONE);
			t.setTransactionDate(Instant.now());
			return t;
		});

		CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(accountId)
				.amount(new BigDecimal("50.00")).currencyCode("USD").description("Lunch").build();

		mockMvc.perform(post("/api/v1/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.description").value("Lunch"));
	}

	@Test
	void getTransactionById_returns200() throws Exception {
		UUID txId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(txId).accountId(UUID.randomUUID()).amount(new BigDecimal("42.00"))
				.status(TransactionStatus.COMPLETED).isReviewed(false).build();

		when(getTransactionUseCase.getTransactionById(txId)).thenReturn(Optional.of(tx));

		mockMvc.perform(get("/api/v1/transactions/{id}", txId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(txId.toString()));
	}

	@Test
	void getTransactionById_returns404WhenNotFound() throws Exception {
		UUID txId = UUID.randomUUID();
		when(getTransactionUseCase.getTransactionById(txId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/transactions/{id}", txId)).andExpect(status().isNotFound());
	}

	@Test
	void getTransactionsByAccountId_returns200() throws Exception {
		UUID accountId = UUID.randomUUID();
		List<Transaction> txs = List.of(Transaction.builder().id(UUID.randomUUID()).accountId(accountId)
				.amount(new BigDecimal("10")).status(TransactionStatus.COMPLETED).build());

		when(getTransactionUseCase.getTransactionsByAccountId(accountId)).thenReturn(txs);

		mockMvc.perform(get("/api/v1/transactions/account/{accountId}", accountId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray());
	}

}
