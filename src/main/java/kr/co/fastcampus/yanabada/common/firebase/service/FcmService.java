package kr.co.fastcampus.yanabada.common.firebase.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import kr.co.fastcampus.yanabada.common.exception.FcmAccessTokenGetFailedException;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.common.exception.OkHttp3RequestFailedException;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Data;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Message;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Notification;
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

    private final ObjectMapper objectMapper;
    @Value("${firebase.project-id}")
    String projectId;
    @Value("${firebase.key-path}")
    String keyPath;
    @Value("${firebase.fcm-request-url.prefix}")
    String fcmRequestUrlPrefix;
    @Value("${firebase.fcm-request-url.postfix}")
    String fcmRequestUrlPostfix;

    public void sendToMessage(
        String fcmToken,
        Notification notification,
        Data data
    ) {
        try {
            FcmMessageRequest fcmMessage = makeFcmMessage(fcmToken, notification, data);
            sendMessageToFcmServer(objectMapper.writeValueAsString(fcmMessage));
        } catch (JsonProcessingException e) {
            throw new JsonProcessFailedException();
        }
    }

    private void sendMessageToFcmServer(String message) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody
            = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        final String requestUrl = fcmRequestUrlPrefix + projectId + fcmRequestUrlPostfix;

        Request request = new Request.Builder()
            .url(requestUrl)
            .post(requestBody)
            .addHeader(AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(CONTENT_TYPE, "application/json; UTF-8")
            .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.code() != 200) {
                log.error(response.toString());
            }

        } catch (IOException e) {
            throw new OkHttp3RequestFailedException();
        }
    }

    private FcmMessageRequest makeFcmMessage(
        String fcmToken,
        Notification notification,
        Data data
    ) {
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
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new FcmAccessTokenGetFailedException();
        }
    }
}