package kr.co.fastcampus.yanabada.domain.accommodation.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RoomCancelPolicy {
    YNBD_1(6L),
    YNBD_2(5L),
    YNBD_3(Long.MAX_VALUE),
    ;

    private final long feeOccurrenceDuration;
}
