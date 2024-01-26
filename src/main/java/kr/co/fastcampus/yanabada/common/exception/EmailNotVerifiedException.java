package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.EMAIL_NOT_VERIFIED_EXCEPTION;

public class EmailNotVerifiedException extends BaseException {
    public EmailNotVerifiedException() {
        super(EMAIL_NOT_VERIFIED_EXCEPTION.getMessage());
    }
}
