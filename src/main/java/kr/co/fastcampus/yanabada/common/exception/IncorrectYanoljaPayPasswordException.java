package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.INCORRECT_YANOLJAPAY_PASSWORD;

public class IncorrectYanoljaPayPasswordException extends BaseException {
    public IncorrectYanoljaPayPasswordException() {
        super(INCORRECT_YANOLJAPAY_PASSWORD.getMessage());
    }
}
