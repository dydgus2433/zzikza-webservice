package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWishProductRepository extends JpaRepository<UserWishProduct, String> {
    Optional<UserWishProduct> findByUserAndProduct(User user, Product product);
}
