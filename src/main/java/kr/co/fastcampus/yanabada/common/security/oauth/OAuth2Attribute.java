package kr.co.fastcampus.yanabada.common.security.oauth;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes; // 사용자 속성 정보를 담는 Map
    private String attributeKey; // 사용자 속성의 키 값
    private String email; // 이메일 정보
    private String name; // 이름 정보
    private String provider;

    static OAuth2Attribute of(
        String provider,
        String attributeKey,
        Map<String, Object> attributes) {
        if (provider.equals("kakao")) {
            return ofKakao(provider, "email", attributes);
            //todo: 다른 OAuth 구현 시 조건문 추가
        }
        throw new RuntimeException();   //todo: CustomEx
    }

    private static OAuth2Attribute ofKakao(
        String provider,
        String attributeKey,
        Map<String, Object> attributes
    ) {
        Map<String, Object> kakaoAccount
            = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attribute.builder()
            .email((String) kakaoAccount.get("email"))
            .name((String) kakaoAccount.get("name"))
            .provider(provider)
            .attributes(kakaoAccount)
            .attributeKey(attributeKey)
            .build();
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("name", name);
        map.put("provider", provider);

        return map;
    }
}