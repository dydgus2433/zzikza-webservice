package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationResposeDto {
    @DateTimeFormat(pattern = "hh")
    Integer reservationStartHour;
    @DateTimeFormat(pattern = "mm")
    Integer reservationStartMinute;
    @DateTimeFormat(pattern = "hh")
    Integer reservationEndHour;
    @DateTimeFormat(pattern = "mm")
    Integer reservationEndMinute;
    String id;
    String userName;
    String tel;
    String scheduleName;
    Integer peopleCnt;
    String customRequest;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationEndDate;
    Integer price;
    PaymentResponseDto payment;
    String firstFilePath;
    EReservationStatus reservationStatus;

    public ReservationResposeDto(Reservation entity) {
        this.id = entity.getId();
        this.scheduleName = entity.getScheduleName();
        if (entity.getUser() != null) {
            this.userName = entity.getUser().getName();
        } else {
            this.userName = entity.getUserName();
        }

        this.tel = entity.getTel();
        this.reservationStatus = entity.getReservationStatus();
        this.peopleCnt = entity.getPeopleCnt();
        this.customRequest = entity.getCustomRequest();
        this.reservationStartDate = entity.getReservationStartDate();
        this.reservationEndDate = entity.getReservationEndDate();
        this.reservationStartHour = entity.getReservationStartHour();
        this.reservationStartMinute = entity.getReservationStartMinute();
        this.reservationEndHour = entity.getReservationEndHour();
        this.reservationEndMinute = entity.getReservationEndMinute();
        if (entity.getPayment() != null) {
            this.payment = new PaymentResponseDto(entity.getPayment());
            this.price = entity.getPayment().getRealPrice();
        } else {
            this.price = entity.getInputPrice();
        }
        if (entity.getProduct() != null) {
            Product product = entity.getProduct();
            if (!product.getProductFiles().isEmpty()) {
                this.firstFilePath = entity.getProduct().getProductFiles().get(0).getFileThumbPath();
            }
        }

    }

    //mustache를 위한 getter
    public String getTitle() {
        return userName + "님 예약";
    }

    public LocalDateTime getStart() {
        return reservationStartDate.atTime(reservationStartHour, reservationStartMinute);
    }


    public LocalDateTime getEnd() {
        return reservationEndDate.atTime(reservationEndHour, reservationEndMinute);
    }

    public String getReservationStartHour() {
        return StringUtil.lpad(reservationStartHour, "0", 2);
    }

    public String getReservationStartMinute() {
        return StringUtil.lpad(reservationStartMinute, "0", 2);
    }

    public String getReservationEndHour() {
        return StringUtil.lpad(reservationEndHour, "0", 2);

    }

    public String getReservationEndMinute() {
        return StringUtil.lpad(reservationEndMinute, "0", 2);
    }

    public boolean isReservation(){
        return reservationStatus.equals(EReservationStatus.Y);
    }
    public boolean isCancel(){
        return reservationStatus.equals(EReservationStatus.C);
    }
    public boolean isWait(){
        return reservationStatus.equals(EReservationStatus.W);
    }
}