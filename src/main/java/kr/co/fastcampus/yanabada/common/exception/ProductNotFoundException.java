package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.PRODUCT_NOT_FOUND;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException() {
        super(PRODUCT_NOT_FOUND.getMessage());
    }
}
