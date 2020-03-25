package com.zzikza.springboot.web.domain.reservation;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.pay.Payment;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    User user;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @OneToOne
    @JoinColumn(name = "PAY_ID", nullable = true)
//    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Payment payment;

    @Builder
    public Reservation(String scheduleName, Payment payment) {
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
}
