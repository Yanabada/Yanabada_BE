package kr.co.fastcampus.yanabada.common.utils;

import static kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType.YANOLJA_PAY;

import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PayFeeCalculator {

    private static final double FEE_RATE = 0.05;

    public static int calculate(int price, PaymentType paymentType) {
        if (paymentType == YANOLJA_PAY) {
            return 0;
        }
        return (int) (price * FEE_RATE);
    }
}
