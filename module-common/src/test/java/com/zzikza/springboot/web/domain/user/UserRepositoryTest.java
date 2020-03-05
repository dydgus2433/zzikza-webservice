package com.zzikza.springboot.web.domain.user;


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
    @PersistenceContext
    private EntityManager em;

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    @Transactional
    public void Attribute_컨버터() {
        User user = User.builder().userId("tester").build();

        em.persist(user);
        em.flush();
        em.clear();

        // native query를 이용하여 gender = 1 조회
        Query query = em.createNativeQuery("select * from tb_user where user_id = :user_id", User.class);
        query.setParameter("user_id", "user001");
        List<User> list = query.getResultList();

        // 검증
        String result = list.get(0).getUserId();
        assertThat("user001").isEqualTo(result);
    }

}