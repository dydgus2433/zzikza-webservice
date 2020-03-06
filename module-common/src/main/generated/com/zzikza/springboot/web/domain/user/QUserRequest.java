package com.zzikza.springboot.web.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRequest is a Querydsl query type for UserRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRequest extends EntityPathBase<UserRequest> {

    private static final long serialVersionUID = 2025358266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRequest userRequest = new QUserRequest("userRequest");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final QUser user;

    public final ListPath<UserRequestFile, QUserRequestFile> userRequestFiles = this.<UserRequestFile, QUserRequestFile>createList("userRequestFiles", UserRequestFile.class, QUserRequestFile.class, PathInits.DIRECT2);

    public final ListPath<UserRequestProduct, QUserRequestProduct> userRequestProducts = this.<UserRequestProduct, QUserRequestProduct>createList("userRequestProducts", UserRequestProduct.class, QUserRequestProduct.class, PathInits.DIRECT2);

    public QUserRequest(String variable) {
        this(UserRequest.class, forVariable(variable), INITS);
    }

    public QUserRequest(Path<? extends UserRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRequest(PathMetadata metadata, PathInits inits) {
        this(UserRequest.class, metadata, inits);
    }

    public QUserRequest(Class<? extends UserRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

