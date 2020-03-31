package com.zzikza.springboot.web.dto;


import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.product.Product;
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
    LocalDate rsrvEndDt;
    Integer reservationStartHour;
    Integer reservationStartMinute;

    Integer reservationEndHour;
    Integer reservationEndMinute;
    String userName;
    String tel;
    String scheduleName;
    String cstmReq;
    Integer price;
    Integer peopleCnt;
    EReservationStatus rsrvStatCd;

    public ReservationRequestDto(LocalDate reservationStartDate, Integer reservationStartHour, Integer reservationStartMinute, LocalDate rsrvEndDt, Integer reservationEndHour, Integer reservationEndMinute, String userName, String tel, String scheduleName, String cstmReq, Integer price, Integer peopleCnt, EReservationStatus rsrvStatCd) {
        this.id = id;
        this.reservationStartDate = reservationStartDate;
        this.reservationStartHour = reservationStartHour;
        this.reservationStartMinute = reservationStartMinute;
        this.rsrvEndDt = rsrvEndDt;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.userName = userName;
        this.tel = tel;
        this.scheduleName = scheduleName;
        this.cstmReq = cstmReq;
        this.price = price;
        this.peopleCnt = peopleCnt;
        this.rsrvStatCd = rsrvStatCd;
    }

    @Builder
    public ReservationRequestDto(String id,Integer peopleCnt,  LocalDate reservationStartDate, Integer reservationStartHour, Integer reservationStartMinute, LocalDate rsrvEndDt, Integer reservationEndHour, Integer reservationEndMinute, String userName, String tel, String scheduleName, String cstmReq, Integer price, EReservationStatus rsrvStatCd) {
        this.id = id;
        this.peopleCnt = peopleCnt;
        this.reservationStartDate = reservationStartDate;
        this.reservationStartHour = reservationStartHour;
        this.reservationStartMinute = reservationStartMinute;
        this.rsrvEndDt = rsrvEndDt;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.userName = userName;
        this.tel = tel;
        this.scheduleName = scheduleName;
        this.cstmReq = cstmReq;
        this.price = price;
        this.rsrvStatCd = rsrvStatCd;
    }



    public Reservation toEntity() {

        return Reservation.builder()
                .id(id)
                .peopleCnt(peopleCnt)
                .reservationStartDate(reservationStartDate)
                .reservationStartHour(reservationStartHour)
                .reservationStartMinute(reservationStartMinute)
                .reservationEndDate(rsrvEndDt)
                .reservationEndHour(reservationEndHour)
                .reservationEndMinute(reservationEndMinute)
                .userName(userName)
                .tel(tel)
                .scheduleName(scheduleName)
                .customRequest(cstmReq)
                .inputPrice(price)
                .reservationStatus(rsrvStatCd)
                .build();
    }
}
