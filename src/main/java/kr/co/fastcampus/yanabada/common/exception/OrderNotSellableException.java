package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class OrderNotSellableException extends BaseException {
    public OrderNotSellableException() {
        super(ErrorCode.ORDER_NOT_SELLABLE.getMessage());
    }
}
