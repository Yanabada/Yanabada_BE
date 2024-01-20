package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.JSON_PROCESS_FAILED;

public class JsonProcessFailedException extends BaseException {
    public JsonProcessFailedException() {
        super(JSON_PROCESS_FAILED.getMessage());
    }
}
