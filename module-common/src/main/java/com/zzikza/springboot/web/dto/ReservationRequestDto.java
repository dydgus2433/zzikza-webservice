package com.zzikza.springboot.web.dto;


import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationEndDate;
    Integer reservationStartHour;
    Integer reservationStartMinute;

    Integer reservationEndHour;
    Integer reservationEndMinute;
    String userName;
    String tel;
    String scheduleName;
    String customRequest;
    Integer reservationPrice;
    Integer productPrice;
    Integer peopleCnt;
    EReservationStatus rsrvStatCd;
    String saleId;
    String exhId;

    public ReservationRequestDto(String exhId, Integer productPrice, Integer reservationPrice, String saleCode, LocalDate reservationStartDate, Integer reservationStartHour, Integer reservationStartMinute, LocalDate reservationEndDate, Integer reservationEndHour, Integer reservationEndMinute, String userName, String tel, String scheduleName, String customRequest, Integer price, Integer peopleCnt, EReservationStatus rsrvStatCd) {
        this.id = id;
        this.reservationStartDate = reservationStartDate;
        this.reservationStartHour = reservationStartHour;
        this.reservationStartMinute = reservationStartMinute;
        this.reservationEndDate = reservationEndDate;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.userName = userName;
        this.tel = tel;
        this.scheduleName = scheduleName;
        this.customRequest = customRequest;
        this.productPrice = productPrice;
        this.reservationPrice = reservationPrice;
        this.peopleCnt = peopleCnt;
        this.rsrvStatCd = rsrvStatCd;
        this.saleId = saleCode;
        this.exhId = exhId;
    }

    @Builder
    public ReservationRequestDto(String exhId, Integer productPrice, Integer reservationPrice, String saleCode, String id, Integer peopleCnt, LocalDate reservationStartDate, Integer reservationStartHour, Integer reservationStartMinute, LocalDate reservationEndDate, Integer reservationEndHour, Integer reservationEndMinute, String userName, String tel, String scheduleName, String customRequest, Integer price, EReservationStatus rsrvStatCd) {
        this.id = id;
        this.peopleCnt = peopleCnt;
        this.reservationStartDate = reservationStartDate;
        this.reservationStartHour = reservationStartHour;
        this.reservationStartMinute = reservationStartMinute;
        this.reservationEndDate = reservationEndDate;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.userName = userName;
        this.tel = tel;
        this.scheduleName = scheduleName;
        this.customRequest = customRequest;
        this.productPrice = productPrice;
        this.reservationPrice = reservationPrice;
        this.rsrvStatCd = rsrvStatCd;
        this.saleId = saleCode;
        this.exhId = exhId;
    }


    public Reservation toEntity() {

        return Reservation.builder()
                .id(id)
                .peopleCnt(peopleCnt)
                .reservationStartDate(reservationStartDate)
                .reservationStartHour(reservationStartHour)
                .reservationStartMinute(reservationStartMinute)
                .reservationEndDate(reservationEndDate)
                .reservationEndHour(reservationEndHour)
                .reservationEndMinute(reservationEndMinute)
                .userName(userName)
                .tel(tel)
                .scheduleName(scheduleName)
                .customRequest(customRequest)
                .inputPrice(reservationPrice)
                .reservationStatus(rsrvStatCd)
                .build();
    }
}
