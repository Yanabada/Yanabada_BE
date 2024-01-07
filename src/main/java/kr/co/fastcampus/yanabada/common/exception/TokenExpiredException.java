package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED.getMessage());
    }
}
