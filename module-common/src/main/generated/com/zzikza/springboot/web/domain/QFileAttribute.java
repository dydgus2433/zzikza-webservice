package com.zzikza.springboot.web.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileAttribute is a Querydsl query type for FileAttribute
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QFileAttribute extends EntityPathBase<FileAttribute> {

    private static final long serialVersionUID = -1829739065L;

    public static final QFileAttribute fileAttribute = new QFileAttribute("fileAttribute");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final StringPath fileExt = createString("fileExt");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> fileOrder = createNumber("fileOrder", Integer.class);

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Integer> fileSize = createNumber("fileSize", Integer.class);

    public final StringPath fileSourceName = createString("fileSourceName");

    public final EnumPath<com.zzikza.springboot.web.domain.enums.EFileStatus> fileStatus = createEnum("fileStatus", com.zzikza.springboot.web.domain.enums.EFileStatus.class);

    public final StringPath fileThumbPath = createString("fileThumbPath");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public QFileAttribute(String variable) {
        super(FileAttribute.class, forVariable(variable));
    }

    public QFileAttribute(Path<? extends FileAttribute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileAttribute(PathMetadata metadata) {
        super(FileAttribute.class, metadata);
    }

}

