package com.zzikza.springboot.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductFileRepository extends JpaRepository<ProductFile, String> {
    List<ProductFile> findAllByProduct(Product product);
}
