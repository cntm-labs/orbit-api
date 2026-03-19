package com.mrbt.orbit.ledger.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.api.request.CreateTransactionRequest;
import com.mrbt.orbit.ledger.api.request.UpdateTransactionRequest;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import com.mrbt.orbit.ledger.core.port.in.CreateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.DeleteTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateTransactionUseCase;
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

	@MockitoBean
	private UpdateTransactionUseCase updateTransactionUseCase;

	@MockitoBean
	private DeleteTransactionUseCase deleteTransactionUseCase;

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
	void getTransactionsByAccountId_returnsPage() throws Exception {
		UUID accountId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(UUID.randomUUID()).accountId(accountId).amount(new BigDecimal("10"))
				.status(TransactionStatus.COMPLETED).build();
		PageResult<Transaction> page = new PageResult<>(List.of(tx), 1L, 1, 0, 20);

		when(getTransactionUseCase.getTransactionsByAccountId(eq(accountId), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get("/api/v1/transactions/account/{accountId}", accountId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.content").isArray())
				.andExpect(jsonPath("$.data.totalElements").value(1)).andExpect(jsonPath("$.data.page").value(0))
				.andExpect(jsonPath("$.data.size").value(20));
	}

	@Test
	void getTransactionsByAccountId_capsSizeAt100() throws Exception {
		UUID accountId = UUID.randomUUID();
		PageResult<Transaction> page = new PageResult<>(List.of(), 0L, 0, 0, 100);

		when(getTransactionUseCase.getTransactionsByAccountId(eq(accountId), eq(0), eq(100))).thenReturn(page);

		mockMvc.perform(get("/api/v1/transactions/account/{accountId}?size=999", accountId)).andExpect(status().isOk());
	}

	@Test
	void updateTransaction_returns200() throws Exception {
		UUID txId = UUID.randomUUID();
		Transaction updated = Transaction.builder().id(txId).accountId(UUID.randomUUID()).amount(new BigDecimal("50"))
				.description("Updated Desc").status(TransactionStatus.COMPLETED).isReviewed(false).build();

		when(updateTransactionUseCase.updateTransaction(eq(txId), eq("Updated Desc"), isNull(), isNull()))
				.thenReturn(updated);

		UpdateTransactionRequest request = UpdateTransactionRequest.builder().description("Updated Desc").build();

		mockMvc.perform(patch("/api/v1/transactions/{transactionId}", txId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.description").value("Updated Desc"));
	}

	@Test
	void deleteTransaction_returns200() throws Exception {
		UUID txId = UUID.randomUUID();

		mockMvc.perform(delete("/api/v1/transactions/{transactionId}", txId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

}
