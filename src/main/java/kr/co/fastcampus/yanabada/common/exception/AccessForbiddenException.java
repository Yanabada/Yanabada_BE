package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ACCESS_FORBIDDEN;

public class AccessForbiddenException extends BaseException {
    public AccessForbiddenException() {
        super(ACCESS_FORBIDDEN.getMessage());
    }
}
