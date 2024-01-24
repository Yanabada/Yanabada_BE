package kr.co.fastcampus.yanabada.domain.chat.repository;

import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Page<ChatMessage> findByChatRoom(ChatRoom chatRoom, Pageable pageable);
}
