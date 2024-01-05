package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ORDER_NOT_SELLABLE;

public class OrderNotSellableException extends BaseException {
    public OrderNotSellableException() {
        super(ORDER_NOT_SELLABLE.getMessage());
    }
}
