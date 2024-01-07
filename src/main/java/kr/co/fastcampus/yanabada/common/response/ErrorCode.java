package kr.co.fastcampus.yanabada.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ACCOMMODATION_NOT_FOUND("존재하지 않는 숙소입니다."),
    ROOM_NOT_FOUND("존재하지 않는 객실입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),

    //Auth
    CLAIM_PARSE_FAILED("토큰의 클레임을 읽을 수 없습니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다."),
    TOKEN_NOT_VALIDATED("잘못된 토큰입니다."),

    ;

    private final String message;

    public String getMessage(Object... arg) {
        return String.format(message, arg);
    }
}
