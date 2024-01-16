package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.PAY_NOT_FOUND;

public class YanoljaPayNotFoundException extends BaseException {

    public YanoljaPayNotFoundException() {
        super(PAY_NOT_FOUND.getMessage());
    }
}
