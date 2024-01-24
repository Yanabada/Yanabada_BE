package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.YANOLJAPAY_NOT_FOUND;

public class YanoljaPayNotFoundException extends BaseException {

    public YanoljaPayNotFoundException() {
        super(YANOLJAPAY_NOT_FOUND.getMessage());
    }
}
