package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudioDetail is a Querydsl query type for StudioDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioDetail extends EntityPathBase<StudioDetail> {

    private static final long serialVersionUID = 1583383334L;

    public static final QStudioDetail studioDetail = new QStudioDetail("studioDetail");

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

    public final StringPath studioDescription = createString("studioDescription");

    public QStudioDetail(String variable) {
        super(StudioDetail.class, forVariable(variable));
    }

    public QStudioDetail(Path<? extends StudioDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudioDetail(PathMetadata metadata) {
        super(StudioDetail.class, metadata);
    }

}

