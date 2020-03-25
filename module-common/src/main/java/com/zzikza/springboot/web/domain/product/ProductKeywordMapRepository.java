package com.zzikza.springboot.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductKeywordMapRepository extends JpaRepository<ProductKeywordMap, String> {
    List<ProductKeywordMap> findAllByProduct(Product product);
}
