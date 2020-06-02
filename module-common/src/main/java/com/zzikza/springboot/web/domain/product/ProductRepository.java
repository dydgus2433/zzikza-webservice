package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.studio.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByStudio(Studio toEntity, Pageable pageable);
    Page<Product> findBySearchTextContains(String searchText, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findBySearchTextContainsAndExhibition(String searchText, Exhibition exhibition, Pageable pageable);
//    Page<Product> findBySearchTextContains(String searchText, Pageable pageable , Sort sort);
//    Page<Product> findAll(Pageable pageable , Sort sort);
}
