package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CANNOT_NEGOTIATE_OWN_PRODUCT;

public class CannotNegotiateOwnProductException extends BaseException {
    public CannotNegotiateOwnProductException() {
        super(CANNOT_NEGOTIATE_OWN_PRODUCT.getMessage());
    }
}
