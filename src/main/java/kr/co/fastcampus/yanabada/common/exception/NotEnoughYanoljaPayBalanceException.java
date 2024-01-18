package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.NOT_ENOUGH_YANOLJAPAY_BALANCE;

public class NotEnoughYanoljaPayBalanceException extends BaseException {
    public NotEnoughYanoljaPayBalanceException() {
        super(NOT_ENOUGH_YANOLJAPAY_BALANCE.getMessage());
    }
}
