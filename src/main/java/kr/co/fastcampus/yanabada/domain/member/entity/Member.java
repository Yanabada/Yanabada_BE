package kr.co.fastcampus.yanabada.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
<<<<<<< HEAD

    public Long getId() {
        return id;
    }
=======
    private String email;
    private String memberName;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String imageUrl;
    @Builder.Default
    private Integer point = 0;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
    private String deviceKey;

>>>>>>> bdae0db03c38d67a8c5d0cb6a769397dca141100
}
