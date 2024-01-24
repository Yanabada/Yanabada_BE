package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.common.utils.PayFeeCalculator;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;

public record TradeSaveRequest(

    @NotNull(message = "상품 ID는 필수로 입력하셔야 합니다.")
    @Positive(message = "상품 ID는 양수이어야 합니다.")
    Long productId,

    @NotEmpty(message = "예약자 이름은 필수로 입력하셔야 합니다.")
    String reservationPersonName,

    @NotEmpty(message = "예약자 휴대폰 번호는 필수로 입력하셔야 합니다.")
    String reservationPersonPhoneNumber,

    @NotEmpty(message = "이용자 이름은 필수로 입력하셔야 합니다.")
    String userPersonName,

    @NotEmpty(message = "이용자 휴대폰 번호는 필수로 입력하셔야 합니다.")
    String userPersonPhoneNumber,

    @NotNull(message = "포인트는 필수로 입력하셔야 합니다.")
    @PositiveOrZero(message = "포인트는 양수 또는 0이어야 합니다.")
    Integer point,

    @NotNull(message = "결제 수단은 필수로 입력하셔야 합니다.")
    PaymentType paymentType,

    String simplePassword
) {
    public Trade toEntity(
        Product product,
        Member seller,
        Member buyer
    ) {
        return Trade.create(
            product,
            seller,
            buyer,
            reservationPersonName,
            reservationPersonPhoneNumber,
            userPersonName,
            userPersonPhoneNumber,
            product.getOrder().getPrice(),
            product.getPrice(),
            PayFeeCalculator.calculate(product.getPrice(), paymentType),
            point,
            paymentType,
            EntityCodeGenerator.generate(),
            TradeStatus.WAITING,
            LocalDateTime.now()
        );
    }
}
