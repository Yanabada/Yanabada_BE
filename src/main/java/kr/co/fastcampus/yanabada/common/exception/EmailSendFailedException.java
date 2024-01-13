package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.EMAIL_SEND_FAILED;

public class EmailSendFailedException extends BaseException {
    public EmailSendFailedException() {
        super(EMAIL_SEND_FAILED.getMessage());
    }
}
