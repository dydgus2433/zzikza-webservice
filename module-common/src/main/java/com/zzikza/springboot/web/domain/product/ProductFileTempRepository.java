package com.zzikza.springboot.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFileTempRepository extends JpaRepository<ProductFileTemp, String> {
    List<ProductFileTemp> findAllByTempKey(String tempKey);
}
