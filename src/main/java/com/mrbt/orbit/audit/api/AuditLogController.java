package com.mrbt.orbit.audit.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.audit.api.mapper.AuditLogDtoMapper;
import com.mrbt.orbit.audit.api.response.AuditLogResponse;
import com.mrbt.orbit.audit.core.port.in.AuditLogUseCase;
import com.mrbt.orbit.common.api.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Endpoints for viewing system audit trails")
public class AuditLogController {

	private final AuditLogUseCase auditLogUseCase;
	private final AuditLogDtoMapper mapper;

	@GetMapping("/user/{userId}")
	@Operation(summary = "Get audit logs for a specific user")
	public ResponseEntity<ApiResponse<Page<AuditLogResponse>>> getLogsByUser(@PathVariable UUID userId,
			Pageable pageable) {
		Page<AuditLogResponse> logs = auditLogUseCase.getAuditLogsByUser(userId, pageable).map(mapper::toResponse);
		return ResponseEntity.ok(ApiResponse.success(logs));
	}

	@GetMapping("/entity/{entityType}/{entityId}")
	@Operation(summary = "Get audit logs for a specific entity")
	public ResponseEntity<ApiResponse<Page<AuditLogResponse>>> getLogsByEntity(@PathVariable String entityType,
			@PathVariable UUID entityId, Pageable pageable) {
		Page<AuditLogResponse> logs = auditLogUseCase.getAuditLogsByEntity(entityType, entityId, pageable)
				.map(mapper::toResponse);
		return ResponseEntity.ok(ApiResponse.success(logs));
	}
}
