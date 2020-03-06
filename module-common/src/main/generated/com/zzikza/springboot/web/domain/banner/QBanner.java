package com.zzikza.springboot.web.domain.banner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = 1823169973L;

    public static final QBanner banner = new QBanner("banner");

    public final com.zzikza.springboot.web.domain.QBaseTimeEntity _super = new com.zzikza.springboot.web.domain.QBaseTimeEntity(this);

    public final ListPath<BannerFile, QBannerFile> bannerFiles = this.<BannerFile, QBannerFile>createList("bannerFiles", BannerFile.class, QBannerFile.class, PathInits.DIRECT2);

    public final ListPath<BannerMobileFile, QBannerMobileFile> bannerMobileFiles = this.<BannerMobileFile, QBannerMobileFile>createList("bannerMobileFiles", BannerMobileFile.class, QBannerMobileFile.class, PathInits.DIRECT2);

    public final EnumPath<com.zzikza.springboot.web.domain.enums.EBannerCategory> category = createEnum("category", com.zzikza.springboot.web.domain.enums.EBannerCategory.class);

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

    public final EnumPath<com.zzikza.springboot.web.domain.enums.EShowStatus> showStatus = createEnum("showStatus", com.zzikza.springboot.web.domain.enums.EShowStatus.class);

    public final EnumPath<com.zzikza.springboot.web.domain.enums.ETargetCategory> targetCategory = createEnum("targetCategory", com.zzikza.springboot.web.domain.enums.ETargetCategory.class);

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public QBanner(String variable) {
        super(Banner.class, forVariable(variable));
    }

    public QBanner(Path<? extends Banner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBanner(PathMetadata metadata) {
        super(Banner.class, metadata);
    }

}

