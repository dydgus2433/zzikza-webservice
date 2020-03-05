package com.zzikza.springboot.web.domain.policy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPolicy is a Querydsl query type for Policy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPolicy extends EntityPathBase<Policy> {

    private static final long serialVersionUID = -1147426059L;

    public static final QPolicy policy = new QPolicy("policy");

    public final StringPath content = createString("content");

    public final StringPath id = createString("id");

    public final StringPath termCode = createString("termCode");

    public QPolicy(String variable) {
        super(Policy.class, forVariable(variable));
    }

    public QPolicy(Path<? extends Policy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPolicy(PathMetadata metadata) {
        super(Policy.class, metadata);
    }

}

