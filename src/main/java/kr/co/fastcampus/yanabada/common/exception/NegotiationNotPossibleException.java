package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class NegotiationNotPossibleException extends BaseException {
    public NegotiationNotPossibleException() {
        super(ErrorCode.NEGOTIATION_NOT_POSSIBLE.getMessage());
    }
}
