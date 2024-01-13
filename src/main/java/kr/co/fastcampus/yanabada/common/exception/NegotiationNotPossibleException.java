package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.NEGOTIATION_NOT_POSSIBLE;

public class NegotiationNotPossibleException extends BaseException {
    public NegotiationNotPossibleException() {
        super(NEGOTIATION_NOT_POSSIBLE.getMessage());
    }
}
