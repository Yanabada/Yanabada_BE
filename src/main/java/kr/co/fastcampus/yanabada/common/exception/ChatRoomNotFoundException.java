package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CHAT_ROOM_NOT_FOUND_EXCEPTION;

public class ChatRoomNotFoundException extends BaseException {
    public ChatRoomNotFoundException() {
        super(CHAT_ROOM_NOT_FOUND_EXCEPTION.getMessage());
    }
}
