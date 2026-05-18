package com.mrbt.orbit.audit.infrastructure.adapter;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mrbt.orbit.audit.core.annotation.Auditable;
import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.port.in.AuditLogUseCase;
import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.security.core.service.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

	private final AuditLogUseCase auditLogUseCase;

	@AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
	public void auditAction(JoinPoint joinPoint, Auditable auditable, Object result) {
		try {
			UUID userId = getCurrentUserId();
			String ipAddress = getClientIp();

			UUID entityId = null;
			String entityType = auditable.entityType();

			if (result instanceof BaseDomainModel domainModel) {
				entityId = domainModel.getId();
				if (entityType.isEmpty()) {
					entityType = domainModel.getClass().getSimpleName().toUpperCase();
				}
			}

			AuditLog auditLog = AuditLog.builder().userId(userId).action(auditable.action()).entityType(entityType)
					.entityId(entityId).ipAddress(ipAddress).build();

			auditLogUseCase.logAction(auditLog);

		} catch (Exception e) {
			log.error("Failed to process audit log for method: {}", joinPoint.getSignature().getName(), e);
		}
	}

	private UUID getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof UserDetailsServiceImpl.InternalUserDetails userDetails) {
			return userDetails.getUserId();
		}
		return null;
	}

	private String getClientIp() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String xfHeader = request.getHeader("X-Forwarded-For");
			if (xfHeader == null) {
				return request.getRemoteAddr();
			}
			return xfHeader.split(",")[0];
		}
		return "unknown";
	}
}
