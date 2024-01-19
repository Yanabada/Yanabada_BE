package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.AdminPayment;
import lombok.Builder;

@Builder
public record AdminPaymentInfoResponse(
    Long accumulatedProductCount,
    Long accumulatedUsageAmount,
    Long accumulatedDiscountAmount
) {

    public static AdminPaymentInfoResponse from(
        long accumulatedProductCount, AdminPayment adminPayment
    ) {
        return AdminPaymentInfoResponse.builder()
            .accumulatedProductCount(accumulatedProductCount)
            .accumulatedUsageAmount(adminPayment.getAccumulatedUsageAmount())
            .accumulatedDiscountAmount(adminPayment.getAccumulatedDiscountAmount())
            .build();
    }
}
