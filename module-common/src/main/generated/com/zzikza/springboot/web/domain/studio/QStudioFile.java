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

    public final com.zzikza.springboot.web.domain.QFileAttribute _super = new com.zzikza.springboot.web.domain.QFileAttribute(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    //inherited
    public final StringPath fileExt = _super.fileExt;

    public final StringPath fileName = createString("fileName");

    //inherited
    public final NumberPath<Integer> fileOrder = _super.fileOrder;

    //inherited
    public final StringPath filePath = _super.filePath;

    //inherited
    public final NumberPath<Integer> fileSize = _super.fileSize;

    //inherited
    public final StringPath fileSourceName = _super.fileSourceName;

    //inherited
    public final EnumPath<com.zzikza.springboot.web.domain.enums.EFileStatus> fileStatus = _super.fileStatus;

    //inherited
    public final StringPath fileThumbPath = _super.fileThumbPath;

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

