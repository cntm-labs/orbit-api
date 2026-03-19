package com.mrbt.orbit.budget.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mrbt.orbit.budget.api.mapper.BudgetDtoMapper;
import com.mrbt.orbit.budget.api.request.CreateBudgetItemRequest;
import com.mrbt.orbit.budget.api.request.CreateBudgetRequest;
import com.mrbt.orbit.budget.api.request.UpdateBudgetRequest;
import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.budget.core.port.in.ArchiveBudgetUseCase;
import com.mrbt.orbit.budget.core.port.in.CreateBudgetUseCase;
import com.mrbt.orbit.budget.core.port.in.GetBudgetUseCase;
import com.mrbt.orbit.budget.core.port.in.UpdateBudgetUseCase;
import com.mrbt.orbit.common.core.model.PageResult;

@WebMvcTest(BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(BudgetDtoMapper.class)
class BudgetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@MockitoBean
	private CreateBudgetUseCase createBudgetUseCase;

	@MockitoBean
	private GetBudgetUseCase getBudgetUseCase;

	@MockitoBean
	private ArchiveBudgetUseCase archiveBudgetUseCase;

	@MockitoBean
	private UpdateBudgetUseCase updateBudgetUseCase;

	@Test
	void createBudget_returns201() throws Exception {
		UUID budgetId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		when(createBudgetUseCase.createBudget(any(Budget.class))).thenAnswer(inv -> {
			Budget b = inv.getArgument(0);
			b.setId(budgetId);
			b.setStatus(BudgetStatus.ACTIVE);
			b.setTotalAmount(new BigDecimal("300"));
			return b;
		});

		CreateBudgetRequest request = CreateBudgetRequest.builder().userId(userId).name("March Budget")
				.periodType(BudgetPeriodType.MONTHLY).startDate(LocalDate.of(2026, 3, 1))
				.endDate(LocalDate.of(2026, 3, 31)).items(List.of(CreateBudgetItemRequest.builder()
						.categoryId(UUID.randomUUID()).allocatedAmount(new BigDecimal("300")).build()))
				.build();

		mockMvc.perform(post("/api/v1/budgets").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("March Budget"));
	}

	@Test
	void getBudgetById_returns200() throws Exception {
		UUID budgetId = UUID.randomUUID();
		Budget budget = Budget.builder().id(budgetId).userId(UUID.randomUUID()).name("Monthly")
				.periodType(BudgetPeriodType.MONTHLY).startDate(LocalDate.of(2026, 3, 1))
				.endDate(LocalDate.of(2026, 3, 31)).totalAmount(new BigDecimal("500")).status(BudgetStatus.ACTIVE)
				.items(List.of()).build();

		when(getBudgetUseCase.getBudgetById(budgetId)).thenReturn(Optional.of(budget));

		mockMvc.perform(get("/api/v1/budgets/{budgetId}", budgetId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(budgetId.toString()))
				.andExpect(jsonPath("$.data.name").value("Monthly"));
	}

	@Test
	void getBudgetById_returns404WhenNotFound() throws Exception {
		UUID budgetId = UUID.randomUUID();
		when(getBudgetUseCase.getBudgetById(budgetId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/budgets/{budgetId}", budgetId)).andExpect(status().isNotFound());
	}

	@Test
	void getBudgetsByUserId_returnsPage() throws Exception {
		UUID userId = UUID.randomUUID();
		Budget budget = Budget.builder().id(UUID.randomUUID()).userId(userId).name("Monthly")
				.periodType(BudgetPeriodType.MONTHLY).status(BudgetStatus.ACTIVE).items(List.of()).build();
		PageResult<Budget> page = new PageResult<>(List.of(budget), 1L, 1, 0, 20);

		when(getBudgetUseCase.getBudgetsByUserId(eq(userId), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get("/api/v1/budgets/user/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.content").isArray())
				.andExpect(jsonPath("$.data.totalElements").value(1)).andExpect(jsonPath("$.data.page").value(0));
	}

	@Test
	void archiveBudget_returns200() throws Exception {
		UUID budgetId = UUID.randomUUID();

		mockMvc.perform(patch("/api/v1/budgets/{budgetId}/archive", budgetId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	void updateBudget_returns200() throws Exception {
		UUID budgetId = UUID.randomUUID();
		Budget updated = Budget.builder().id(budgetId).userId(UUID.randomUUID()).name("Updated Budget")
				.periodType(BudgetPeriodType.MONTHLY).startDate(LocalDate.of(2026, 3, 1))
				.endDate(LocalDate.of(2026, 3, 31)).totalAmount(new BigDecimal("500")).status(BudgetStatus.ACTIVE)
				.items(List.of()).build();

		when(updateBudgetUseCase.updateBudget(eq(budgetId), eq("Updated Budget"))).thenReturn(updated);

		UpdateBudgetRequest request = UpdateBudgetRequest.builder().name("Updated Budget").build();

		mockMvc.perform(patch("/api/v1/budgets/{budgetId}", budgetId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.name").value("Updated Budget"));
	}

	@Test
	void deleteBudget_returns200() throws Exception {
		UUID budgetId = UUID.randomUUID();

		mockMvc.perform(delete("/api/v1/budgets/{budgetId}", budgetId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

}
