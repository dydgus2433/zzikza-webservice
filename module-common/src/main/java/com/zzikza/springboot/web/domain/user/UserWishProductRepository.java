package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.dto.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWishProductRepository extends JpaRepository<UserWishProduct, String> {
    Optional<UserWishProduct> findByUserAndProduct(User user, Product product);

    List<UserWishProduct> findAllByUser(User user);
}
