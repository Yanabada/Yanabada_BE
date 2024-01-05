package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class SaleEndDateRangeException extends BaseException {
    public SaleEndDateRangeException() {
        super(ErrorCode.INVALID_SALE_END_DATE_RANGE.getMessage());
    }
}
