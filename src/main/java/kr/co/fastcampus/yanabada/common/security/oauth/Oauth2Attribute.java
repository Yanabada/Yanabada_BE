package kr.co.fastcampus.yanabada.common.security.oauth;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.KAKAO;

import java.util.HashMap;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.exception.NotMatchedProviderNameException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Oauth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String provider;

    static Oauth2Attribute of(
        String provider,
        String attributeKey,
        Map<String, Object> attributes) {
        if (provider.equals("kakao")) {
            return ofKakao(KAKAO.name(), "email", attributes);
            //todo: 다른 OAuth 구현 시 조건문 추가
        }
        throw new NotMatchedProviderNameException();
    }

    private static Oauth2Attribute ofKakao(
        String provider,
        String attributeKey,
        Map<String, Object> attributes
    ) {
        Map<String, Object> kakaoAccount
            = (Map<String, Object>) attributes.get("kakao_account");

        return Oauth2Attribute.builder()
            .email((String) kakaoAccount.get("email"))
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
        map.put("provider", provider);

        return map;
    }
}