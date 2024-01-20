package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.NOT_MATCHED_PROVIDER_NAME;

public class NotMatchedProviderNameException extends BaseException {
    public NotMatchedProviderNameException() {
        super(NOT_MATCHED_PROVIDER_NAME.getMessage());
    }
}
