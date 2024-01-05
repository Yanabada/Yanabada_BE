package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class AccessForbiddenException extends BaseException {
    public AccessForbiddenException() {
        super(ErrorCode.ACCESS_FORBIDDEN.getMessage());
    }
}
