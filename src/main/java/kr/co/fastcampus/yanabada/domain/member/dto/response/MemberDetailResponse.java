package kr.co.fastcampus.yanabada.domain.member.dto.response;

import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberDetailResponse(
    Long id,
    String email,
    String nickname,
    String phoneNumber,
    String imageUrl,
    Integer point
) {
    public static MemberDetailResponse from(Member member) {
        return MemberDetailResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickName())
            .phoneNumber(member.getPhoneNumber())
            .imageUrl(member.getImageUrl())
            .point(member.getPoint())
            .build();
    }
}