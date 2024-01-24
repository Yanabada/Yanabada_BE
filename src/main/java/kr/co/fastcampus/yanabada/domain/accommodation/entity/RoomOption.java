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
@Table(name = "room_option")
@Entity
public class RoomOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private Boolean canPark;

    @Column(nullable = false)
    private Boolean isPartyRoom;

    @Column(nullable = false)
    private Boolean canAccompanyPet;

    @Column(nullable = false)
    private Boolean isKidsRoom;

    @Column(nullable = false)
    private Boolean isCityView;

    @Column(nullable = false)
    private Boolean isOceanView;

    @Column(nullable = false)
    private Boolean hasPc;

    @Column(nullable = false)
    private Boolean hasOtt;

    @Column(nullable = false)
    private Boolean hasBathtub;

    @Column(nullable = false)
    private Boolean hasAmenity;

    @Column(nullable = false)
    private Boolean hasBreakfast;

    @Column(nullable = false)
    private Boolean canCook;

    @Column(nullable = false)
    private Boolean isNoKids;

    private RoomOption(
        Room room,
        Boolean canPark,
        Boolean isPartyRoom,
        Boolean canAccompanyPet,
        Boolean isKidsRoom,
        Boolean isCityView,
        Boolean isOceanView,
        Boolean hasPc,
        Boolean hasOtt,
        Boolean hasBathtub,
        Boolean hasAmenity,
        Boolean hasBreakfast,
        Boolean canCook,
        Boolean isNoKids
    ) {
        this.room = room;
        this.canPark = canPark;
        this.isPartyRoom = isPartyRoom;
        this.canAccompanyPet = canAccompanyPet;
        this.isKidsRoom = isKidsRoom;
        this.isCityView = isCityView;
        this.isOceanView = isOceanView;
        this.hasPc = hasPc;
        this.hasOtt = hasOtt;
        this.hasBathtub = hasBathtub;
        this.hasAmenity = hasAmenity;
        this.hasBreakfast = hasBreakfast;
        this.canCook = canCook;
        this.isNoKids = isNoKids;
    }

    public static RoomOption create(
        Room room,
        Boolean canPark,
        Boolean isPartyRoom,
        Boolean canAccompanyPet,
        Boolean isKidsRoom,
        Boolean isCityView,
        Boolean isOceanView,
        Boolean hasPc,
        Boolean hasOtt,
        Boolean hasBathtub,
        Boolean hasAmenity,
        Boolean hasBreakfast,
        Boolean canCook,
        Boolean isNoKids
    ) {
        return new RoomOption(
            room,
            canPark,
            isPartyRoom,
            canAccompanyPet,
            isKidsRoom,
            isCityView,
            isOceanView,
            hasPc,
            hasOtt,
            hasBathtub,
            hasAmenity,
            hasBreakfast,
            canCook,
            isNoKids
        );
    }
}
