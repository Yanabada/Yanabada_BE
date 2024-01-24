package kr.co.fastcampus.yanabada.domain.accommodation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodation_option")
@Entity
public class AccommodationOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(nullable = false)
    private Boolean hasSauna;

    @Column(nullable = false)
    private Boolean hasRooftop;

    @Column(nullable = false)
    private Boolean hasPool;

    @Column(nullable = false)
    private Boolean hasGym;

    @Column(nullable = false)
    private Boolean hasLoungeBar;

    private AccommodationOption(
        Accommodation accommodation,
        Boolean hasSauna,
        Boolean hasRooftop,
        Boolean hasPool,
        Boolean hasGym,
        Boolean hasLoungeBar
    ) {
        this.accommodation = accommodation;
        this.hasSauna = hasSauna;
        this.hasRooftop = hasRooftop;
        this.hasPool = hasPool;
        this.hasGym = hasGym;
        this.hasLoungeBar = hasLoungeBar;
    }

    public static AccommodationOption create(
        Accommodation accommodation,
        Boolean hasSauna,
        Boolean hasRooftop,
        Boolean hasPool,
        Boolean hasGym,
        Boolean hasLoungeBar
    ) {
        return new AccommodationOption(
            accommodation,
            hasSauna,
            hasRooftop,
            hasPool,
            hasGym,
            hasLoungeBar
        );
    }
}
