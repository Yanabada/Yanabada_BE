package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TOKEN_CANNOT_BE_EMPTY;

public class TokenCannotBeEmptyException extends BaseException {
    public TokenCannotBeEmptyException() {
        super(TOKEN_CANNOT_BE_EMPTY.getMessage());
    }
}
