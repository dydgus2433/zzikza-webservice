package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioBoard is a Querydsl query type for StudioBoard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioBoard extends EntityPathBase<StudioBoard> {

    private static final long serialVersionUID = -227584655L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioBoard studioBoard = new QStudioBoard("studioBoard");

    public final StringPath id = createString("id");

    public final QStudio studio;

    public final ListPath<StudioBoardFile, QStudioBoardFile> studioBoardFiles = this.<StudioBoardFile, QStudioBoardFile>createList("studioBoardFiles", StudioBoardFile.class, QStudioBoardFile.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QStudioBoard(String variable) {
        this(StudioBoard.class, forVariable(variable), INITS);
    }

    public QStudioBoard(Path<? extends StudioBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioBoard(PathMetadata metadata, PathInits inits) {
        this(StudioBoard.class, metadata, inits);
    }

    public QStudioBoard(Class<? extends StudioBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new QStudio(forProperty("studio"), inits.get("studio")) : null;
    }

}

