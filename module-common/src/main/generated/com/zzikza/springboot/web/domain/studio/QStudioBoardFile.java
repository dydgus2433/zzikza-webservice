package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioBoardFile is a Querydsl query type for StudioBoardFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudioBoardFile extends EntityPathBase<StudioBoardFile> {

    private static final long serialVersionUID = -686383475L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioBoardFile studioBoardFile = new QStudioBoardFile("studioBoardFile");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final com.zzikza.springboot.web.domain.QFileAttribute file;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final QStudioBoard studioBoard;

    public QStudioBoardFile(String variable) {
        this(StudioBoardFile.class, forVariable(variable), INITS);
    }

    public QStudioBoardFile(Path<? extends StudioBoardFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioBoardFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioBoardFile(PathMetadata metadata, PathInits inits) {
        this(StudioBoardFile.class, metadata, inits);
    }

    public QStudioBoardFile(Class<? extends StudioBoardFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new com.zzikza.springboot.web.domain.QFileAttribute(forProperty("file")) : null;
        this.studioBoard = inits.isInitialized("studioBoard") ? new QStudioBoard(forProperty("studioBoard"), inits.get("studioBoard")) : null;
    }

}

