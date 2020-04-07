package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.request.UserRequest;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
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
public class User  extends BaseTimeEntity {
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
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

    @Column(name = "USER_NM", unique = true)
    String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserRequest> userRequests = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserWishProduct> userWishProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Reservation> reservations = new ArrayList<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Payment> payments = new ArrayList<>();


    @Builder
    public User(String userId) {
        this.userId = userId;
    }

    public void addUserRequest(UserRequest userRequest){
        this.userRequests.add(userRequest);
        if(userRequest.getUser() != this){
            userRequest.setUser(this);
        }
    }

    public void addWishProduct(UserWishProduct product) {
        this.userWishProducts.add(product);
        if(product.getUser() != this){
            product.setUser(this);
        }
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        if(reservation.getUser() != this){
            reservation.setUser(this);
        }
    }

//    public void addPayment(Payment payment) {
//        this.payments.add(payment);
//        if(payment.getUser() != this){
//            payment.setUser(this);
//        }
//    }
}
