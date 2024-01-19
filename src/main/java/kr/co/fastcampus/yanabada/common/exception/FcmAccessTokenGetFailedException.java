package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.FCM_ACCESS_TOKEN_GET_FAILED;

public class FcmAccessTokenGetFailedException extends BaseException {
    public FcmAccessTokenGetFailedException() {
        super(FCM_ACCESS_TOKEN_GET_FAILED.getMessage());
    }
}
