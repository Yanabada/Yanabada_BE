package kr.co.fastcampus.yanabada.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record SignUpResponse(
    Long memberId
) {

    public static SignUpResponse from(Long id) {
        return SignUpResponse.builder()
            .memberId(id)
            .build();
    }
}
