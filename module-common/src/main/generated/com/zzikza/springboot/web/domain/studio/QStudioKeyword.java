package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioKeyword is a Querydsl query type for StudioKeyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioKeyword extends EntityPathBase<StudioKeyword> {

    private static final long serialVersionUID = -531886668L;

    public static final QStudioKeyword studioKeyword = new QStudioKeyword("studioKeyword");

    public final StringPath id = createString("id");

    public final StringPath keywordName = createString("keywordName");

    public final ListPath<StudioKeywordMap, QStudioKeywordMap> studioKeywordMaps = this.<StudioKeywordMap, QStudioKeywordMap>createList("studioKeywordMaps", StudioKeywordMap.class, QStudioKeywordMap.class, PathInits.DIRECT2);

    public QStudioKeyword(String variable) {
        super(StudioKeyword.class, forVariable(variable));
    }

    public QStudioKeyword(Path<? extends StudioKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudioKeyword(PathMetadata metadata) {
        super(StudioKeyword.class, metadata);
    }

}

