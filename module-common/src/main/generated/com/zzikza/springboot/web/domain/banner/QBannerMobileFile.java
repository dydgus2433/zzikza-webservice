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

    public final com.zzikza.springboot.web.domain.QFileAttribute _super = new com.zzikza.springboot.web.domain.QFileAttribute(this);

    public final QBanner banner;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deletedId = _super.deletedId;

    //inherited
    public final StringPath fileExt = _super.fileExt;

    //inherited
    public final StringPath fileName = _super.fileName;

    //inherited
    public final NumberPath<Integer> fileOrder = _super.fileOrder;

    //inherited
    public final StringPath filePath = _super.filePath;

    //inherited
    public final NumberPath<Integer> fileSize = _super.fileSize;

    //inherited
    public final StringPath fileSourceName = _super.fileSourceName;

    //inherited
    public final EnumPath<com.zzikza.springboot.web.domain.enums.EFileStatus> fileStatus = _super.fileStatus;

    //inherited
    public final StringPath fileThumbPath = _super.fileThumbPath;

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
    }

}

