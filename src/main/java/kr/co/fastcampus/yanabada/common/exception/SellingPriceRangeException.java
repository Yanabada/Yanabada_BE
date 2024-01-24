package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.INVALID_SELLING_PRICE_RANGE;

public class SellingPriceRangeException extends BaseException {
    public SellingPriceRangeException() {
        super(INVALID_SELLING_PRICE_RANGE.getMessage());
    }
}
