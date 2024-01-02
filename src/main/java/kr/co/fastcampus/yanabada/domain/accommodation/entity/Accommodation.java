package kr.co.fastcampus.yanabada.domain.accommodation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodation")
@Entity
public class Accommodation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String image;

    private Accommodation(
        String name,
        String address,
        Double longitude,
        Double latitude,
        City city,
        String phoneNumber,
        String description,
        Category category,
        String image
    ) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public static Accommodation create(
        String name,
        String address,
        Double longitude,
        Double latitude,
        City city,
        String phoneNumber,
        String description,
        Category category,
        String image
    ) {
        return new Accommodation(
            name,
            address,
            longitude,
            latitude,
            city,
            phoneNumber,
            description,
            category,
            image
        );
    }
}
