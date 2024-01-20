package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.ADMIN_PAYMENT_NOT_FOUND;

public class AdminPaymentNotFoundException extends BaseException {
    public AdminPaymentNotFoundException() {
        super(ADMIN_PAYMENT_NOT_FOUND.getMessage());
    }
}
