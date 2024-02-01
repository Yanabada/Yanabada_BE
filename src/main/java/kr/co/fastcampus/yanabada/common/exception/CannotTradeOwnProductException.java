package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CANNOT_TRADE_OWN_PRODUCT;

public class CannotTradeOwnProductException extends BaseException {
    public CannotTradeOwnProductException() {
        super(CANNOT_TRADE_OWN_PRODUCT.getMessage());
    }
}
