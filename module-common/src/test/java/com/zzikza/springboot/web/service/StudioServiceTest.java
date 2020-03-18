package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.studio.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudioServiceTest {

    @Autowired
    StudioRepository studioRepository;
    @Autowired
    StudioKeywordMapRepository studioKeywordMapRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void 스튜디오키워드_수정() {

        Studio studio = studioRepository.findById("STD0000000354").orElseThrow(IllegalArgumentException::new);

        em.persist(studio);
        StudioKeyword keyword = StudioKeyword.builder().keywordName("스튜디오키워드_수정").build();
        em.persist(keyword);
        StudioKeywordMap studioKeywordMap = StudioKeywordMap.builder().studioKeyword(keyword).build();
        em.persist(studioKeywordMap);
        studio.addStudioKeywordMap(studioKeywordMap);

        assertThat(studio.getStudioKeywordMaps().size()).isEqualTo(1);
        studioKeywordMapRepository.deleteInBatch(studio.getStudioKeywordMaps());
        em.flush();
        em.clear();

        Studio studio1 = studioRepository.findById("STD0000000354").orElseThrow(IllegalArgumentException::new);
        em.persist(studio1);
        assertThat(studio1.getStudioKeywordMaps().size()).isEqualTo(0);
        studio1.addStudioKeywordMap(studioKeywordMap);

        assertThat(studio1.getStudioKeywordMaps().size()).isEqualTo(1);



    }


    @Test
    @Transactional
    public void 스튜디오키워드_삭제() {

        Studio studio = studioRepository.findById("STD0000000344").orElseThrow(IllegalArgumentException::new);

        em.persist(studio);
        StudioKeyword keyword = StudioKeyword.builder().keywordName("스튜디오키워드_수정").build();
        em.persist(keyword);
        StudioKeywordMap studioKeywordMap = StudioKeywordMap.builder().studioKeyword(keyword).build();
        em.persist(studioKeywordMap);
        studio.addStudioKeywordMap(studioKeywordMap);

        assertThat(studio.getStudioKeywordMaps().size()).isEqualTo(1);

        studioKeywordMapRepository.deleteInBatch(studio.getStudioKeywordMaps());
        em.flush();
        em.clear();


        Studio studio1 = studioRepository.findById("STD0000000344").orElseThrow(IllegalArgumentException::new);

        assertThat(studio1.getStudioKeywordMaps().size()).isEqualTo(0);
//        studio.addStudioKeywordMap(studioKeywordMap);
//
//        assertThat(studio.getStudioKeywordMaps().size()).isEqualTo(1);
//        em.flush();
//        em.clear();


    }
}