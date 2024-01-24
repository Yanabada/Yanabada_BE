package kr.co.fastcampus.yanabada.domain.chat.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.chat.dto.ReceivedChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomModifyRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessagePageResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomModifyResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomSummaryResponse;
import kr.co.fastcampus.yanabada.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    @Value("${chatroom.topic.prefix}")
    private String chatroomTopicPrefix;

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void message(ReceivedChatMessage message) {
        System.out.println("컨트롤러message = " + message);
        messagingTemplate.convertAndSend(
            chatroomTopicPrefix + message.chatRoomCode(),
            chatService.saveChatMessage(message)
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
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseBody.ok(chatService.getChatRooms(principalDetails.id()));
    }

    @GetMapping("/{chatRoomCode}")
    public ResponseBody<ChatMessagePageResponse> getChatRoom(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("chatRoomCode") String chatRoomCode,
        @PageableDefault(size = 20, sort = "sendDateTime", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseBody.ok(
            chatService.getChatRoomMessages(principalDetails.id(), chatRoomCode, pageable)
        );
    }

    @PutMapping
    public ResponseBody<ChatRoomModifyResponse> modifyChatRoom(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ChatRoomModifyRequest request
    ) {
        return ResponseBody.ok(chatService.updateChatRoom(principalDetails.id(), request));
    }

    @DeleteMapping
    public ResponseBody<ChatRoomModifyResponse> modifyOrDeleteChatRoom(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ChatRoomModifyRequest request
    ) {
        return ResponseBody.ok(chatService.modifyOrDeleteChatRoom(principalDetails.id(), request));
    }
}
