package kr.co.fastcampus.yanabada.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @Column(nullable = false)
    private String content;

    private LocalDateTime sendDateTime;

    private ChatMessage(
        ChatRoom chatRoom,
        Member sender,
        String content,
        LocalDateTime sendDateTime
    ) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.sendDateTime = sendDateTime;
    }

    public static ChatMessage create(
        ChatRoom chatRoom,
        Member sender,
        String content,
        LocalDateTime sendDateTime
    ) {
        return new ChatMessage(
            chatRoom,
            sender,
            content,
            sendDateTime
        );
    }
}
