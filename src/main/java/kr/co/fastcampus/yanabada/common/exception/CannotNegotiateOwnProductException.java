package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class CannotNegotiateOwnProductException extends BaseException {
    public CannotNegotiateOwnProductException() {
        super(ErrorCode.CANNOT_NEGOTIATE_OWN_PRODUCT.getMessage());
    }
}
