package kr.co.fastcampus.yanabada.domain.member.entity;

import jakarta.persistence.*;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String memberName;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String imageUrl;
    private Integer point;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
    private String deviceKey;

}
