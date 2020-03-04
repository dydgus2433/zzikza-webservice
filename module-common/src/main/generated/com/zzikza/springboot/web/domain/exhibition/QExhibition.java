package com.zzikza.springboot.web.domain.exhibition;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QExhibition is a Querydsl query type for Exhibition
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExhibition extends EntityPathBase<Exhibition> {

    private static final long serialVersionUID = -15453739L;

    public static final QExhibition exhibition = new QExhibition("exhibition");

    public final StringPath id = createString("id");

    public final StringPath title = createString("title");

    public QExhibition(String variable) {
        super(Exhibition.class, forVariable(variable));
    }

    public QExhibition(Path<? extends Exhibition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExhibition(PathMetadata metadata) {
        super(Exhibition.class, metadata);
    }

}

