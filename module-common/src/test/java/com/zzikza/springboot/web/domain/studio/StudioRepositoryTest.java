package com.zzikza.springboot.web.domain.studio;


import com.zzikza.springboot.web.domain.enums.EDateStatus;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductRepository;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudioRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    StudioBoardRepository studioBoardRepository;

    @Autowired
    StudioBoardFileRepository studioBoardFileRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StudioDetailRepository studioDetailRepository;

    @Autowired
    StudioQuestionRepository studioQuestionRepository;

    @Autowired
    StudioQuestionReplyRepository studioQuestionReplyRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void before() {

    }

    @After
    public void cleanup() {
        studioBoardFileRepository.deleteAll();
        studioBoardRepository.deleteAll();
        studioRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        studioDetailRepository.deleteAll();
    }

    @Test
    public void 스튜디오_가입_디테일포함() {
        String studioId = "test1";
        //given
        StudioDetail detail = StudioDetail.builder().studioDescription("설명").build();
        studioDetailRepository.save(detail);
        Studio exStudio= Studio.builder().studioId(studioId).studioDetail(detail).build();
        studioRepository.save(exStudio);
        //when
        List<Studio> studios = studioRepository.findAll();
        //then
        Studio studio = studioRepository.findById(exStudio.getId()).orElseThrow(()->new IllegalArgumentException("아이디가 일치하지 않습니다."));
        assertThat(studio.getStudioId()).isEqualTo(studioId);

    }

    @Test
    public void 스튜디오_글쓰기_디테일포함() {

        //given

        StudioDetail detail = StudioDetail.builder().studioDescription("설명").build();
        studioDetailRepository.save(detail);

        String studioId = "test";
        Studio studio = Studio.builder().studioId(studioId).studioDetail(detail).build();

        StudioBoard studioBoard = StudioBoard.builder().title("board").build();
        studio.addStudioBoard(studioBoard);
        StudioBoardFile studioBoardFile = StudioBoardFile.builder().fileName("file").build();
        studioBoard.addStudioBoardFile(studioBoardFile);


        studioRepository.save(studio);
        studioBoardRepository.save(studioBoard);
        studioBoardFileRepository.save(studioBoardFile);

        //when
        String id = studio.getId();
        Studio expectStudio = studioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("저장한 값이 아닙니다"));


        StudioBoard expectStudioBoards = studioBoardRepository.findById(studioBoard.getId()).orElseThrow(() -> new IllegalArgumentException("저장한 값이 아닙니다"));
        StudioBoardFile expectedStudioBoardFiles = studioBoardFileRepository.findById(studioBoardFile.getId()).orElseThrow(() -> new IllegalArgumentException("저장한 값이 아닙니다"));
        //then
        assertThat(expectStudioBoards.getTitle()).isEqualTo(studioBoard.getTitle());
        assertThat(expectedStudioBoardFiles.getFileName()).isEqualTo(studioBoardFile.getFileName());
    }

    @Test
    public void 스튜디오_상품추가() {

        //given
        StudioDetail detail = StudioDetail.builder().studioDescription("설명").build();
        studioDetailRepository.save(detail);

        String studioId = "test";
        Studio studio = Studio.builder().studioId(studioId).studioDetail(detail).build();
        studioRepository.save(studio);

        String productTitle = "상품제목0";
        Product product = Product.builder().title(productTitle).studio(studio).build();
        productRepository.save(product);
        //when

        //then
        Product expectedProduct = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalArgumentException("저장한 값이 아닙니다"));
        assertThat(expectedProduct.getTitle()).isEqualTo(productTitle);
        assertThat(expectedProduct.getStudio().getStudioId()).isEqualTo(studioId);

    }

    @Test
    public void 스튜디오_질문() {

        //given
        User user = User.builder().userId("user001").build();
        userRepository.save(user);


        StudioDetail detail = StudioDetail.builder().studioDescription("얍얍2").build();
        studioDetailRepository.save(detail);
        Studio studio = Studio.builder().studioId("tester12345").studioDetail(detail).build();


        StudioQuestion studioQuestion = StudioQuestion.builder().content("질문남깁니다.").user(user).build();
        studio.addStudioQuestion(studioQuestion);
        studioRepository.save(studio);
        studioQuestionRepository.save(studioQuestion);

        //when
        StudioQuestion expectedQuestion = studioQuestionRepository.findById(studioQuestion.getId()).orElseThrow(()->new IllegalArgumentException("질문X"));

        //then
        assertThat(expectedQuestion.getContent()).isEqualTo("질문남깁니다.");
    }

    @Test
    public void 스튜디오_유저질문_답변() {

        //given
        User user = User.builder().userId("user0012").build();
        userRepository.save(user);

        StudioDetail detail = StudioDetail.builder().studioDescription("얍얍").build();
        studioDetailRepository.save(detail);
        Studio studio = Studio.builder().studioId("tester1234").studioDetail(detail).build();


        StudioQuestion studioQuestion = StudioQuestion.builder().content("질문남깁니다.").user(user).build();
        studio.addStudioQuestion(studioQuestion);

        StudioQuestionReply reply = StudioQuestionReply.builder().content("답변입니다.").build();
        studioQuestion.addQuestionReply(reply);
        studioRepository.save(studio);
        studioQuestionRepository.save(studioQuestion);
        studioQuestionReplyRepository.save(reply);

        //when
        Studio expectedStudio = studioRepository.getOne(studio.getId());
        StudioQuestion expectedQuestion = studioQuestionRepository.findById(studioQuestion.getId()).orElseThrow(()->new IllegalArgumentException("질문X"));
        StudioQuestionReply expectedQuestionReply = studioQuestionReplyRepository.findById(reply.getId()).orElseThrow(()->new IllegalArgumentException("답변X"));

        //then
        assertThat(expectedQuestion.getContent()).isEqualTo("질문남깁니다.");
        assertThat(expectedQuestionReply.getContent()).isEqualTo("답변입니다.");
    }

    @Test
    @Transactional
    public void 키워드관계설정() {

        //given
        StudioDetail detail = StudioDetail.builder().studioDescription("얍얍").build();
        em.persist(detail);
        Studio studio = Studio.builder().studioId("tester1234").studioDetail(detail).build();
        em.persist(studio);
        Studio studioTest = Studio.builder().studioId("tester12345").studioDetail(detail).build();
        em.persist(studioTest);
        /*
        sudo
        키워드 여러개 만들고
        스튜디오와 매핑
        여러개 모두 있는지 확인
        */
        StudioKeyword keyword1 = StudioKeyword.builder().keywordName("기업사진").build();
        StudioKeyword keyword2 = StudioKeyword.builder().keywordName("상품사진").build();
        StudioKeyword keyword3 = StudioKeyword.builder().keywordName("전문작가").build();
        StudioKeyword keyword4 = StudioKeyword.builder().keywordName("전문작가1").build();
        em.persist(keyword1);
        em.persist(keyword2);
        em.persist(keyword3);
        em.persist(keyword4);
        StudioKeywordMap studioKeyword1 = StudioKeywordMap.builder().studioKeyword(keyword1).build();
        StudioKeywordMap studioKeyword2 = StudioKeywordMap.builder().studioKeyword(keyword2).build();
        StudioKeywordMap studioKeyword3 = StudioKeywordMap.builder().studioKeyword(keyword3).build();
        StudioKeywordMap studioKeyword4 = StudioKeywordMap.builder().studioKeyword(keyword4).build();

        studio.addStudioKeywordMap(studioKeyword1);
        studio.addStudioKeywordMap(studioKeyword2);
        studio.addStudioKeywordMap(studioKeyword3);

        studioTest.addStudioKeywordMap(studioKeyword1);
        studioTest.addStudioKeywordMap(studioKeyword2);
        studioTest.addStudioKeywordMap(studioKeyword3);
        studioTest.addStudioKeywordMap(studioKeyword4);

        em.persist(studioKeyword1);
        em.persist(studioKeyword2);
        em.persist(studioKeyword3);
        em.persist(studioKeyword4);
        //when
        //String query = "select m from Member m inner join m.team t where t.name = :teamName";
//        Query query = em.createNativeQuery("select * from tb_stdo a , tb_keyword_map b where a.stdo_seq = :stdo_seq", Studio.class);
        Studio studio1 =studioRepository.findById(studio.getId()).orElseThrow(()->new IllegalArgumentException("스튜디오"));
        Studio studioTest1 =studioRepository.findById(studioTest.getId()).orElseThrow(()->new IllegalArgumentException("스튜디오"));
        //then
        assertThat(studio1.getStudioKeywordMaps().size()).isEqualTo(3);
        assertThat(studioTest1.getStudioKeywordMaps().size()).isEqualTo(4);


        em.clear();
    }

    @Test
    @Transactional
    public void 휴일추가() {

        //given
        StudioDetail detail = StudioDetail.builder().studioDescription("얍얍").build();
        em.persist(detail);
        Studio studio = Studio.builder().studioId("tester1234").studioDetail(detail).build();

        //when
        StudioHoliday studioHoliday = StudioHoliday.builder().dateCode(EDateStatus.DAY).dateValue("2019-07-19").build();
        StudioHoliday studioHolidayWeek = StudioHoliday.builder().dateCode(EDateStatus.WEEK).dateValue("1").build();
        StudioHoliday studioHolidayWeek1 = StudioHoliday.builder().dateCode(EDateStatus.WEEK).dateValue("1").build();
//        em.persist(studioHoliday);
//        em.persist(studioHolidayWeek);
//        em.persist(studioHolidayWeek1);
        studio.addStudioHoliday(studioHoliday);
        studio.addStudioHoliday(studioHolidayWeek);
        studio.addStudioHoliday(studioHolidayWeek1);
        em.persist(studio);
        //then
        Studio studio1 =studioRepository.findById(studio.getId()).orElseThrow(()->new IllegalArgumentException("스튜디오"));
        List<StudioHoliday> holidays = studio1.getStudioHolidays();
        assertThat(studio1.getStudioHolidays().size()).isEqualTo(2);
    }

    @Test
    public void 스튜디오_유저_상품댓글() {

        /*
        //현재 시스템에도 아직 스튜디오 대댓글 미구현
        스튜디오 - 상품 - 유저댓글 - 스튜디오 대댓글
                       - 스튜디오 댓글
        스튜디오가 상품등록
        유저가 상품후기
        스튜디오가 답글
        유저가 또다시 상품

         */
        //given

        //when

        //then
    }
}