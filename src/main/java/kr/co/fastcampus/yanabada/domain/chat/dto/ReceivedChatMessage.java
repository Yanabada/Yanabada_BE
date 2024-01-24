package kr.co.fastcampus.yanabada.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;

public record ReceivedChatMessage(
    @Size(min = 18, max = 18, message = "유효하지 않은 채팅방 코드입니다.")
    @Pattern(regexp = "^[0-9a-f]+$", message = "유효하지 않은 채팅방 코드입니다.")
    String chatRoomCode,
    @NotNull(message = "전송자 ID는 필수로 입력하셔야 합니다.")
    @Positive(message = "전송자 ID는 양수이어야 합니다.")
    Long senderId,
    @NotBlank(message = "메세지 내용이 없습니다.")
    @Size(max = 255, message = "메세지 내용은 255자를 넘을 수 없습니다.")
    String content
) {

    public ChatMessage toEntity(
        ChatRoom chatRoom,
        Member sender,
        LocalDateTime sendDateTime
    ) {
        return ChatMessage.create(
            chatRoom,
            sender,
            content,
            sendDateTime
        );
    }
}
