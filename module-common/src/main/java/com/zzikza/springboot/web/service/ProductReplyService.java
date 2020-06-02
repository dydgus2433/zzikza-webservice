package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductReply;
import com.zzikza.springboot.web.domain.product.ProductReplyRepository;
import com.zzikza.springboot.web.domain.product.ProductRepository;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.dto.ProductReplyRequestDto;
import com.zzikza.springboot.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductReplyService {

    final ProductReplyRepository productReplyRepository;
    final ProductRepository productRepository;
    final UserRepository userRepository;

    public void insertProductReply(ProductReplyRequestDto productReplyRequestDto, UserResponseDto userResponseDto) {
        User user  = userRepository.findById(userResponseDto.getId()).orElseThrow(()-> new IllegalArgumentException("사용자 정보가 없습니다."));
        Product product  = productRepository.findById(productReplyRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("상품 정보가 없습니다."));
        ProductReply productReply = ProductReply.builder()
                .user(user)
                .product(product)
                .grade(productReplyRequestDto.getGrade())
                .content(productReplyRequestDto.getContent())
                .build();
        productReplyRepository.save(productReply);
    }

    public Page<ProductReply> findAllByProduct(ProductReplyRequestDto productReplyRequestDto, Pageable pageable) {
        Product product = productRepository.findById(productReplyRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("상품정보가 없습니다."));
        return productReplyRepository.findAllByProduct(product, pageable);
    }
}
