package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioKeywordMap is a Querydsl query type for StudioKeywordMap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioKeywordMap extends EntityPathBase<StudioKeywordMap> {

    private static final long serialVersionUID = -1301294328L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioKeywordMap studioKeywordMap = new QStudioKeywordMap("studioKeywordMap");

    public final StringPath id = createString("id");

    public final QStudio studio;

    public final QStudioKeyword studioKeyword;

    public QStudioKeywordMap(String variable) {
        this(StudioKeywordMap.class, forVariable(variable), INITS);
    }

    public QStudioKeywordMap(Path<? extends StudioKeywordMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioKeywordMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioKeywordMap(PathMetadata metadata, PathInits inits) {
        this(StudioKeywordMap.class, metadata, inits);
    }

    public QStudioKeywordMap(Class<? extends StudioKeywordMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new QStudio(forProperty("studio"), inits.get("studio")) : null;
        this.studioKeyword = inits.isInitialized("studioKeyword") ? new QStudioKeyword(forProperty("studioKeyword")) : null;
    }

}

