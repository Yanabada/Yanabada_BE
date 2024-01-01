package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

public class CommonEntityNotFoundException extends BaseException {
    public CommonEntityNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND.getErrorMsg());
    }
}
