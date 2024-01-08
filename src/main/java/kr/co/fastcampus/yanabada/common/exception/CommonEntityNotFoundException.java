package kr.co.fastcampus.yanabada.common.exception;

import kr.co.fastcampus.yanabada.common.response.ErrorCode;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.COMMON_ENTITY_NOT_FOUND;

public class CommonEntityNotFoundException extends BaseException {
    public CommonEntityNotFoundException() {
        super(COMMON_ENTITY_NOT_FOUND.getMessage());
    }
}
