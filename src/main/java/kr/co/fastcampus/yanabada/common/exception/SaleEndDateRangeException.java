package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.INVALID_SALE_END_DATE_RANGE;

public class SaleEndDateRangeException extends BaseException {
    public SaleEndDateRangeException() {
        super(INVALID_SALE_END_DATE_RANGE.getMessage());
    }
}
