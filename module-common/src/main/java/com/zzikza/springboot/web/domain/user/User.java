package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.ESnsConnectStatus;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.request.UserRequest;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.dto.UserRequestDto;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "tb_user")
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @Parameter(name = "table_name", value = "sequences"),
            @Parameter(name = "value_column_name", value = "currval"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "tb_user"),
            @Parameter(name = "prefix_key", value = "USR"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "USER_ID", unique = true)
    String userId;

    @Column(name = "SNS_CONNECT_YN")
    @Enumerated(EnumType.STRING)
    ESnsConnectStatus snsConnectStatus;

    @Column(name = "SNS_TYPE")
    @Enumerated(EnumType.STRING)
    ESnsType snsType;

    @Column(name = "PW")
    String password;
    @Column(name = "TEL")
    String tel;

    @Column(name = "USER_NM", unique = true)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STAT_CD")
    EUserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "USE_TERM_YN")
    ETermStatus useTermYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "CANCEL_TERM_YN")
    ETermStatus cancelTermYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIVATE_TERM_YN")
    ETermStatus privateTermYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "OTHER_TERM_YN")
    ETermStatus otherTermYn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserRequest> userRequests = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserWishProduct> userWishProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Reservation> reservations = new ArrayList<>();


//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Payment> payments = new ArrayList<>();


    @Builder
    public User(String userId, String password, String tel, EUserStatus userStatus, String name
            , ETermStatus useTermYn, ETermStatus cancelTermYn, ETermStatus privateTermYn, ETermStatus otherTermYn, ESnsType snsType
            , ESnsConnectStatus snsConnectStatus) {
        this.userId = userId;
        this.password = password;
        this.tel = tel;
        this.userStatus = userStatus;
        this.name = name;
        this.useTermYn = useTermYn;
        this.cancelTermYn = cancelTermYn;
        this.privateTermYn = privateTermYn;
        this.otherTermYn = otherTermYn;
        this.snsType = snsType;
        this.snsConnectStatus = snsConnectStatus;
    }

    public void addUserRequest(UserRequest userRequest) {
        this.userRequests.add(userRequest);
        if (userRequest.getUser() != this) {
            userRequest.setUser(this);
        }
    }

    public void addWishProduct(UserWishProduct product) {
        this.userWishProducts.add(product);
        if (product.getUser() != this) {
            product.setUser(this);
        }
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        if (reservation.getUser() != this) {
            reservation.setUser(this);
        }
    }

    public void update(UserRequestDto entity) {
        if (entity.getChangePassword() != null && !"".equals(entity.getChangePassword())) {
            this.password = PasswordUtil.getEncriptPassword(entity.getChangePassword(), this.userId);
        }
        if (entity.getUserStatus() != null) {
            this.userStatus = entity.getUserStatus();
        }
        if (entity.getTel() != null && !"".equals(entity.getTel())) {
            this.tel = entity.getTel();
        }
//        if (entity.getLttd() != null) {
//            this.lttd = entity.getLttd();
//        }
//        if (entity.getLgtd() != null) {
//            this.lgtd = entity.getLgtd();
//        }
    }


//    public void addPayment(Payment payment) {
//        this.payments.add(payment);
//        if(payment.getUser() != this){
//            payment.setUser(this);
//        }
//    }
}
