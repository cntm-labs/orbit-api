package com.mrbt.orbit.ledger.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import com.mrbt.orbit.ledger.api.request.CreateAccountRequest;
import com.mrbt.orbit.ledger.api.request.UpdateAccountRequest;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.in.CreateAccountUseCase;
import com.mrbt.orbit.ledger.core.port.in.DeleteAccountUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetAccountUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateAccountUseCase;
import com.mrbt.orbit.ledger.infrastructure.mapper.AccountMapper;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(AccountMapper.class)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private CreateAccountUseCase createAccountUseCase;

	@MockitoBean
	private GetAccountUseCase getAccountUseCase;

	@MockitoBean
	private UpdateAccountUseCase updateAccountUseCase;

	@MockitoBean
	private DeleteAccountUseCase deleteAccountUseCase;

	@Test
	void createAccount_returns201() throws Exception {
		UUID userId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();

		when(createAccountUseCase.createAccount(any(Account.class))).thenAnswer(inv -> {
			Account a = inv.getArgument(0);
			a.setId(accountId);
			a.setStatus(AccountStatus.ACTIVE);
			a.setCurrentBalance(BigDecimal.ZERO);
			return a;
		});

		CreateAccountRequest request = CreateAccountRequest.builder().userId(userId).name("Savings")
				.type(AccountType.BANK).currencyCode("USD").build();

		mockMvc.perform(post("/api/v1/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Savings"));
	}

	@Test
	void getAccountById_returns200() throws Exception {
		UUID accountId = UUID.randomUUID();
		Account account = Account.builder().id(accountId).name("Main").type(AccountType.BANK)
				.status(AccountStatus.ACTIVE).currentBalance(new BigDecimal("100")).build();

		when(getAccountUseCase.getAccountById(accountId)).thenReturn(Optional.of(account));

		mockMvc.perform(get("/api/v1/accounts/{id}", accountId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name").value("Main"));
	}

	@Test
	void getAccountById_returns404WhenNotFound() throws Exception {
		UUID accountId = UUID.randomUUID();
		when(getAccountUseCase.getAccountById(accountId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/accounts/{id}", accountId)).andExpect(status().isNotFound());
	}

	@Test
	void getAccountsByUserId_returns200() throws Exception {
		UUID userId = UUID.randomUUID();
		List<Account> accounts = List.of(Account.builder().name("A1").type(AccountType.BANK).build());

		when(getAccountUseCase.getAccountsByUserId(userId)).thenReturn(accounts);

		mockMvc.perform(get("/api/v1/accounts/user/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray()).andExpect(jsonPath("$.data[0].name").value("A1"));
	}

	@Test
	void updateAccount_returns200() throws Exception {
		UUID accountId = UUID.randomUUID();
		Account updated = Account.builder().id(accountId).name("New Name").type(AccountType.BANK)
				.status(AccountStatus.ACTIVE).currentBalance(BigDecimal.ZERO).build();

		when(updateAccountUseCase.updateAccount(eq(accountId), eq("New Name"))).thenReturn(updated);

		UpdateAccountRequest request = UpdateAccountRequest.builder().name("New Name").build();

		mockMvc.perform(patch("/api/v1/accounts/{accountId}", accountId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("New Name"));
	}

	@Test
	void deleteAccount_returns200() throws Exception {
		UUID accountId = UUID.randomUUID();

		mockMvc.perform(delete("/api/v1/accounts/{accountId}", accountId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

}
