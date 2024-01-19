package kr.co.fastcampus.yanabada.domain.member.dto.response;

import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import lombok.Builder;

@Builder
public record MemberDetailResponse(
    Long id,
    String email,
    String nickName,
    String phoneNumber,
    String imageUrl,
    Integer point,
    ProviderType provider
) {
    public static MemberDetailResponse from(Member member) {
        return MemberDetailResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .nickName(member.getNickName())
            .phoneNumber(member.getPhoneNumber())
            .imageUrl(member.getImageUrl())
            .point(member.getPoint())
            .provider(member.getProviderType())
            .build();
    }
}