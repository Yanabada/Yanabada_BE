package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.FCM_MESSAGE_SEND_FAILED;

public class FcmMessageSendFailedException extends BaseException {
    public FcmMessageSendFailedException() {
        super(FCM_MESSAGE_SEND_FAILED.getMessage());
    }
}
