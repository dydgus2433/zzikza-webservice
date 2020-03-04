package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioQuestionReply is a Querydsl query type for StudioQuestionReply
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioQuestionReply extends EntityPathBase<StudioQuestionReply> {

    private static final long serialVersionUID = 1041306159L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioQuestionReply studioQuestionReply = new QStudioQuestionReply("studioQuestionReply");

    public final StringPath content = createString("content");

    public final StringPath id = createString("id");

    public final QStudioQuestion studioQuestion;

    public QStudioQuestionReply(String variable) {
        this(StudioQuestionReply.class, forVariable(variable), INITS);
    }

    public QStudioQuestionReply(Path<? extends StudioQuestionReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioQuestionReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioQuestionReply(PathMetadata metadata, PathInits inits) {
        this(StudioQuestionReply.class, metadata, inits);
    }

    public QStudioQuestionReply(Class<? extends StudioQuestionReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studioQuestion = inits.isInitialized("studioQuestion") ? new QStudioQuestion(forProperty("studioQuestion"), inits.get("studioQuestion")) : null;
    }

}

