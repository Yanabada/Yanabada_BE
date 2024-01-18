package kr.co.fastcampus.yanabada.domain.auth.dto.response;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.domain.member.dto.response.MemberDetailResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record LoginResponse(
    TokenIssueResponse tokenIssue,
    MemberDetailResponse member
) {
    public static LoginResponse from(
        TokenIssueResponse tokenIssueResponse,
        Member member
    ) {
        return LoginResponse.builder()
            .tokenIssue(tokenIssueResponse)
            .member(MemberDetailResponse.from(member))
            .build();
    }
}
