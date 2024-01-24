package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import lombok.Builder;

@Builder
public record YanoljaPayHistoryIdResponse(
    Long yanoljaPayHistoryId
) {
    public static YanoljaPayHistoryIdResponse from(YanoljaPayHistory yanoljaPayHistory) {
        return YanoljaPayHistoryIdResponse.builder()
            .yanoljaPayHistoryId(yanoljaPayHistory.getId())
            .build();
    }
}
