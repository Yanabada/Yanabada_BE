package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.NOT_ENOUGH_POINT;

public class NotEnoughPointException extends BaseException {
    public NotEnoughPointException() {
        super(NOT_ENOUGH_POINT.getMessage());
    }
}
