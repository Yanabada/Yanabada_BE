package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.DUPLICATE_YANOLJAPAY;

public class DuplicateYanoljaPayException extends BaseException {
    public DuplicateYanoljaPayException() {
        super(DUPLICATE_YANOLJAPAY.getMessage());
    }
}
