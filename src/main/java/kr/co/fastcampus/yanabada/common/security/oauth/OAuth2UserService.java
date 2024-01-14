package kr.co.fastcampus.yanabada.common.security.oauth;


import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import java.util.Collections;
import java.util.Map;
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
public class OAuth2UserService extends DefaultOAuth2UserService {

//    private final MemberRepository memberRepository;
//    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        log.info("registrationId={}", registrationId);
        log.info("userNameAttributeName={}", userNameAttributeName);

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes()
        );

        log.info("email={}", oAuth2Attribute.getEmail());
        log.info("name={}", oAuth2Attribute.getName());

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.name())),
            oAuth2Attribute.toMap(),
            "email"
        );
    }
}
