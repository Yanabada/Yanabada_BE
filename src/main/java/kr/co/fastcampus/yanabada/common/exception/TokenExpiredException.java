package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TOKEN_EXPIRED;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(TOKEN_EXPIRED.getMessage());
    }
}
