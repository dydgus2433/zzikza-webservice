package com.zzikza.springboot.web.domain.studio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudio is a Querydsl query type for Studio
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudio extends EntityPathBase<Studio> {

    private static final long serialVersionUID = -1804604811L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudio studio = new QStudio("studio");

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

    public final ListPath<com.zzikza.springboot.web.domain.product.Product, com.zzikza.springboot.web.domain.product.QProduct> products = this.<com.zzikza.springboot.web.domain.product.Product, com.zzikza.springboot.web.domain.product.QProduct>createList("products", com.zzikza.springboot.web.domain.product.Product.class, com.zzikza.springboot.web.domain.product.QProduct.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registedDate = _super.registedDate;

    //inherited
    public final StringPath registedId = _super.registedId;

    public final ListPath<com.zzikza.springboot.web.domain.reservation.Reservation, com.zzikza.springboot.web.domain.reservation.QReservation> reservations = this.<com.zzikza.springboot.web.domain.reservation.Reservation, com.zzikza.springboot.web.domain.reservation.QReservation>createList("reservations", com.zzikza.springboot.web.domain.reservation.Reservation.class, com.zzikza.springboot.web.domain.reservation.QReservation.class, PathInits.DIRECT2);

    public final ListPath<StudioBoard, QStudioBoard> studioBoards = this.<StudioBoard, QStudioBoard>createList("studioBoards", StudioBoard.class, QStudioBoard.class, PathInits.DIRECT2);

    public final QStudioDetail studioDetail;

    public final ListPath<StudioFile, QStudioFile> studioFiles = this.<StudioFile, QStudioFile>createList("studioFiles", StudioFile.class, QStudioFile.class, PathInits.DIRECT2);

    public final ListPath<StudioHoliday, QStudioHoliday> studioHolidays = this.<StudioHoliday, QStudioHoliday>createList("studioHolidays", StudioHoliday.class, QStudioHoliday.class, PathInits.DIRECT2);

    public final StringPath studioId = createString("studioId");

    public final ListPath<StudioKeywordMap, QStudioKeywordMap> studioKeywordMaps = this.<StudioKeywordMap, QStudioKeywordMap>createList("studioKeywordMaps", StudioKeywordMap.class, QStudioKeywordMap.class, PathInits.DIRECT2);

    public final ListPath<StudioQuestion, QStudioQuestion> studioQuestions = this.<StudioQuestion, QStudioQuestion>createList("studioQuestions", StudioQuestion.class, QStudioQuestion.class, PathInits.DIRECT2);

    public QStudio(String variable) {
        this(Studio.class, forVariable(variable), INITS);
    }

    public QStudio(Path<? extends Studio> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudio(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudio(PathMetadata metadata, PathInits inits) {
        this(Studio.class, metadata, inits);
    }

    public QStudio(Class<? extends Studio> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studioDetail = inits.isInitialized("studioDetail") ? new QStudioDetail(forProperty("studioDetail")) : null;
    }

}

