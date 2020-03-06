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

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    public final com.zzikza.springboot.web.domain.product.QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

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

