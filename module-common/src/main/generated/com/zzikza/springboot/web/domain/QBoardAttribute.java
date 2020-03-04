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

