package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioFile is a Querydsl query type for StudioFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioFile extends EntityPathBase<StudioFile> {

    private static final long serialVersionUID = -1392701039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioFile studioFile = new QStudioFile("studioFile");

    public final StringPath fileName = createString("fileName");

    public final StringPath id = createString("id");

    public final QStudio studio;

    public QStudioFile(String variable) {
        this(StudioFile.class, forVariable(variable), INITS);
    }

    public QStudioFile(Path<? extends StudioFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioFile(PathMetadata metadata, PathInits inits) {
        this(StudioFile.class, metadata, inits);
    }

    public QStudioFile(Class<? extends StudioFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new QStudio(forProperty("studio"), inits.get("studio")) : null;
    }

}

