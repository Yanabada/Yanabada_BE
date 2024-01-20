package kr.co.fastcampus.yanabada.domain.notification.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ChatNotificationDto(
    Member sender,
    Member receiver,
    String accommodationName,
    String chatRoomCode,
    String senderNickname,
    String image
) {

    public static ChatNotificationDto from(
        Member sender,
        Member receiver,
        ChatRoom chatRoom
    ) {
        return ChatNotificationDto.builder()
            .sender(sender)
            .receiver(receiver)
            .accommodationName(
                chatRoom.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
            .image(sender.getImage())
            .build();
    }

    public String convertMapToJsonStr(ObjectMapper objectMapper) {
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("senderNickname", senderNickname);
        contentMap.put("accommodationName", accommodationName);
        contentMap.put("chatRoomCode", chatRoomCode);
        try {
            return objectMapper.writeValueAsString(contentMap);
        } catch (JsonProcessingException e) {
            throw new JsonProcessFailedException();
        }
    }

}