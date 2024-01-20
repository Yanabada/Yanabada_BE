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
    TRADE_NOT_FOUND("존재하지 않는 거래입니다."),
    YANOLJAPAY_NOT_FOUND("야놀자 페이를 찾을 수 없습니다."),
    YANOLJAPAY_HISTORY_NOT_FOUND("야놀자 페이 내역을 찾을 수 없습니다."),
    ADMIN_PAYMENT_NOT_FOUND("어드민 페이를 찾을 수 없습니다."),
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

    NEGOTIATION_NOT_POSSIBLE("네고 불가능한 상품입니다."),
    CANNOT_NEGOTIATE_OWN_PRODUCT("자신이 등록한 상품을 네고할 순 없습니다."),
    CHAT_ROOM_NOT_FOUND_EXCEPTION("존재하지 않은 채팅방입니다."),
    INCORRECT_CHAT_ROOM_MEMBER("채팅방 멤버가 올바르지 않습니다."),

    EMAIL_SEND_FAILED("이메일 전송에 실패하였습니다."),
    EMAIL_DUPLICATED("이메일이 중복됩니다."),

    ILLEGAL_PRODUCT_STATUS("해당 기능을 수행할 수 없는 상품 상태입니다."),
    CANNOT_TRADE_OWN_PRODUCT("자신이 등록한 상품을 거래할 수 없습니다."),

    ILLEGAL_TRADE_STATUS("해당 기능을 수행할 수 없는 거래 상태입니다."),
    NOT_ENOUGH_POINT("포인트가 부족합니다."),
    NOT_ENOUGH_YANOLJAPAY_BALANCE("야놀자 페이 잔액이 부족합니다."),
    DUPLICATE_YANOLJAPAY("이미 야놀자 페이에 가입되어있습니다."),
    INCORRECT_YANOLJAPAY_PASSWORD("일치하지 않는 야놀자 페이 비밀번호 입니다."),
    UNAVAILABLE_STATUS_QUERY("찾을 수 없는 STATUS 쿼리파라미터입니다."),

    JSON_PROCESS_FAILED("객체를 스트링으로 변환하는 데 실패하였습니다."),

    OKHTTP3_REQUEST_FAILED("okhttp3의 리퀘스트 생성이 실패하였습니다."),
    FCM_MESSAGE_SEND_FAILED("FCM 메세지 전송이 실패하였습니다."),
    FCM_ACCESS_TOKEN_GET_FAILED("FCM 엑세스 토큰을 발급 받는 데 실패하였습니다."),

    NOT_MATCHED_PROVIDER_NAME("Provider 이름이 매칭이 안됩니다."),

    NOTIFICATION_NOT_FOUND("존재하지 않는 알림입니다."),

    ;

    private final String message;

    public String getMessage(Object... arg) {
        return String.format(message, arg);
    }
}
