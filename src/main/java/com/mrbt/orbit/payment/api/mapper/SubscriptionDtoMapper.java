package com.mrbt.orbit.payment.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.payment.api.request.CreateSubscriptionRequest;
import com.mrbt.orbit.payment.api.response.SubscriptionResponse;
import com.mrbt.orbit.payment.core.model.Subscription;

@Component
public class SubscriptionDtoMapper
		extends
			GenericDtoMapper<CreateSubscriptionRequest, SubscriptionResponse, Subscription> {

	@Override
	public Subscription toDomain(CreateSubscriptionRequest request) {
		if (request == null) {
			return null;
		}
		return Subscription.builder().userId(request.userId()).accountId(request.accountId())
				.categoryId(request.categoryId()).paymentMethodId(request.paymentMethodId()).name(request.name())
				.amount(request.amount()).currencyCode(request.currencyCode()).billingCycle(request.billingCycle())
				.nextBillingDate(request.nextBillingDate()).reminderDaysBefore(request.reminderDaysBefore())
				.trialEndDate(request.trialEndDate()).build();
	}

	@Override
	public SubscriptionResponse toResponse(Subscription domain) {
		if (domain == null) {
			return null;
		}
		return SubscriptionResponse.builder().id(domain.getId()).userId(domain.getUserId())
				.categoryId(domain.getCategoryId()).paymentMethodId(domain.getPaymentMethodId())
				.accountId(domain.getAccountId()).name(domain.getName()).amount(domain.getAmount())
				.currencyCode(domain.getCurrencyCode()).billingCycle(EnumUtils.toStringOrNull(domain.getBillingCycle()))
				.nextBillingDate(domain.getNextBillingDate()).reminderDaysBefore(domain.getReminderDaysBefore())
				.status(EnumUtils.toStringOrNull(domain.getStatus())).trialEndDate(domain.getTrialEndDate())
				.createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();
	}

}
