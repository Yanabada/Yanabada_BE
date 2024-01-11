package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import lombok.Builder;

@Builder
public record ChatRoomIdResponse(
    Long chatRoomId
) {

    public static ChatRoomIdResponse from(ChatRoom chatRoom) {
        return ChatRoomIdResponse.builder()
            .chatRoomId(chatRoom.getId())
            .build();
    }
}
