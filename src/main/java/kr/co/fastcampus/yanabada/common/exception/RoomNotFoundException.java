package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class RoomNotFoundException extends BaseException {
    public RoomNotFoundException() {
        super(ErrorCode.ROOM_NOT_FOUND.getErrorMsg());
    }
}
