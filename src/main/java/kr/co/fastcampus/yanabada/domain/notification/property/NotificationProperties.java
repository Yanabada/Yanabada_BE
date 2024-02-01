package kr.co.fastcampus.yanabada.domain.notification.property;

public class NotificationProperties {
    public static final String CHAT_MESSAGE_TITLE = "채팅";

    public static final String CHAT_CREATED_TITLE = "채팅방 개설";
    public static final String CHAT_CREATED_CONTENT_INFIX = "님이 ";
    public static final String CHAT_CREATED_CONTENT_POSTFIX = "에 대한 채팅을 보냈어요!";

    public static final String TRADE_REQUEST_TITLE = "구매 승인 요청";
    public static final String TRADE_REQUEST_CONTENT_POSTFIX = "상품의 구매 승인 요청이 왔어요!";

    public static final String TRADE_CANCELED_TITLE = "구매 승인 취소";
    public static final String TRADE_CANCELED_CONTENT_PREFIX = "구매자가 ";
    public static final String TRADE_CANCELED_CONTENT_POSTFIX = "상품의 구매요청을 취소했어요.";

    public static final String TRADE_APPROVAL_TITLE = "구매 승인 완료";
    public static final String TRADE_APPROVAL_CONTENT_POSTFIX = "상품의 구매가 승인되었어요!";

    public static final String TRADE_REJECTED_TITLE = "구매 승인 거절";
    public static final String TRADE_REJECTED_CONTENT_POSTFIX = "상품의 구매가 거절되었어요.";

    public static final long EXPIRATION_DURATION = 7 * 2;
}
