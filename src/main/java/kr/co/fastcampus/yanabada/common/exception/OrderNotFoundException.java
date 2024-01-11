package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ORDER_NOT_FOUND;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException() {
        super(ORDER_NOT_FOUND.getMessage());
    }
}
