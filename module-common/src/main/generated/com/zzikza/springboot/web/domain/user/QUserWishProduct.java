package com.zzikza.springboot.web.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserWishProduct is a Querydsl query type for UserWishProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserWishProduct extends EntityPathBase<UserWishProduct> {

    private static final long serialVersionUID = -536609293L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserWishProduct userWishProduct = new QUserWishProduct("userWishProduct");

    public final StringPath id = createString("id");

    public final com.zzikza.springboot.web.domain.product.QProduct product;

    public final QUser user;

    public QUserWishProduct(String variable) {
        this(UserWishProduct.class, forVariable(variable), INITS);
    }

    public QUserWishProduct(Path<? extends UserWishProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserWishProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserWishProduct(PathMetadata metadata, PathInits inits) {
        this(UserWishProduct.class, metadata, inits);
    }

    public QUserWishProduct(Class<? extends UserWishProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.zzikza.springboot.web.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

