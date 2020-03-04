package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioQuestion is a Querydsl query type for StudioQuestion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioQuestion extends EntityPathBase<StudioQuestion> {

    private static final long serialVersionUID = -997636741L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioQuestion studioQuestion = new QStudioQuestion("studioQuestion");

    public final StringPath content = createString("content");

    public final StringPath id = createString("id");

    public final QStudio studio;

    public final ListPath<StudioQuestionReply, QStudioQuestionReply> studioQuestionReplies = this.<StudioQuestionReply, QStudioQuestionReply>createList("studioQuestionReplies", StudioQuestionReply.class, QStudioQuestionReply.class, PathInits.DIRECT2);

    public final com.zzikza.springboot.web.domain.user.QUser user;

    public QStudioQuestion(String variable) {
        this(StudioQuestion.class, forVariable(variable), INITS);
    }

    public QStudioQuestion(Path<? extends StudioQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioQuestion(PathMetadata metadata, PathInits inits) {
        this(StudioQuestion.class, metadata, inits);
    }

    public QStudioQuestion(Class<? extends StudioQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new QStudio(forProperty("studio"), inits.get("studio")) : null;
        this.user = inits.isInitialized("user") ? new com.zzikza.springboot.web.domain.user.QUser(forProperty("user")) : null;
    }

}

