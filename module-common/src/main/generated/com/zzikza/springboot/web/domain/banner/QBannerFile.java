package com.zzikza.springboot.web.domain.banner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBannerFile is a Querydsl query type for BannerFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBannerFile extends EntityPathBase<BannerFile> {

    private static final long serialVersionUID = 1204610257L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBannerFile bannerFile = new QBannerFile("bannerFile");

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

    public QBannerFile(String variable) {
        this(BannerFile.class, forVariable(variable), INITS);
    }

    public QBannerFile(Path<? extends BannerFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBannerFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBannerFile(PathMetadata metadata, PathInits inits) {
        this(BannerFile.class, metadata, inits);
    }

    public QBannerFile(Class<? extends BannerFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.banner = inits.isInitialized("banner") ? new QBanner(forProperty("banner")) : null;
        this.file = inits.isInitialized("file") ? new com.zzikza.springboot.web.domain.QFileAttribute(forProperty("file")) : null;
    }

}

