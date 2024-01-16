package kr.co.fastcampus.yanabada.domain.chat.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.chat.dto.ReceivedChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomModifyRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessageInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomModifyResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomSummaryResponse;
import kr.co.fastcampus.yanabada.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void message(ReceivedChatMessage message) {
        messagingTemplate.convertAndSend(
            "/sub/chatroom/" + message.chatRoomCode(), chatService.saveChatMessage(message)
        );
    }

    @PostMapping
    public ResponseBody<ChatRoomInfoResponse> getOrAddChatRoom(
        @RequestBody ChatRoomSaveRequest request
    ) {
        return ResponseBody.ok(chatService.getOrSaveChatRoom(request));
    }

    @GetMapping
    public ResponseBody<List<ChatRoomSummaryResponse>> getChatRooms(
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.getChatRooms(memberId));
    }

    @GetMapping("/{chatRoomCode}")
    public ResponseBody<List<ChatMessageInfoResponse>> getChatRoom(
        @PathVariable("chatRoomCode") String chatRoomCode
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.getChatRoomMessages(memberId, chatRoomCode));
    }

    @PutMapping
    public ResponseBody<ChatRoomModifyResponse> modifyChatRoom(
        @RequestBody ChatRoomModifyRequest request
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.updateChatRoom(memberId, request));
    }

    @DeleteMapping
    public ResponseBody<ChatRoomModifyResponse> modifyOrDeleteChatRoom(
        @RequestBody ChatRoomModifyRequest request
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.modifyOrDeleteChatRoom(memberId, request));
    }
}
