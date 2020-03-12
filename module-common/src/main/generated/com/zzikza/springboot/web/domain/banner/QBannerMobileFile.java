package com.zzikza.springboot.web.domain.banner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBannerMobileFile is a Querydsl query type for BannerMobileFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBannerMobileFile extends EntityPathBase<BannerMobileFile> {

    private static final long serialVersionUID = 636368083L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBannerMobileFile bannerMobileFile = new QBannerMobileFile("bannerMobileFile");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    public final QBanner banner;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    public final com.zzikza.springboot.web.domain.QFileAttribute file;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedId = _super.modifiedId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public QBannerMobileFile(String variable) {
        this(BannerMobileFile.class, forVariable(variable), INITS);
    }

    public QBannerMobileFile(Path<? extends BannerMobileFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBannerMobileFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBannerMobileFile(PathMetadata metadata, PathInits inits) {
        this(BannerMobileFile.class, metadata, inits);
    }

    public QBannerMobileFile(Class<? extends BannerMobileFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.banner = inits.isInitialized("banner") ? new QBanner(forProperty("banner")) : null;
        this.file = inits.isInitialized("file") ? new com.zzikza.springboot.web.domain.QFileAttribute(forProperty("file")) : null;
    }

}

