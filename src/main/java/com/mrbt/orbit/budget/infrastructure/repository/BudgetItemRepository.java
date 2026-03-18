package com.mrbt.orbit.budget.infrastructure.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mrbt.orbit.budget.infrastructure.entity.BudgetItemEntity;

public interface BudgetItemRepository extends JpaRepository<BudgetItemEntity, UUID> {

	@Query("SELECT bi FROM BudgetItemEntity bi JOIN bi.budget b " + "WHERE bi.categoryId = :categoryId "
			+ "AND b.startDate <= :date AND b.endDate >= :date "
			+ "AND b.status = com.mrbt.orbit.budget.core.model.enums.BudgetStatus.ACTIVE")
	List<BudgetItemEntity> findActiveByCategoryIdAndDate(@Param("categoryId") UUID categoryId,
			@Param("date") LocalDate date);

	@Modifying
	@Query("UPDATE BudgetItemEntity bi SET bi.spentAmount = bi.spentAmount + :amount WHERE bi.id = :id")
	int updateSpentAmountAtomically(@Param("id") UUID id, @Param("amount") BigDecimal amount);

}
