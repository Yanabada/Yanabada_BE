package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import lombok.Builder;

@Builder
public record ChatRoomInfoResponse(
    String chatRoomCode
) {

    public static ChatRoomInfoResponse from(ChatRoom chatRoom) {
        return ChatRoomInfoResponse.builder()
            .chatRoomCode(chatRoom.getCode())
            .build();
    }
}
