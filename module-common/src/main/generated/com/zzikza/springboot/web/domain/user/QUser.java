package com.zzikza.springboot.web.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2127162091L;

    public static final QUser user = new QUser("user");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final ListPath<com.zzikza.springboot.web.domain.reservation.Reservation, com.zzikza.springboot.web.domain.reservation.QReservation> reservations = this.<com.zzikza.springboot.web.domain.reservation.Reservation, com.zzikza.springboot.web.domain.reservation.QReservation>createList("reservations", com.zzikza.springboot.web.domain.reservation.Reservation.class, com.zzikza.springboot.web.domain.reservation.QReservation.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final ListPath<UserRequest, QUserRequest> userRequests = this.<UserRequest, QUserRequest>createList("userRequests", UserRequest.class, QUserRequest.class, PathInits.DIRECT2);

    public final ListPath<UserWishProduct, QUserWishProduct> userWishProducts = this.<UserWishProduct, QUserWishProduct>createList("userWishProducts", UserWishProduct.class, QUserWishProduct.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

