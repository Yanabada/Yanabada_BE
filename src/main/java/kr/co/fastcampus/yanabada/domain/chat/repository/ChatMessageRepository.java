package kr.co.fastcampus.yanabada.domain.chat.repository;

import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
