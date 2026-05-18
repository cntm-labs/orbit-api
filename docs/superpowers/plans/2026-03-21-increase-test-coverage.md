# Increase Test Coverage Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Increase test coverage to 80%+ for AuditLogController, UserProfileController, and their associated mappers.

**Architecture:** Use JUnit 5 for testing, Mockito for mocking dependencies, and MockMvc for controller testing. Use AssertJ for fluent assertions.

**Tech Stack:** Java 25, Spring Boot 4.0.6, JUnit 5, Mockito, AssertJ, JaCoCo.

---

### Task 1: Create AuditLogDtoMapperTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/audit/api/mapper/AuditLogDtoMapperTest.java`

- [ ] **Step 1: Write the test code**

```java
package com.mrbt.orbit.audit.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.audit.api.response.AuditLogResponse;
import com.mrbt.orbit.audit.core.model.AuditLog;

class AuditLogDtoMapperTest {

    private final AuditLogDtoMapper mapper = new AuditLogDtoMapper();

    @Test
    void toResponse_mapsAllFields() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID entityId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        AuditLog domain = AuditLog.builder()
                .id(id)
                .userId(userId)
                .action("CREATE")
                .entityType("TRANSACTION")
                .entityId(entityId)
                .changesJson("{\"amount\": 100}")
                .ipAddress("127.0.0.1")
                .createdAt(now)
                .build();

        AuditLogResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.action()).isEqualTo("CREATE");
        assertThat(response.entityType()).isEqualTo("TRANSACTION");
        assertThat(response.entityId()).isEqualTo(entityId);
        assertThat(response.changesJson()).isEqualTo("{\"amount\": 100}");
        assertThat(response.ipAddress()).isEqualTo("127.0.0.1");
        assertThat(response.createdAt()).isEqualTo(now);
    }

    @Test
    void toResponse_returnsNullForNull() {
        assertThat(mapper.toResponse(null)).isNull();
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=AuditLogDtoMapperTest`
Expected: PASS

### Task 2: Create CryptoDtoMapperTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/crypto/api/mapper/CryptoDtoMapperTest.java`

- [ ] **Step 1: Write the test code**

```java
package com.mrbt.orbit.crypto.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.crypto.api.response.CryptoAssetResponse;
import com.mrbt.orbit.crypto.api.response.CryptoPortfolioSnapshotResponse;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;

class CryptoDtoMapperTest {

    private final CryptoDtoMapper mapper = new CryptoDtoMapper();

    @Test
    void toResponse_mapsCryptoAsset() {
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        CryptoAsset domain = CryptoAsset.builder()
                .id(id)
                .symbol("BTC")
                .name("Bitcoin")
                .coingeckoId("bitcoin")
                .currentPriceUsd(new BigDecimal("50000.00"))
                .marketCapRank(1)
                .updatedAt(now)
                .build();

        CryptoAssetResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.symbol()).isEqualTo("BTC");
        assertThat(response.name()).isEqualTo("Bitcoin");
        assertThat(response.coingeckoId()).isEqualTo("bitcoin");
        assertThat(response.currentPriceUsd()).isEqualTo(new BigDecimal("50000.00"));
        assertThat(response.marketCapRank()).isEqualTo(1);
        assertThat(response.updatedAt()).isEqualTo(now);
    }

    @Test
    void toResponse_mapsCryptoPortfolioSnapshot() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        OffsetDateTime now = OffsetDateTime.now();

        CryptoPortfolioSnapshot domain = CryptoPortfolioSnapshot.builder()
                .id(id)
                .userId(userId)
                .snapshotDate(today)
                .totalValueUsd(new BigDecimal("10000.00"))
                .holdingsJson("[]")
                .createdAt(now)
                .build();

        CryptoPortfolioSnapshotResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.snapshotDate()).isEqualTo(today);
        assertThat(response.totalValueUsd()).isEqualTo(new BigDecimal("10000.00"));
        assertThat(response.holdingsJson()).isEqualTo("[]");
        assertThat(response.createdAt()).isEqualTo(now);
    }

    @Test
    void toResponse_returnsNullForNull() {
        assertThat(mapper.toResponse((CryptoAsset) null)).isNull();
        assertThat(mapper.toResponse((CryptoPortfolioSnapshot) null)).isNull();
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=CryptoDtoMapperTest`
Expected: PASS

### Task 3: Create IntegrationDtoMapperTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/integration/api/mapper/IntegrationDtoMapperTest.java`

- [ ] **Step 1: Write the test code**

```java
package com.mrbt.orbit.integration.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.integration.api.response.ExchangeRateResponse;
import com.mrbt.orbit.integration.api.response.PlaidLinkResponse;
import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.model.PlaidLink;

class IntegrationDtoMapperTest {

    private final IntegrationDtoMapper mapper = new IntegrationDtoMapper();

    @Test
    void toResponse_mapsExchangeRate() {
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        ExchangeRate domain = ExchangeRate.builder()
                .id(id)
                .baseCurrency("USD")
                .targetCurrency("EUR")
                .rate(new BigDecimal("0.85"))
                .source("ECB")
                .fetchedAt(now)
                .build();

        ExchangeRateResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.baseCurrency()).isEqualTo("USD");
        assertThat(response.targetCurrency()).isEqualTo("EUR");
        assertThat(response.rate()).isEqualTo(new BigDecimal("0.85"));
        assertThat(response.source()).isEqualTo("ECB");
        assertThat(response.fetchedAt()).isEqualTo(now);
    }

    @Test
    void toResponse_mapsPlaidLink() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        PlaidLink domain = PlaidLink.builder()
                .id(id)
                .userId(userId)
                .institutionName("Chase")
                .status("CONNECTED")
                .errorCode(null)
                .build();

        PlaidLinkResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.institutionName()).isEqualTo("Chase");
        assertThat(response.status()).isEqualTo("CONNECTED");
        assertThat(response.errorCode()).isNull();
    }

    @Test
    void toResponse_returnsNullForNull() {
        assertThat(mapper.toResponse((ExchangeRate) null)).isNull();
        assertThat(mapper.toResponse((PlaidLink) null)).isNull();
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=IntegrationDtoMapperTest`
Expected: PASS

### Task 4: Create UserProfileDtoMapperTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/security/api/mapper/UserProfileDtoMapperTest.java`

- [ ] **Step 1: Write the test code**

```java
package com.mrbt.orbit.security.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.api.request.NotificationPreferenceRequest;
import com.mrbt.orbit.security.api.request.UserAddressRequest;
import com.mrbt.orbit.security.api.request.UserPreferenceRequest;
import com.mrbt.orbit.security.api.response.NotificationPreferenceResponse;
import com.mrbt.orbit.security.api.response.UserAddressResponse;
import com.mrbt.orbit.security.api.response.UserPreferenceResponse;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;

class UserProfileDtoMapperTest {

    private final UserProfileDtoMapper mapper = new UserProfileDtoMapper();

    @Test
    void toDomain_mapsUserAddressRequest() {
        UUID userId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        UserAddressRequest request = new UserAddressRequest(id, "Home", "123 Main St", null, "City", "State", "12345", "US", true);

        UserAddress domain = mapper.toDomain(userId, request);

        assertThat(domain.getUserId()).isEqualTo(userId);
        assertThat(domain.getId()).isEqualTo(id);
        assertThat(domain.getLabel()).isEqualTo("Home");
        assertThat(domain.getAddressLine1()).isEqualTo("123 Main St");
        assertThat(domain.getCity()).isEqualTo("City");
        assertThat(domain.getIsDefault()).isTrue();
    }

    @Test
    void toResponse_mapsUserAddress() {
        UUID id = UUID.randomUUID();
        UserAddress domain = UserAddress.builder()
                .id(id)
                .label("Home")
                .addressLine1("123 Main St")
                .city("City")
                .isDefault(true)
                .build();

        UserAddressResponse response = mapper.toResponse(domain);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.label()).isEqualTo("Home");
        assertThat(response.addressLine1()).isEqualTo("123 Main St");
        assertThat(response.isDefault()).isTrue();
    }

    @Test
    void toDomain_mapsUserPreferenceRequest() {
        UUID userId = UUID.randomUUID();
        UserPreferenceRequest request = new UserPreferenceRequest(true, "en", "YYYY-MM-DD", "COMMA", true, true, true, true);

        UserPreference domain = mapper.toDomain(userId, request);

        assertThat(domain.getUserId()).isEqualTo(userId);
        assertThat(domain.getDarkMode()).isTrue();
        assertThat(domain.getLanguage()).isEqualTo("en");
    }

    @Test
    void toResponse_mapsUserPreference() {
        UserPreference domain = UserPreference.builder()
                .darkMode(true)
                .language("en")
                .dateFormat("YYYY-MM-DD")
                .numberFormat("COMMA")
                .biometricEnabled(true)
                .build();

        UserPreferenceResponse response = mapper.toResponse(domain);

        assertThat(response.darkMode()).isTrue();
        assertThat(response.language()).isEqualTo("en");
    }

    @Test
    void toDomain_mapsNotificationPreferenceRequest() {
        UUID userId = UUID.randomUUID();
        NotificationPreferenceRequest request = new NotificationPreferenceRequest(true, true, true, true, new BigDecimal("1000.00"));

        NotificationPreference domain = mapper.toDomain(userId, request);

        assertThat(domain.getUserId()).isEqualTo(userId);
        assertThat(domain.getEmailEnabled()).isTrue();
        assertThat(domain.getLargeTransactionThreshold()).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void toResponse_mapsNotificationPreference() {
        NotificationPreference domain = NotificationPreference.builder()
                .emailEnabled(true)
                .pushEnabled(true)
                .budgetAlerts(true)
                .largeTransactionThreshold(new BigDecimal("1000.00"))
                .build();

        NotificationPreferenceResponse response = mapper.toResponse(domain);

        assertThat(response.emailEnabled()).isTrue();
        assertThat(response.largeTransactionThreshold()).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void toDomain_returnsNullForNull() {
        assertThat(mapper.toDomain(UUID.randomUUID(), (UserAddressRequest) null)).isNull();
        assertThat(mapper.toDomain(UUID.randomUUID(), (UserPreferenceRequest) null)).isNull();
        assertThat(mapper.toDomain(UUID.randomUUID(), (NotificationPreferenceRequest) null)).isNull();
    }

    @Test
    void toResponse_returnsNullForNull() {
        assertThat(mapper.toResponse((UserAddress) null)).isNull();
        assertThat(mapper.toResponse((UserPreference) null)).isNull();
        assertThat(mapper.toResponse((NotificationPreference) null)).isNull();
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=UserProfileDtoMapperTest`
Expected: PASS

### Task 5: Create AuditLogControllerTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/audit/api/AuditLogControllerTest.java`

- [ ] **Step 1: Write the test code**

```java
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mrbt.orbit.audit.api.mapper.AuditLogDtoMapper;
import com.mrbt.orbit.audit.api.response.AuditLogResponse;
import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.port.in.AuditLogUseCase;

@WebMvcTest(AuditLogController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuditLogUseCase auditLogUseCase;

    @MockitoBean
    private AuditLogDtoMapper mapper;

    @Test
    void getLogsByUser_returnsLogs() throws Exception {
        UUID userId = UUID.randomUUID();
        AuditLog log = AuditLog.builder().id(UUID.randomUUID()).userId(userId).action("LOGIN").build();
        Page<AuditLog> page = new PageImpl<>(List.of(log));
        
        when(auditLogUseCase.getAuditLogsByUser(eq(userId), any(Pageable.class))).thenReturn(page);
        
        AuditLogResponse response = new AuditLogResponse(log.getId(), userId, "LOGIN", null, null, null, null, null);
        when(mapper.toResponse(any(AuditLog.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/audit-logs/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].action").value("LOGIN"));
    }

    @Test
    void getLogsByEntity_returnsLogs() throws Exception {
        UUID entityId = UUID.randomUUID();
        AuditLog log = AuditLog.builder().id(UUID.randomUUID()).entityType("ACCOUNT").entityId(entityId).action("UPDATE").build();
        Page<AuditLog> page = new PageImpl<>(List.of(log));
        
        when(auditLogUseCase.getAuditLogsByEntity(eq("ACCOUNT"), eq(entityId), any(Pageable.class))).thenReturn(page);
        
        AuditLogResponse response = new AuditLogResponse(log.getId(), null, "UPDATE", "ACCOUNT", entityId, null, null, null);
        when(mapper.toResponse(any(AuditLog.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/audit-logs/entity/ACCOUNT/{entityId}", entityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].action").value("UPDATE"));
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=AuditLogControllerTest`
Expected: PASS

### Task 6: Create UserProfileControllerTest

**Files:**
- Create: `src/test/java/com/mrbt/orbit/security/api/UserProfileControllerTest.java`

- [ ] **Step 1: Write the test code**

```java
package com.mrbt.orbit.security.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.security.api.mapper.UserProfileDtoMapper;
import com.mrbt.orbit.security.api.request.UserAddressRequest;
import com.mrbt.orbit.security.api.request.UserPreferenceRequest;
import com.mrbt.orbit.security.api.response.UserAddressResponse;
import com.mrbt.orbit.security.api.response.UserPreferenceResponse;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.port.in.UserProfileUseCase;

@WebMvcTest(UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserProfileUseCase userProfileUseCase;

    @MockitoBean
    private UserProfileDtoMapper mapper;

    @Test
    void addAddress_returnsAddress() throws Exception {
        UUID userId = UUID.randomUUID();
        UserAddressRequest request = new UserAddressRequest(null, "Home", "123 St", null, "City", "ST", "12345", "US", true);
        UserAddress address = UserAddress.builder().id(UUID.randomUUID()).label("Home").build();
        
        when(mapper.toDomain(eq(userId), any(UserAddressRequest.class))).thenReturn(address);
        when(userProfileUseCase.addAddress(any(UserAddress.class))).thenReturn(address);
        when(mapper.toResponse(any(UserAddress.class))).thenReturn(new UserAddressResponse(address.getId(), "Home", "123 St", null, "City", "ST", "12345", "US", true));

        mockMvc.perform(post("/api/v1/users/{userId}/profile/addresses", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.label").value("Home"));
    }

    @Test
    void getAddresses_returnsList() throws Exception {
        UUID userId = UUID.randomUUID();
        UserAddress address = UserAddress.builder().id(UUID.randomUUID()).label("Home").build();
        
        when(userProfileUseCase.getUserAddresses(userId)).thenReturn(List.of(address));
        when(mapper.toResponse(any(UserAddress.class))).thenReturn(new UserAddressResponse(address.getId(), "Home", null, null, null, null, null, null, true));

        mockMvc.perform(get("/api/v1/users/{userId}/profile/addresses", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].label").value("Home"));
    }

    @Test
    void updateAddress_returnsUpdated() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UserAddressRequest request = new UserAddressRequest(null, "Work", "456 St", null, "City", "ST", "12345", "US", false);
        UserAddress address = UserAddress.builder().id(addressId).label("Work").build();
        
        when(mapper.toDomain(eq(userId), any(UserAddressRequest.class))).thenReturn(address);
        when(userProfileUseCase.updateAddress(any(UserAddress.class))).thenReturn(address);
        when(mapper.toResponse(any(UserAddress.class))).thenReturn(new UserAddressResponse(addressId, "Work", "456 St", null, "City", "ST", "12345", "US", false));

        mockMvc.perform(put("/api/v1/users/{userId}/profile/addresses/{addressId}", userId, addressId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.label").value("Work"));
    }

    @Test
    void removeAddress_returnsSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/users/{userId}/profile/addresses/{addressId}", userId, addressId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(userProfileUseCase).removeAddress(addressId);
    }

    @Test
    void getPreferences_returnsPreferences() throws Exception {
        UUID userId = UUID.randomUUID();
        UserPreference pref = UserPreference.builder().darkMode(true).build();
        
        when(userProfileUseCase.getPreferences(userId)).thenReturn(pref);
        when(mapper.toResponse(any(UserPreference.class))).thenReturn(new UserPreferenceResponse(true, "en", "YMD", "COMMA", true, true, true, true));

        mockMvc.perform(get("/api/v1/users/{userId}/profile/preferences", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.darkMode").value(true));
    }

    @Test
    void updatePreferences_returnsUpdated() throws Exception {
        UUID userId = UUID.randomUUID();
        UserPreferenceRequest request = new UserPreferenceRequest(false, "th", "DMY", "DOT", false, false, false, false);
        UserPreference pref = UserPreference.builder().darkMode(false).build();
        
        when(mapper.toDomain(eq(userId), any(UserPreferenceRequest.class))).thenReturn(pref);
        when(userProfileUseCase.updatePreferences(any(UserPreference.class))).thenReturn(pref);
        when(mapper.toResponse(any(UserPreference.class))).thenReturn(new UserPreferenceResponse(false, "th", "DMY", "DOT", false, false, false, false));

        mockMvc.perform(put("/api/v1/users/{userId}/profile/preferences", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.darkMode").value(false));
    }
}
```

- [ ] **Step 2: Run the test**

Run: `./mvnw test -Dtest=UserProfileControllerTest`
Expected: PASS

### Task 7: Verify Overall Coverage

- [ ] **Step 1: Run all tests with JaCoCo**

Run: `./mvnw clean test jacoco:report`

- [ ] **Step 2: Check the report**

Expected: Overall coverage should be above 80%.

---
