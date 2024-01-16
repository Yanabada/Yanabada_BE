package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ILLEGAL_PRODUCT_STATUS;

public class IllegalProductStatusException extends BaseException {
    public IllegalProductStatusException() {
        super(ILLEGAL_PRODUCT_STATUS.getMessage());
    }
}
