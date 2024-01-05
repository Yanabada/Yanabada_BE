package kr.co.fastcampus.yanabada.common.security;


import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

public record PrincipalDetails(
        String email,
        String password,
        String memberName
) implements UserDetails {

    public static PrincipalDetails of(Member member) {
        return new PrincipalDetails(member.getEmail(),
                member.getPassword(),
                member.getMemberName()
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
