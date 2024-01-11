package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.TOKEN_NOT_EXIST_AT_CACHE;

public class TokenNotExistAtCacheException extends BaseException {

    public TokenNotExistAtCacheException() {
        super(TOKEN_NOT_EXIST_AT_CACHE.getMessage());
    }
}
