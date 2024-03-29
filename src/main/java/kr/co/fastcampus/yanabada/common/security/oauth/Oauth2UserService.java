package kr.co.fastcampus.yanabada.common.security.oauth;


import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import java.util.Collections;
import java.util.Map;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        Oauth2Attribute oauth2Attribute = Oauth2Attribute.of(
                registrationId, userNameAttributeName, oauth2User.getAttributes()
        );

        boolean isExist = memberRepository.existsByEmailAndProviderType(
            oauth2Attribute.getEmail(),
            ProviderType.valueOf(oauth2Attribute.getProvider()));

        Map<String, Object> attributeMap = oauth2Attribute.toMap();
        attributeMap.put("isExist", isExist);

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.name())),
            attributeMap,
            "email"
        );
    }
}
