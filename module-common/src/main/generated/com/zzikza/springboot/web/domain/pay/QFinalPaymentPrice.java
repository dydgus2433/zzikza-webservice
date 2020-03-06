package com.zzikza.springboot.web.domain.pay;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFinalPaymentPrice is a Querydsl query type for FinalPaymentPrice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFinalPaymentPrice extends EntityPathBase<FinalPaymentPrice> {

    private static final long serialVersionUID = 1032310266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFinalPaymentPrice finalPaymentPrice = new QFinalPaymentPrice("finalPaymentPrice");

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

    public final com.zzikza.springboot.web.domain.product.QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final com.zzikza.springboot.web.domain.sale.QSale sale;

    public QFinalPaymentPrice(String variable) {
        this(FinalPaymentPrice.class, forVariable(variable), INITS);
    }

    public QFinalPaymentPrice(Path<? extends FinalPaymentPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFinalPaymentPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFinalPaymentPrice(PathMetadata metadata, PathInits inits) {
        this(FinalPaymentPrice.class, metadata, inits);
    }

    public QFinalPaymentPrice(Class<? extends FinalPaymentPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.zzikza.springboot.web.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.sale = inits.isInitialized("sale") ? new com.zzikza.springboot.web.domain.sale.QSale(forProperty("sale"), inits.get("sale")) : null;
    }

}

