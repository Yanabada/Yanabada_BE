package kr.co.fastcampus.yanabada.domain.accommodation.entity.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Region {
    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    CHUNGCHEONG("충청"),
    DAEJEON("대전"),
    JEOLLA("전라"),
    GWANGJU("광주"),
    GYEONGSANG("경상"),
    DAEGU("대구"),
    ULSAN("울산"),
    BUSAN("부산"),
    JEJU("제주");

    private final String value;
}
