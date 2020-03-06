package com.zzikza.springboot.web.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseTimeEntity is a Querydsl query type for BaseTimeEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseTimeEntity extends EntityPathBase<BaseTimeEntity> {

    private static final long serialVersionUID = 72084058L;

    public static final QBaseTimeEntity baseTimeEntity = new QBaseTimeEntity("baseTimeEntity");

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    public final StringPath deletedId = createString("deletedId");

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final StringPath modifiedId = createString("modifiedId");

    public final DateTimePath<java.time.LocalDateTime> registedDate = createDateTime("registedDate", java.time.LocalDateTime.class);

    public final StringPath registedId = createString("registedId");

    public QBaseTimeEntity(String variable) {
        super(BaseTimeEntity.class, forVariable(variable));
    }

    public QBaseTimeEntity(Path<? extends BaseTimeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseTimeEntity(PathMetadata metadata) {
        super(BaseTimeEntity.class, metadata);
    }

}

