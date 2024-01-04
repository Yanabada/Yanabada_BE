package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class AccommodationNotFoundException extends BaseException {
    public AccommodationNotFoundException() {
        super(ErrorCode.ACCOMMODATION_NOT_FOUND.getErrorMsg());
    }
}
