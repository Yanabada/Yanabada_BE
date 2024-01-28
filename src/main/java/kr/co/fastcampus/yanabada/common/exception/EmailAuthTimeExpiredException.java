package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.EMAIL_AUTH_TIME_EXPIRED;

public class EmailAuthTimeExpiredException extends BaseException {
    public EmailAuthTimeExpiredException() {
        super(EMAIL_AUTH_TIME_EXPIRED.getMessage());
    }
}
