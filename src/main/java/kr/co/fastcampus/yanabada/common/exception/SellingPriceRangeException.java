package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class SellingPriceRangeException extends BaseException {
    public SellingPriceRangeException() {
        super(ErrorCode.INVALID_SELLING_PRICE_RANGE.getMessage());
    }
}
