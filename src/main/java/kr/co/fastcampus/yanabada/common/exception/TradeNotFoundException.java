package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TRADE_NOT_FOUND;

public class TradeNotFoundException extends BaseException {
    public TradeNotFoundException() {
        super(TRADE_NOT_FOUND.getMessage());
    }
}
