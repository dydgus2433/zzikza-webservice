package com.zzikza.springboot.web.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReplyRepository extends JpaRepository<ProductReply, String> {

    Page<ProductReply> findAllByProduct(Product product, Pageable pageable);
}
