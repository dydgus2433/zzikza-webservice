package com.zzikza.springboot.web.domain.area;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, String> {
    public List<Area> findBySido(String sido);
}
