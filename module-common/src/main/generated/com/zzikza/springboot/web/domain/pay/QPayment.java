package com.zzikza.springboot.web.domain.pay;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 257066439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final QFinalPaymentPrice finalPaymentPrice;

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final com.zzikza.springboot.web.domain.studio.QStudio studio;

    public final com.zzikza.springboot.web.domain.user.QUser user;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.finalPaymentPrice = inits.isInitialized("finalPaymentPrice") ? new QFinalPaymentPrice(forProperty("finalPaymentPrice"), inits.get("finalPaymentPrice")) : null;
        this.studio = inits.isInitialized("studio") ? new com.zzikza.springboot.web.domain.studio.QStudio(forProperty("studio"), inits.get("studio")) : null;
        this.user = inits.isInitialized("user") ? new com.zzikza.springboot.web.domain.user.QUser(forProperty("user")) : null;
    }

}

