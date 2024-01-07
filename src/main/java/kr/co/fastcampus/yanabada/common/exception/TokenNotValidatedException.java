package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class TokenNotValidatedException extends BaseException {
    public TokenNotValidatedException() {
        super(ErrorCode.TOKEN_NOT_VALIDATED.getMessage());
    }
}
