package kr.co.fastcampus.yanabada.domain.chat.repository;

import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
