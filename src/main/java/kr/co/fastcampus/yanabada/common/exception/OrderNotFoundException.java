package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException() {
        super(ErrorCode.ACCOMMODATION_NOT_FOUND.getMessage());
    }
}
