package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ChatMessagePageResponse(
    String chatRoomCode,
    List<ChatMessageInfoResponse> messages,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static ChatMessagePageResponse from(
        String chatRoomCode, Page<ChatMessageInfoResponse> responsePage
    ) {
        return ChatMessagePageResponse.builder()
            .chatRoomCode(chatRoomCode)
            .messages(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();

    }
}
