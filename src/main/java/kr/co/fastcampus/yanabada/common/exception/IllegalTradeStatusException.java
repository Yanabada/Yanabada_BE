package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ILLEGAL_TRADE_STATUS;

public class IllegalTradeStatusException extends BaseException {
    public IllegalTradeStatusException() {
        super(ILLEGAL_TRADE_STATUS.getMessage());
    }
}
