package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.YANOLJAPAY_HISTORY_NOT_FOUND;

public class YanoljaPayHistoryNotFoundException extends BaseException {

    public YanoljaPayHistoryNotFoundException() {
        super(YANOLJAPAY_HISTORY_NOT_FOUND.getMessage());
    }
}
