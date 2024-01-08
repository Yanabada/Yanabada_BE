package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ROOM_NOT_FOUND;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ROOM_NOT_FOUND;

public class RoomNotFoundException extends BaseException {
    public RoomNotFoundException() {
        super(ROOM_NOT_FOUND.getMessage());
    }
}
