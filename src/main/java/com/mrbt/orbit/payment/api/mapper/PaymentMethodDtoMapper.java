package com.mrbt.orbit.payment.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.payment.api.request.CreatePaymentMethodRequest;
import com.mrbt.orbit.payment.api.response.PaymentMethodResponse;
import com.mrbt.orbit.payment.core.model.PaymentMethod;

@Component
public class PaymentMethodDtoMapper
		extends
			GenericDtoMapper<CreatePaymentMethodRequest, PaymentMethodResponse, PaymentMethod> {

	@Override
	public PaymentMethod toDomain(CreatePaymentMethodRequest request) {
		if (request == null) {
			return null;
		}
		return PaymentMethod.builder().userId(request.userId()).provider(request.provider())
				.providerReferenceId(request.providerReferenceId()).lastFourDigits(request.lastFourDigits())
				.isDefault(request.isDefault()).build();
	}

	@Override
	public PaymentMethodResponse toResponse(PaymentMethod domain) {
		if (domain == null) {
			return null;
		}
		return PaymentMethodResponse.builder().id(domain.getId()).userId(domain.getUserId())
				.provider(EnumUtils.toStringOrNull(domain.getProvider()))
				.providerReferenceId(domain.getProviderReferenceId()).lastFourDigits(domain.getLastFourDigits())
				.isDefault(domain.getIsDefault()).status(EnumUtils.toStringOrNull(domain.getStatus()))
				.createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();
	}

}
