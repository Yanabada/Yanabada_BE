package kr.co.fastcampus.yanabada.domain.member.dto.response;

import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberSummaryResponse(
    Long id,
    String nickname,
    String imageUrl
) {

    public static MemberSummaryResponse from(Member member) {
        return MemberSummaryResponse.builder()
            .id(member.getId())
            .nickname(member.getNickName())
            .imageUrl(member.getImageUrl())
            .build();
    }
}
