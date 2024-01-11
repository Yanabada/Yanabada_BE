package kr.co.fastcampus.yanabada.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;

public record ChatRoomSaveRequest(
    @NotNull(message = "상품 ID는 필수로 입력하셔야 합니다.")
    @Positive(message = "상품 ID는 양수이어야 합니다.")
    Long productId,

    @NotNull(message = "구매자 ID는 필수로 입력하셔야 합니다.")
    @Positive(message = "구매자 ID는 양수이어야 합니다.")
    Long buyerId
) {

    public ChatRoom toEntity(
        Product product,
        Member seller,
        Member buyer,
        LocalDateTime sellerLastCheckTime,
        LocalDateTime buyerLastCheckTime
    ) {
        return ChatRoom.create(
            product,
            seller,
            buyer,
            EntityCodeGenerator.generate(),
            sellerLastCheckTime,
            buyerLastCheckTime
        );
    }
}
