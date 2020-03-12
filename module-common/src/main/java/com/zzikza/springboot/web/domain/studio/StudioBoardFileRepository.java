package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioBoardFileRepository extends JpaRepository<StudioBoardFile,String> {
    List<StudioBoardFile> findAllByStudioBoard(StudioBoard studioBoard);
}
