package kr.co.fastcampus.yanabada.common.security;

import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import java.util.Collection;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record PrincipalDetails(
        String email,
        String password,
        ProviderType provider
) implements UserDetails {

    public static PrincipalDetails of(Member member) {
        return new PrincipalDetails(member.getEmail(),
                member.getPassword(),
                member.getProviderType()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: 역할 관련 협의 필요
        return List.of(new SimpleGrantedAuthority(ROLE_USER.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
