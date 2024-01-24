package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.INVALID_STATUS_PRODUCT_UPDATE;

public class InvalidStatusProductUpdateException extends BaseException {
    public InvalidStatusProductUpdateException() {
        super(INVALID_STATUS_PRODUCT_UPDATE.getMessage());
    }
}
