package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }
}
