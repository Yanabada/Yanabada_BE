package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.OKHTTP3_REQUEST_FAILED;

public class OkHttp3RequestFailedException extends BaseException {
    public OkHttp3RequestFailedException() {
        super(OKHTTP3_REQUEST_FAILED.getMessage());
    }
}
