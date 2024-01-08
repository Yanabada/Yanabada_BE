package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND.getMessage());
    }
}
