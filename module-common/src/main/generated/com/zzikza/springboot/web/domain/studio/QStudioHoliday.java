package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioHoliday is a Querydsl query type for StudioHoliday
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioHoliday extends EntityPathBase<StudioHoliday> {

    private static final long serialVersionUID = 1374427171L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioHoliday studioHoliday = new QStudioHoliday("studioHoliday");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    public final EnumPath<com.zzikza.springboot.web.domain.enums.EDateStatus> dateCode = createEnum("dateCode", com.zzikza.springboot.web.domain.enums.EDateStatus.class);

    public final StringPath dateValue = createString("dateValue");

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

    public final QStudio studio;

    public QStudioHoliday(String variable) {
        this(StudioHoliday.class, forVariable(variable), INITS);
    }

    public QStudioHoliday(Path<? extends StudioHoliday> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioHoliday(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioHoliday(PathMetadata metadata, PathInits inits) {
        this(StudioHoliday.class, metadata, inits);
    }

    public QStudioHoliday(Class<? extends StudioHoliday> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new QStudio(forProperty("studio"), inits.get("studio")) : null;
    }

}

