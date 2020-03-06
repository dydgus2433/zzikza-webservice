package com.zzikza.springboot.web.domain.exhibition;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExhibition is a Querydsl query type for Exhibition
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExhibition extends EntityPathBase<Exhibition> {

    private static final long serialVersionUID = -15453739L;

    public static final QExhibition exhibition = new QExhibition("exhibition");

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

    public final ListPath<com.zzikza.springboot.web.domain.sale.Sale, com.zzikza.springboot.web.domain.sale.QSale> sales = this.<com.zzikza.springboot.web.domain.sale.Sale, com.zzikza.springboot.web.domain.sale.QSale>createList("sales", com.zzikza.springboot.web.domain.sale.Sale.class, com.zzikza.springboot.web.domain.sale.QSale.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QExhibition(String variable) {
        super(Exhibition.class, forVariable(variable));
    }

    public QExhibition(Path<? extends Exhibition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExhibition(PathMetadata metadata) {
        super(Exhibition.class, metadata);
    }

}

