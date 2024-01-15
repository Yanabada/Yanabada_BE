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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@Entity
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime checkInTime;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime checkOutTime;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer minHeadCount;

    @Column(nullable = false)
    private Integer maxHeadCount;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomCancelPolicy cancelPolicy;

    @OneToOne(
        fetch = FetchType.LAZY, mappedBy = "room",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private RoomOption roomOption;

    private Room(
        Accommodation accommodation,
        String name,
        Integer price,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        String description,
        Integer minHeadCount,
        Integer maxHeadCount,
        Double rating,
        String image,
        RoomCancelPolicy cancelPolicy
    ) {
        this.accommodation = accommodation;
        this.name = name;
        this.price = price;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.description = description;
        this.minHeadCount = minHeadCount;
        this.maxHeadCount = maxHeadCount;
        this.rating = rating;
        this.image = image;
        this.cancelPolicy = cancelPolicy;
    }

    public static Room create(
        Accommodation accommodation,
        String name,
        Integer price,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        String description,
        Integer minHeadCount,
        Integer maxHeadCount,
        Double rating,
        String image,
        RoomCancelPolicy cancelPolicy
    ) {
        return new Room(
            accommodation,
            name,
            price,
            checkInTime,
            checkOutTime,
            description,
            minHeadCount,
            maxHeadCount,
            rating,
            image,
            cancelPolicy
        );
    }

    public void registerRoomOption(RoomOption accommodationOption) {
        this.roomOption = accommodationOption;
    }
}
