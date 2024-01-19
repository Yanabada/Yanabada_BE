package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.EMAIL_DUPLICATED;

public class EmailDuplicatedException extends BaseException {
    public EmailDuplicatedException() {
        super(EMAIL_DUPLICATED.getMessage());
    }
}
