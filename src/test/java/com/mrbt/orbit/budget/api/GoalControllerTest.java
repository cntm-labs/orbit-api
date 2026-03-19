package com.mrbt.orbit.budget.api;

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
import com.mrbt.orbit.budget.api.mapper.GoalDtoMapper;
import com.mrbt.orbit.budget.api.request.ContributeGoalRequest;
import com.mrbt.orbit.budget.api.request.CreateGoalRequest;
import com.mrbt.orbit.budget.api.request.UpdateGoalRequest;
import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.in.ContributeGoalUseCase;
import com.mrbt.orbit.budget.core.port.in.CreateGoalUseCase;
import com.mrbt.orbit.budget.core.port.in.DeleteGoalUseCase;
import com.mrbt.orbit.budget.core.port.in.GetGoalUseCase;
import com.mrbt.orbit.budget.core.port.in.UpdateGoalUseCase;
import com.mrbt.orbit.common.core.model.PageResult;

@WebMvcTest(GoalController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GoalDtoMapper.class)
class GoalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@MockitoBean
	private CreateGoalUseCase createGoalUseCase;

	@MockitoBean
	private GetGoalUseCase getGoalUseCase;

	@MockitoBean
	private ContributeGoalUseCase contributeGoalUseCase;

	@MockitoBean
	private UpdateGoalUseCase updateGoalUseCase;

	@MockitoBean
	private DeleteGoalUseCase deleteGoalUseCase;

	@Test
	void createGoal_returns201() throws Exception {
		UUID goalId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		when(createGoalUseCase.createGoal(any(Goal.class))).thenAnswer(inv -> {
			Goal g = inv.getArgument(0);
			g.setId(goalId);
			g.setStatus(GoalStatus.IN_PROGRESS);
			g.setCurrentAmount(BigDecimal.ZERO);
			return g;
		});

		CreateGoalRequest request = CreateGoalRequest.builder().userId(userId).name("Vacation Fund")
				.targetAmount(new BigDecimal("5000")).targetDate(LocalDate.of(2026, 12, 31)).build();

		mockMvc.perform(post("/api/v1/goals").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Vacation Fund"));
	}

	@Test
	void getGoalById_returns200() throws Exception {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Car Fund")
				.targetAmount(new BigDecimal("20000")).currentAmount(new BigDecimal("5000"))
				.status(GoalStatus.IN_PROGRESS).build();

		when(getGoalUseCase.getGoalById(goalId)).thenReturn(Optional.of(goal));

		mockMvc.perform(get("/api/v1/goals/{goalId}", goalId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(goalId.toString()))
				.andExpect(jsonPath("$.data.name").value("Car Fund"));
	}

	@Test
	void getGoalById_returns404WhenNotFound() throws Exception {
		UUID goalId = UUID.randomUUID();
		when(getGoalUseCase.getGoalById(goalId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/goals/{goalId}", goalId)).andExpect(status().isNotFound());
	}

	@Test
	void getGoalsByUserId_returnsPage() throws Exception {
		UUID userId = UUID.randomUUID();
		Goal goal = Goal.builder().id(UUID.randomUUID()).userId(userId).name("Savings")
				.targetAmount(new BigDecimal("1000")).currentAmount(BigDecimal.ZERO).status(GoalStatus.IN_PROGRESS)
				.build();
		PageResult<Goal> page = new PageResult<>(List.of(goal), 1L, 1, 0, 20);

		when(getGoalUseCase.getGoalsByUserId(eq(userId), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get("/api/v1/goals/user/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.content").isArray())
				.andExpect(jsonPath("$.data.totalElements").value(1)).andExpect(jsonPath("$.data.page").value(0));
	}

	@Test
	void contribute_returns200() throws Exception {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Vacation")
				.targetAmount(new BigDecimal("5000")).currentAmount(new BigDecimal("550"))
				.status(GoalStatus.IN_PROGRESS).build();

		when(contributeGoalUseCase.contribute(eq(goalId), any(BigDecimal.class))).thenReturn(goal);

		ContributeGoalRequest request = ContributeGoalRequest.builder().amount(new BigDecimal("50")).build();

		mockMvc.perform(patch("/api/v1/goals/{goalId}/contribute", goalId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Vacation"));
	}

	@Test
	void updateGoal_returns200() throws Exception {
		UUID goalId = UUID.randomUUID();
		Goal updated = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Updated Goal")
				.targetAmount(new BigDecimal("5000")).currentAmount(BigDecimal.ZERO).status(GoalStatus.IN_PROGRESS)
				.build();

		when(updateGoalUseCase.updateGoal(eq(goalId), eq("Updated Goal"), isNull(), isNull())).thenReturn(updated);

		UpdateGoalRequest request = UpdateGoalRequest.builder().name("Updated Goal").build();

		mockMvc.perform(patch("/api/v1/goals/{goalId}", goalId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Updated Goal"));
	}

	@Test
	void deleteGoal_returns200() throws Exception {
		UUID goalId = UUID.randomUUID();

		mockMvc.perform(delete("/api/v1/goals/{goalId}", goalId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

}
