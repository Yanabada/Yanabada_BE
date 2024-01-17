package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.common.utils.FeeCalculator;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;

public record TradeSaveRequest(

    @NotNull
    Long productId,

    @NotEmpty
    String reservationPersonName,

    @NotEmpty
    String reservationPersonPhoneNumber,

    @NotEmpty
    String userPersonName,

    @NotEmpty
    String userPersonPhoneNumber,

    @NotNull
    Integer point,

    @NotNull
    PaymentType paymentType
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
            FeeCalculator.calculate(product.getPrice()),
            point,
            paymentType,
            EntityCodeGenerator.generate(),
            TradeStatus.WAITING,
            LocalDateTime.now()
        );
    }
}
