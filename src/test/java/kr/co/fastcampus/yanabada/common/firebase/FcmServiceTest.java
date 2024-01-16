package kr.co.fastcampus.yanabada.common.firebase;

import java.io.IOException;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest;
import kr.co.fastcampus.yanabada.common.firebase.service.FcmService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FcmServiceTest {

    @Autowired
    private FcmService fcmService;

    @Test
    void sendMessageTest() throws IOException {
        FcmMessageRequest.Notification notification = FcmMessageRequest.Notification.builder()
            .title("제목")
            .body("내용")
            .image("이미지")
            .build();
        Member sender = Member
            .builder()
            .id(1L)
            .nickName("sender")
            .build();
        Member receiver = Member
            .builder()
            .id(1L)
            .nickName("sender")
            .build();
        fcmService.sendToMessage(sender, receiver, notification);
    }


}