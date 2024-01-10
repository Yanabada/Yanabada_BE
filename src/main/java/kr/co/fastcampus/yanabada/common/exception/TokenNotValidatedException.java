package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TOKEN_NOT_VALIDATED;

public class TokenNotValidatedException extends BaseException {
    public TokenNotValidatedException() {
        super(TOKEN_NOT_VALIDATED.getMessage());
    }
}
