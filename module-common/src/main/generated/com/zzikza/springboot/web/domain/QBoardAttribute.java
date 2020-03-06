package com.zzikza.springboot.web.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardAttribute is a Querydsl query type for BoardAttribute
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBoardAttribute extends EntityPathBase<BoardAttribute> {

    private static final long serialVersionUID = 1805435375L;

    public static final QBoardAttribute boardAttribute = new QBoardAttribute("boardAttribute");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final StringPath title = createString("title");

    public QBoardAttribute(String variable) {
        super(BoardAttribute.class, forVariable(variable));
    }

    public QBoardAttribute(Path<? extends BoardAttribute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardAttribute(PathMetadata metadata) {
        super(BoardAttribute.class, metadata);
    }

}

