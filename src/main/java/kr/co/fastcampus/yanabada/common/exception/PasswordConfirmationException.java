package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.PASSWORD_CONFIRMATION_DOES_NOT_MATCH;

public class PasswordConfirmationException extends BaseException {
    public PasswordConfirmationException() {
        super(PASSWORD_CONFIRMATION_DOES_NOT_MATCH.getMessage());
    }
}
