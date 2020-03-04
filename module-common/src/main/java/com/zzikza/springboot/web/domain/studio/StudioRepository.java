package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, String> {
//    @Query("SELECT s FROM Studio s ORDER BY s.id DESC")
//    List<Studio> findAllDesc();
}
