package com.zzikza.springboot.web.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRequestProduct is a Querydsl query type for UserRequestProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRequestProduct extends EntityPathBase<UserRequestProduct> {

    private static final long serialVersionUID = -861987883L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRequestProduct userRequestProduct = new QUserRequestProduct("userRequestProduct");

    public final StringPath id = createString("id");

    public final com.zzikza.springboot.web.domain.product.QProduct product;

    public final QUserRequest userRequest;

    public QUserRequestProduct(String variable) {
        this(UserRequestProduct.class, forVariable(variable), INITS);
    }

    public QUserRequestProduct(Path<? extends UserRequestProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRequestProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRequestProduct(PathMetadata metadata, PathInits inits) {
        this(UserRequestProduct.class, metadata, inits);
    }

    public QUserRequestProduct(Class<? extends UserRequestProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.zzikza.springboot.web.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.userRequest = inits.isInitialized("userRequest") ? new QUserRequest(forProperty("userRequest"), inits.get("userRequest")) : null;
    }

}

