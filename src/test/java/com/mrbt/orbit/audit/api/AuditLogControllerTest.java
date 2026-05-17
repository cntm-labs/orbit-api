package com.mrbt.orbit.audit.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mrbt.orbit.audit.api.mapper.AuditLogDtoMapper;
import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.port.in.AuditLogUseCase;

@WebMvcTest(AuditLogController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(AuditLogDtoMapper.class)
class AuditLogControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuditLogUseCase auditLogUseCase;

	@Test
	void getLogsByUser_ShouldReturnLogs() throws Exception {
		UUID userId = UUID.randomUUID();
		AuditLog log = AuditLog.builder().id(UUID.randomUUID()).userId(userId).build();
		when(auditLogUseCase.getAuditLogsByUser(eq(userId), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(log)));

		mockMvc.perform(get("/api/v1/audit-logs/user/" + userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.content[0].userId").value(userId.toString()));
	}
}
