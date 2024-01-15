package kr.co.fastcampus.yanabada.common.security.oauth;


import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import java.util.Collections;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        log.info("registrationId={}", registrationId);
        log.info("userNameAttributeName={}", userNameAttributeName);

        Oauth2Attribute oauth2Attribute = Oauth2Attribute.of(
                registrationId, userNameAttributeName, oauth2User.getAttributes()
        );

        log.info("email={}", oauth2Attribute.getEmail());
        log.info("name={}", oauth2Attribute.getName());

        //todo: OAuth password 환경 변수 분리 예정
        String oauth2Password = passwordEncoder.encode("oauth-password");

        Member newMember = Member.builder()
            .email(oauth2Attribute.getEmail())
            .memberName(oauth2Attribute.getName())
            .password(oauth2Password)
            .roleType(ROLE_USER)
            .providerType(ProviderType.valueOf(oauth2Attribute.getProvider()))
            .build();

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.name())),
            oauth2Attribute.toMap(),
            "email"
        );
    }
}
