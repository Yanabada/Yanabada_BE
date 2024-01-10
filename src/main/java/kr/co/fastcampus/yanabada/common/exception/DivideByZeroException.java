package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.DIVIDE_BY_ZERO;

public class DivideByZeroException extends BaseException {
    public DivideByZeroException() {
        super(DIVIDE_BY_ZERO.getMessage());
    }
}
