package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    ProductRepository productRepository;

    @Before
    public void before() {

    }

    @After
    public void cleanup() {
//        studioBoardFileRepository.deleteAll();
//        studioBoardRepository.deleteAll();
//        studioRepository.deleteAll();
//        productRepository.deleteAll();
//        userRepository.deleteAll();
//        studioDetailRepository.deleteAll();
    }


    @Test
    @Transactional
    public void 상품추가() {

        Studio studio = studioRepository.findAll().get(0);

        em.persist(studio);
        long size = studio.getProducts().size();

        Product product = Product.builder().price(1000).title("상품굳").productDescription("좋아요 상품").build();

        studio.addProudct(product);
//        em.persist(product);
        em.flush();
        em.clear();
        //when
        Pageable pageable = Pageable.unpaged();
        Page<Product> products = productRepository.findAllByStudio(studio, pageable);
        //then
        assertThat(products.getTotalElements()).isEqualTo(size+1);
    }


}