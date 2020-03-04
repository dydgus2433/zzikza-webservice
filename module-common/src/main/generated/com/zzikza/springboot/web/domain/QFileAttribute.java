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

