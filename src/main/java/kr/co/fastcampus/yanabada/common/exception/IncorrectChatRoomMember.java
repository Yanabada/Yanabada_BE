package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.INCORRECT_CHAT_ROOM_MEMBER;

public class IncorrectChatRoomMember extends BaseException {
    public IncorrectChatRoomMember() {
        super(INCORRECT_CHAT_ROOM_MEMBER.getMessage());
    }
}
