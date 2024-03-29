package kr.co.fastcampus.yanabada.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record SendChatMessage(
    String chatRoomCode,
    Long senderId,
    String senderNickname,
    String senderProfileImage,
    String content,
    LocalDateTime sendTime
) {

    public static SendChatMessage from(
        ChatRoom chatRoom,
        Member sender,
        String content,
        LocalDateTime sendTime
    ) {
        return SendChatMessage.builder()
            .chatRoomCode(chatRoom.getCode())
            .senderId(sender.getId())
            .senderNickname(sender.getNickName())
            .senderProfileImage(sender.getImage())
            .content(content)
            .sendTime(sendTime)
            .build();
    }
}
