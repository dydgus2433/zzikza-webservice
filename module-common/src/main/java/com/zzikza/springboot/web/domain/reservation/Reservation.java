package com.zzikza.springboot.web.domain.reservation;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EReservationStatus;
import com.zzikza.springboot.web.domain.pay.Payment;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.dto.ReservationRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "tb_rsrv")
public class Reservation  extends BaseTimeEntity {
    @Id
    @Column(name = "RSRV_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_rsrv"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "RSV"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "SCHEDULE_NM")
    String scheduleName;


    @Column(name = "USER_NM")
    String userName;

    String tel;
    @Column(name = "ppCnt")
    Integer peopleCnt;
    @Column(name = "PRD_PRC")
    Integer inputPrice;

    @Column(name = "RSRV_STRT_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationStartDate;
    @Column(name = "RSRV_END_DT")@DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate reservationEndDate;
    @Column(name = "RSRV_STRT_HOUR")
    Integer reservationStartHour;
    @Column(name = "RSRV_STRT_MIN")
    Integer reservationStartMinute;
    @Column(name = "RSRV_END_HOUR")
    Integer reservationEndHour;
    @Column(name = "RSRV_END_MIN")
    Integer reservationEndMinute;
    @Column(name = "CSTM_REQ")
    String customRequest;
    @Column(name = "RSRV_STAT_CD")
    EReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    User user;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @OneToOne
    @JoinColumn(name = "PAY_ID", nullable = true)
//    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Payment payment;

    @Builder
    public Reservation(String scheduleName, Payment payment, String id, String userName, String tel, Integer peopleCnt, Integer inputPrice, LocalDate reservationStartDate, LocalDate reservationEndDate, Integer reservationStartHour, Integer reservationStartMinute, Integer reservationEndHour, Integer reservationEndMinute, String customRequest, EReservationStatus reservationStatus, User user, Studio studio, Product product) {
        this.id = id;
        this.userName = userName;
        this.tel = tel;
        this.peopleCnt = peopleCnt;
        this.inputPrice = inputPrice;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.reservationStartHour = reservationStartHour;
        this.reservationStartMinute = reservationStartMinute;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.customRequest = customRequest;
        this.reservationStatus = reservationStatus;
        this.user = user;
        this.studio = studio;
        this.product = product;
        this.scheduleName = scheduleName;
        this.payment = payment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void update(ReservationRequestDto dto) {
        this.id = dto.getId();
        this.userName = dto.getUserName();
        this.tel = dto.getTel();
        this.peopleCnt = dto.getPeopleCnt();
        this.inputPrice = dto.getPrice();
        this.reservationStartDate = dto.getReservationStartDate();
        this.reservationEndDate = dto.getRsrvEndDt();
        this.reservationStartHour = dto.getReservationStartHour();
        this.reservationStartMinute = dto.getReservationStartMinute();
        this.reservationEndHour = dto.getReservationEndHour();
        this.reservationEndMinute = dto.getReservationEndMinute();
        this.customRequest = dto.getCstmReq();
        this.reservationStatus = dto.getRsrvStatCd();
        this.userName = dto.getUserName();
        this.scheduleName = dto.getScheduleName();
    }
}
