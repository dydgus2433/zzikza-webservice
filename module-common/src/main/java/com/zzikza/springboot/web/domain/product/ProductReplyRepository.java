package com.zzikza.springboot.web.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReplyRepository extends JpaRepository<ProductReply, String> {

    Page<ProductReply> findAllByProduct(Product product, Pageable pageable);

    List<ProductReply> findAllByProduct(Product product);
}
