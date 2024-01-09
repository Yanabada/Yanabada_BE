package kr.co.fastcampus.yanabada.common.exception;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.RANDOM_CODE_LENGTH_RANGE;

public class RandomCodeLengthRangeException extends BaseException {
    public RandomCodeLengthRangeException() {
        super(RANDOM_CODE_LENGTH_RANGE.getMessage());
    }
}
