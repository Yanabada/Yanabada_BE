package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.UNAVAILABLE_STATUS_QUERY;

public class UnavailableStatusQueryException extends BaseException {
    public UnavailableStatusQueryException() {
        super(UNAVAILABLE_STATUS_QUERY.getMessage());
    }
}