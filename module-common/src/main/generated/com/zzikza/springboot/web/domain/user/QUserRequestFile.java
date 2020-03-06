package com.zzikza.springboot.web.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRequestFile is a Querydsl query type for UserRequestFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRequestFile extends EntityPathBase<UserRequestFile> {

    private static final long serialVersionUID = -1659010986L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRequestFile userRequestFile = new QUserRequestFile("userRequestFile");

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

    public final QUserRequest userRequest;

    public QUserRequestFile(String variable) {
        this(UserRequestFile.class, forVariable(variable), INITS);
    }

    public QUserRequestFile(Path<? extends UserRequestFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRequestFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRequestFile(PathMetadata metadata, PathInits inits) {
        this(UserRequestFile.class, metadata, inits);
    }

    public QUserRequestFile(Class<? extends UserRequestFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userRequest = inits.isInitialized("userRequest") ? new QUserRequest(forProperty("userRequest"), inits.get("userRequest")) : null;
    }

}

