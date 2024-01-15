package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record ChatRoomModifyResponse(
    String chatRoomCode,
    Long updatedMemberId
) {

    public static ChatRoomModifyResponse create(String chatRoomCode, Long updatedMemberId) {
        return ChatRoomModifyResponse.builder()
            .chatRoomCode(chatRoomCode)
            .updatedMemberId(updatedMemberId)
            .build();
    }
}
