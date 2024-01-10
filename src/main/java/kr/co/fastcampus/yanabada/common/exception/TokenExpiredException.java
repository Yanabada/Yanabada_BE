package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TOKEN_EXPIRED;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(TOKEN_EXPIRED.getMessage());
    }
}
