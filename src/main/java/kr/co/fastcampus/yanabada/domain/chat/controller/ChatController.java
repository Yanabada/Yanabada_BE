package kr.co.fastcampus.yanabada.domain.chat.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessageInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomSummaryResponse;
import kr.co.fastcampus.yanabada.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseBody<ChatRoomInfoResponse> getOrAddChatRoom(
        @RequestBody ChatRoomSaveRequest request
    ) {
        return ResponseBody.ok(chatService.getOrSaveChatRoom(request));
    }

    @GetMapping
    public ResponseBody<List<ChatRoomSummaryResponse>> getChatRooms(
    //Principal 을 통해 로그인한 멤버 id를 가져올 예정
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.getChatRooms(memberId));
    }

    @GetMapping("/{chatRoomCode}")
    public ResponseBody<List<ChatMessageInfoResponse>> getChatRoom(
    //Principal 을 통해 로그인한 멤버 id를 가져올 예정
    @PathVariable("chatRoomCode") String chatRoomCode
    ) {
        Long memberId = 1L;
        return ResponseBody.ok(chatService.getChatRoomMessages(memberId, chatRoomCode));
    }
}
