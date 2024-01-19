package kr.co.fastcampus.yanabada.domain.notification.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;

public record ChatNotificationDto(
    Member sender,
    Member receiver,
    String accommodationName,
    String chatRoomCode,
    String image
) {

    public String convertMapToJsonStr(ObjectMapper objectMapper) {
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("accommodationName", accommodationName);
        contentMap.put("chatRoomCode", chatRoomCode);
        try {
            return objectMapper.writeValueAsString(contentMap);
        } catch (JsonProcessingException e) {
            throw new JsonProcessFailedException();
        }
    }

}