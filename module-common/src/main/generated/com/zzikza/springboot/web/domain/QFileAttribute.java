package com.zzikza.springboot.web.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileAttribute is a Querydsl query type for FileAttribute
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QFileAttribute extends BeanPath<FileAttribute> {

    private static final long serialVersionUID = -1829739065L;

    public static final QFileAttribute fileAttribute = new QFileAttribute("fileAttribute");

    public final StringPath fileExt = createString("fileExt");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> fileOrder = createNumber("fileOrder", Integer.class);

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Integer> fileSize = createNumber("fileSize", Integer.class);

    public final StringPath fileSourceName = createString("fileSourceName");

    public final EnumPath<com.zzikza.springboot.web.domain.enums.EFileStatus> fileStatus = createEnum("fileStatus", com.zzikza.springboot.web.domain.enums.EFileStatus.class);

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

