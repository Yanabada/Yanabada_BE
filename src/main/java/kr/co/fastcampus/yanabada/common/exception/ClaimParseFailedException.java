package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CLAIM_PARSE_FAILED;

public class ClaimParseFailedException extends BaseException {
    public ClaimParseFailedException() {
        super(CLAIM_PARSE_FAILED.getMessage());
    }
}
