package com.mrbt.orbit.budget.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.budget.api.request.CreateBudgetRequest;
import com.mrbt.orbit.budget.api.response.BudgetItemResponse;
import com.mrbt.orbit.budget.api.response.BudgetResponse;
import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;

@Component
public class BudgetDtoMapper extends GenericDtoMapper<CreateBudgetRequest, BudgetResponse, Budget> {

	@Override
	public Budget toDomain(CreateBudgetRequest request) {
		if (request == null) {
			return null;
		}
		List<BudgetItem> items = request.items() != null
				? request.items().stream()
						.map(r -> (BudgetItem) BudgetItem.builder().categoryId(r.categoryId())
								.allocatedAmount(r.allocatedAmount()).alertThresholdPct(r.alertThresholdPct()).build())
						.toList()
				: List.of();

		return Budget.builder().userId(request.userId()).name(request.name()).periodType(request.periodType())
				.startDate(request.startDate()).endDate(request.endDate()).items(items).build();
	}

	@Override
	public BudgetResponse toResponse(Budget domain) {
		if (domain == null) {
			return null;
		}
		List<BudgetItemResponse> items = domain.getItems() != null
				? domain.getItems().stream().map(this::itemToResponse).toList()
				: List.of();

		return BudgetResponse.builder().id(domain.getId()).userId(domain.getUserId()).name(domain.getName())
				.periodType(EnumUtils.toStringOrNull(domain.getPeriodType())).startDate(domain.getStartDate())
				.endDate(domain.getEndDate()).totalAmount(domain.getTotalAmount())
				.status(EnumUtils.toStringOrNull(domain.getStatus())).items(items).createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt()).build();
	}

	public BudgetItemResponse itemToResponse(BudgetItem item) {
		if (item == null) {
			return null;
		}
		return BudgetItemResponse.builder().id(item.getId()).categoryId(item.getCategoryId())
				.allocatedAmount(item.getAllocatedAmount()).spentAmount(item.getSpentAmount())
				.alertThresholdPct(item.getAlertThresholdPct()).build();
	}

}
