package kr.co.fastcampus.yanabada.common.firebase.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Data;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Message;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Notification;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    @Value("${firebase.project-id}")
    String projectId;
    @Value("${firebase.key-path}")
    String keyPath;
    @Value("${firebase.fcm-request-url.prefix}")
    String FCM_REQUEST_URL_PREFIX;
    @Value("${firebase.fcm-request-url.postfix}")
    String FCM_REQUEST_URL_POSTFIX;
    String fcmToken
        = "dHouRIbpZSUeFWw9vVNN7p:APA91bECfpMv9ES-Q8-6MrpBHmgiiQAkNAUfIGE5BiS0rvcUXkcI3U6PkDx_pwQ6N1o6gkTCv5qpx1oTCiJbPqMEsnHqbj3FWM6OTLpFjSkA2e5PrMSAkdjxybbnZ-NDHSxzSHOZIwcR";

    public void sendToMessage(
        Member sender,
        Member receiver,
        Notification notification
    ) throws IOException {
        FcmMessageRequest fcmMessage = makeFcmMessage(sender, receiver, notification);
        sendMessageToFcmServer(objectMapper.writeValueAsString(fcmMessage));
    }

    private void sendMessageToFcmServer(String message) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody
            = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        final String API_URL = FCM_REQUEST_URL_PREFIX + projectId + FCM_REQUEST_URL_POSTFIX;

        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(CONTENT_TYPE, "application/json; UTF-8")
            .build();

        try {
            Response response = client.newCall(request).execute();
            log.info("response.statusCode={}", response.code());
            log.info("RESPONSE={}", response.body().string());

//            System.out.println("RESPONSE : " + response.body().string());

            if (response.code() != 200) {
                throw new RuntimeException("FCM 통신 오류");
            }

        } catch (IOException e) {
            throw new RuntimeException("통신 오류");
        }
    }

    private FcmMessageRequest makeFcmMessage(
        Member sender,
        Member receiver,
        Notification notification
    ) {
        String title = "hihihi";
        String body = "안녕하세요.";

        Data data = Data.builder()
            .senderName(sender.getNickName())
            .senderId(sender.getId().toString())
            .message("message message")
            .build();

        Message message = Message
            .builder()
            .notification(notification)
            .data(data)
            .token(fcmToken)
            .build();

        return FcmMessageRequest
            .builder()
            .validateOnly(false)
            .message(message)
            .build();
    }

    private String getAccessToken()  {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(keyPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            googleCredentials.refreshIfExpired();
            log.info("getAccessToken={}", googleCredentials.getAccessToken().getTokenValue());
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException("AccessToken Error");    //todo: 커스텀 Ex
        }
    }
}