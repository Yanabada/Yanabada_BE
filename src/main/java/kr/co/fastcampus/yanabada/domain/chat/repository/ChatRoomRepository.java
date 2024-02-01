package kr.co.fastcampus.yanabada.domain.chat.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.ChatRoomNotFoundException;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByProductIdAndSellerAndBuyer(
        Long productId, Member seller, Member buyer
    );

    @Query("SELECT cr FROM ChatRoom cr "
        + "LEFT JOIN FETCH cr.messages "
        + "WHERE (cr.buyer.id = :memberId AND cr.hasBuyerLeft = false) "
        + "OR (cr.seller.id = :memberId AND cr.hasSellerLeft = false)"
    )
    List<ChatRoom> findChatRoomsByMemberId(@Param("memberId") Long memberId);

    Optional<ChatRoom> findByCode(String code);

    default ChatRoom getChatroom(String code) {
        return findByCode(code).orElseThrow(ChatRoomNotFoundException::new);
    }
}
