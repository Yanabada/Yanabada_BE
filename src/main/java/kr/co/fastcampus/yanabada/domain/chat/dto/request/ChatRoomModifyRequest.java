package kr.co.fastcampus.yanabada.domain.chat.dto.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChatRoomModifyRequest(
    @Size(min = 18, max = 18, message = "유효하지 않은 채팅방 코드입니다.")
    @Pattern(regexp = "^[0-9a-f]+$", message = "유효하지 않은 채팅방 코드입니다.")
    String chatRoomCode
) {
}
