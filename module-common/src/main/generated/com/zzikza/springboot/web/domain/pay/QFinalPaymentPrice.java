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

    public final com.zzikza.springboot.web.domain.exhibition.QExhibition exhibition;

    public final StringPath id = createString("id");

    public final com.zzikza.springboot.web.domain.product.QProduct product;

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
        this.exhibition = inits.isInitialized("exhibition") ? new com.zzikza.springboot.web.domain.exhibition.QExhibition(forProperty("exhibition")) : null;
        this.product = inits.isInitialized("product") ? new com.zzikza.springboot.web.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.sale = inits.isInitialized("sale") ? new com.zzikza.springboot.web.domain.sale.QSale(forProperty("sale"), inits.get("sale")) : null;
    }

}

