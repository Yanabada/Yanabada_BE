package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import lombok.Builder;

@Builder
public record YanoljaPayIdResponse(
    Long yanoljaPayId
) {
    public static YanoljaPayIdResponse from(YanoljaPay yanoljaPay) {
        return YanoljaPayIdResponse.builder()
            .yanoljaPayId(yanoljaPay.getId())
            .build();
    }
}
