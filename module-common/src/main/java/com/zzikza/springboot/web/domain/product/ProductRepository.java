package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.studio.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByStudio(Studio toEntity, Pageable pageable);

}
