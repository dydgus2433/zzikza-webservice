package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudioBoardRepository extends JpaRepository<StudioBoard, String> {
    Page<StudioBoard> findAllByBoardCategoryCodeEquals(EBoardCategory boardCategory, Pageable pageable);
    @Query(value = "select * from tb_stdo_brd as sb where sb.stdo_brd_id = (select MIN(sb1.stdo_brd_id) from tb_stdo_brd as sb1 where sb1.stdo_brd_id > :brdId and sb1.brd_cate_cd = :brdCateCd)", nativeQuery = true)
    Optional<StudioBoard> findNextBoard(String brdId, String brdCateCd);
    @Query(value = "select * from tb_stdo_brd as sb where sb.stdo_brd_id = (select MAX(sb1.stdo_brd_id) from tb_stdo_brd as sb1 where sb1.stdo_brd_id < :brdId and sb1.brd_cate_cd = :brdCateCd)", nativeQuery = true)
    Optional<StudioBoard> findPrevBoard(@Param("brdId") String brdId, @Param("brdCateCd") String brdCateCd);//, @Param("brdCateCd") EBoardCategory brdCateCd
}
