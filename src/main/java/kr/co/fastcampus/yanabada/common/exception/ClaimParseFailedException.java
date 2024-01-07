package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class ClaimParseFailedException extends BaseException {
    public ClaimParseFailedException() {
        super(ErrorCode.CLAIM_PARSE_FAILED.getMessage());
    }
}
