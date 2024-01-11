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
    ORDER_NOT_FOUND("존재하지 않는 예약입니다."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다."),
    ACCESS_FORBIDDEN("권한이 없습니다."),
    ORDER_NOT_SELLABLE("판매할 수 없는 예약입니다."),
    INVALID_SELLING_PRICE_RANGE("판매가는 구매가보다 클 수 없습니다."),
    INVALID_SALE_END_DATE_RANGE("판매 중단 날짜는 현재 날짜 이상 체크인 날짜 이하여야 합니다."),
    RANDOM_CODE_LENGTH_RANGE("랜덤 코드의 길이는 1 이상 UUID 길이 보다 작아야 합니다."),
    DIVIDE_BY_ZERO("0으로 나눌 수 없습니다."),

    CLAIM_PARSE_FAILED("토큰의 클레임을 읽을 수 없습니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다."),
    TOKEN_NOT_VALIDATED("잘못된 토큰입니다."),
    TOKEN_NOT_EXIST_AT_CACHE("이미 로그아웃 처리된 토큰입니다."),
    INVALID_STATUS_PRODUCT_UPDATE("판매되거나 취소된 상품은 수정할 수 없습니다."),
    ;

    private final String message;

    public String getMessage(Object... arg) {
        return String.format(message, arg);
    }
}
