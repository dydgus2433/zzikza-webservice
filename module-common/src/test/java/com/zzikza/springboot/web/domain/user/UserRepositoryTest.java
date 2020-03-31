package com.zzikza.springboot.web.domain.user;


import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioDetail;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    StudioRepository studioRepository;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void Attribute_컨버터() {
        User user = User.builder().userId("tester").build();

        em.persist(user);
        em.flush();
        em.clear();

        // native query를 이용하여 gender = 1 조회
        Query query = em.createNativeQuery("select * from tb_user where user_id = :user_id", User.class);
        query.setParameter("user_id", "tester");
        List<User> list = query.getResultList();

        // 검증
        String result = list.get(0).getUserId();
        assertThat("tester").isEqualTo(result);

    }

    @Test
    @Transactional
    public void 사용자_요청하기() {

        //given
        User user = User.builder().userId("tester01").build();
        UserRequest userRequest = UserRequest.builder().content("제품사진 10만원에 가능?").build();
        em.persist(userRequest);
        em.persist(user);
        userRequest.addUserRequestFile(UserRequestFile.builder().fileName("file.mvc").build());

        user.addUserRequest(userRequest);

        StudioDetail studioDetail = StudioDetail.builder().studioDescription("상세").build();
        em.persist(studioDetail);
        Studio studio = Studio.builder()
                .studioId("test")
                .studioDetail(studioDetail)
                .build();
        em.persist(studio);
        Product product = Product.builder().build();
        em.persist(product);
        studio.addProudct(product);

        userRequest.addUserRequestProduct(UserRequestProduct.builder().product(product).build());
        em.persist(userRequest);
        em.flush();
        em.clear();
        //when
        Studio expected = studioRepository.findById(studio.getId()).orElseThrow(IllegalAccessError::new);
        //then
        assertThat(userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다. id = ")).getUserRequests().size()).isEqualTo(1);
        assertThat(userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다. id = ")).getUserRequests().get(0).getUserRequestProducts().get(0).getProduct()).isEqualTo(expected.getProducts().get(0));
    }

    @Test
    @Transactional
    public void 찜하기() {

        //given
        User user = userRepository.findAll().get(0);
        em.persist(user);

        StudioDetail studioDetail = StudioDetail.builder().studioDescription("상세").build();
        em.persist(studioDetail);
        Studio studio = studioRepository.findAll().get(0);
        em.persist(studio);
        Product product = Product.builder().build();
        Product product1 = Product.builder().build();
        studio.addProudct(product);
        studio.addProudct(product1);
        em.persist(product);
        em.persist(product1);

        UserWishProduct userWishProduct = UserWishProduct.builder().build();
        userWishProduct.setProduct(product);
        em.persist(userWishProduct);
        //when
        user.addWishProduct(userWishProduct);
        em.flush();
        em.clear();
        //then
        User expectedUser = userRepository.findById(user.getId()).orElseThrow(IllegalAccessError::new);
        Studio expectedStudio = studioRepository.findById(studio.getId()).orElseThrow(IllegalAccessError::new);
        assertThat(expectedUser.getUserWishProducts().size()).isEqualTo(1);
        assertThat(expectedStudio.getProducts().size()).isEqualTo(2);

    }

}