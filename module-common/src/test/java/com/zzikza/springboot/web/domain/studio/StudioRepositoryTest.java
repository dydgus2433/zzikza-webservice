package com.zzikza.springboot.web.domain.studio;


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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudioRepositoryTest {
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
        studioBoardFileRepository.deleteAll();
        studioBoardRepository.deleteAll();
        studioRepository.deleteAll();
        productRepository.deleteAll();
    }

    @After
    public void cleanup() {
//        studioBoardFileRepository.deleteAll();
//        studioBoardRepository.deleteAll();
//        studioRepository.deleteAll();
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
}