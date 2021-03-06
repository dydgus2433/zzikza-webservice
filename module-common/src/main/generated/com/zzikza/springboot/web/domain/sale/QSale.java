package com.zzikza.springboot.web.domain.sale;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSale is a Querydsl query type for Sale
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSale extends EntityPathBase<Sale> {

    private static final long serialVersionUID = 1727234709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSale sale = new QSale("sale");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final com.zzikza.springboot.web.domain.exhibition.QExhibition exhibition;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final StringPath saleName = createString("saleName");

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public QSale(String variable) {
        this(Sale.class, forVariable(variable), INITS);
    }

    public QSale(Path<? extends Sale> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSale(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSale(PathMetadata metadata, PathInits inits) {
        this(Sale.class, metadata, inits);
    }

    public QSale(Class<? extends Sale> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exhibition = inits.isInitialized("exhibition") ? new com.zzikza.springboot.web.domain.exhibition.QExhibition(forProperty("exhibition")) : null;
    }

}

