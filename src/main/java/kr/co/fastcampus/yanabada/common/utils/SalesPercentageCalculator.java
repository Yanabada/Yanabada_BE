package kr.co.fastcampus.yanabada.common.utils;

import kr.co.fastcampus.yanabada.common.exception.DivideByZeroException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesPercentageCalculator {

    public static int calculate(int price, int salePrice) {
        if (price == 0) {
            throw new DivideByZeroException();
        }

        return ((price - salePrice) * 100) / price;
    }
}
