package kr.co.fastcampus.yanabada.common.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FeeCalculator {

    private static final double FEE_RATE = 0.05;

    public static int calculate(int price) {
        return (int) (price * FEE_RATE);
    }
}
