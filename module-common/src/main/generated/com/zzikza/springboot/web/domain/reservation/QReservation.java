package com.zzikza.springboot.web.domain.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 1799566641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

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

    public final com.zzikza.springboot.web.domain.pay.QPayment payment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final StringPath scheduleName = createString("scheduleName");

    public final com.zzikza.springboot.web.domain.studio.QStudio studio;

    public final com.zzikza.springboot.web.domain.user.QUser user;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payment = inits.isInitialized("payment") ? new com.zzikza.springboot.web.domain.pay.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.studio = inits.isInitialized("studio") ? new com.zzikza.springboot.web.domain.studio.QStudio(forProperty("studio"), inits.get("studio")) : null;
        this.user = inits.isInitialized("user") ? new com.zzikza.springboot.web.domain.user.QUser(forProperty("user")) : null;
    }

}

