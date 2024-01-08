package kr.co.fastcampus.yanabada.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesPercentageCalculator {

    public static int calculate(int price, int salePrice) {
        return ((price - salePrice) * 100) / price;
    }
}
