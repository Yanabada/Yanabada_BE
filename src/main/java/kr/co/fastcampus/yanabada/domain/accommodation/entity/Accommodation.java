package kr.co.fastcampus.yanabada.domain.accommodation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Region;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String image;

    @OneToOne(
        fetch = FetchType.LAZY, mappedBy = "accommodation",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private AccommodationOption accommodationOption;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "accommodation",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private final List<Room> rooms = new ArrayList<>();

    private Accommodation(
        String name,
        String address,
        Double longitude,
        Double latitude,
        Region region,
        String phoneNumber,
        String description,
        Category category,
        String image
    ) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.region = region;
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
        Region region,
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
            region,
            phoneNumber,
            description,
            category,
            image
        );
    }

    public void registerAccommodationOption(AccommodationOption accommodationOption) {
        this.accommodationOption = accommodationOption;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
