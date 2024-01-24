package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.NOTIFICATION_NOT_FOUND;

public class NotificationNotFoundException extends BaseException {
    public NotificationNotFoundException() {
        super(NOTIFICATION_NOT_FOUND.getMessage());
    }
}
