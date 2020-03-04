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

    public final StringPath id = createString("id");

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

