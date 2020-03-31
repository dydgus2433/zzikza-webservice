package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.pay.FinalPaymentPrice;
import com.zzikza.springboot.web.domain.pay.Payment;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductRepository;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.reservation.ReservationRepository;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    StudioRepository studioRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudioKeywordMapRepository studioKeywordMapRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void 일반상품_구매하기() {

        /*
        유저 생성
        스튜디오 등록
        스튜디오 상품 등록
        유저 상품 구매(일반상품)
        */
        //given
        User user = User.builder().userId("t").build();
        em.persist(user);
        StudioDetail detail = StudioDetail.builder().studioDescription("묘사").build();
        em.persist(detail);
        Studio studio = Studio.builder().studioId("d").studioDetail(detail).build();

        em.persist(studio);
        Product product = Product.builder().title("상품1").price(1000).build();
        em.persist(product);
        studio.addProudct(product);

        FinalPaymentPrice finalPaymentPrice = FinalPaymentPrice.builder().product(product).build();
        em.persist(finalPaymentPrice);
        //when 유저 결제
        Payment payment = Payment.builder().name("결제").finalPaymentPrice(finalPaymentPrice).build();
        Reservation reservation = Reservation.builder().scheduleName("예약5명요").build();
        reservation.setPayment(payment);
        em.persist(payment);
        em.persist(reservation);

        //예약내역
        user.addReservation(reservation);
        studio.addReservation(reservation);

        em.flush();
        em.clear();
        /*
        예약된 상품 등록 아 상품이 아니라 예약이 등록되는 거구나
         */
        Studio expectedStudio = studioRepository.findById(studio.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        User expectedUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        //then
        assertThat(expectedStudio.getReservations().get(0)).isEqualTo(expectedUser.getReservations().get(0));
        assertThat(expectedStudio.getReservations().get(0).getPayment().getRealPrice()).isEqualTo(expectedUser.getReservations().get(0).getPayment().getRealPrice());
    }

    @Test
    @Transactional
    public void 전시회상품_구매하기() {

        /*
        유저 생성
        스튜디오 등록
        스튜디오 상품 등록
        유저 상품 구매(전시회)
          */
        //given
        User user = User.builder().userId("t").build();
        em.persist(user);
        StudioDetail detail = StudioDetail.builder().studioDescription("묘사").build();
        em.persist(detail);
        Studio studio = Studio.builder().studioId("d").studioDetail(detail).build();

        em.persist(studio);
        Product product = Product.builder().title("상품1").price(1000).build();
        em.persist(product);
        studio.addProudct(product);
        Exhibition exhibition = Exhibition.builder().title("특별전시회").build();
        em.persist(exhibition);
        Sale sale = Sale.builder().saleName("특가").salePrice(500).build();
        em.persist(sale);
        exhibition.addSale(sale);
        //적어도 여기에서는 필요없음
        FinalPaymentPrice finalPaymentPrice = FinalPaymentPrice.builder().product(product).sale(sale).build();
        em.persist(finalPaymentPrice);
        //when 유저 결제
        Payment payment = Payment.builder().name("결제").finalPaymentPrice(finalPaymentPrice).build();
        Reservation reservation = Reservation.builder().scheduleName("예약5명요").build();
        reservation.setPayment(payment);
        em.persist(payment);
        em.persist(reservation);

        //예약내역
        user.addReservation(reservation);
        studio.addReservation(reservation);

        em.flush();
        em.clear();
        /*
        예약된 상품 등록 아 상품이 아니라 예약이 등록되는 거구나
         */
        Studio expectedStudio = studioRepository.findById(studio.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        User expectedUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        //then
        assertThat(expectedStudio.getReservations().get(0)).isEqualTo(expectedUser.getReservations().get(0));
        assertThat(expectedStudio.getReservations().get(0).getPayment().getRealPrice()).isEqualTo(expectedUser.getReservations().get(0).getPayment().getRealPrice());
        assertThat(expectedStudio.getReservations().get(0).getPayment().getRealPrice()).isEqualTo(500);

    }


    @Test
    @Transactional
    public void 전시회상품_구매하기_전시회할인가가_더비쌀경우() {

        /*
        유저 생성
        스튜디오 등록
        스튜디오 상품 등록
        유저 상품 구매(전시회)
          */
        //given
        User user = User.builder().userId("t").build();
        em.persist(user);
        StudioDetail detail = StudioDetail.builder().studioDescription("묘사").build();
        em.persist(detail);
        Studio studio = Studio.builder().studioId("d").studioDetail(detail).build();

        em.persist(studio);
        Product product = Product.builder().title("상품1").price(1000).build();
        em.persist(product);
        studio.addProudct(product);
        Exhibition exhibition = Exhibition.builder().title("특별전시회").build();
        em.persist(exhibition);
        Sale sale = Sale.builder().saleName("특가").salePrice(5000).build();
        em.persist(sale);
        exhibition.addSale(sale);
        //적어도 여기에서는 필요없음
        FinalPaymentPrice finalPaymentPrice = FinalPaymentPrice.builder().product(product).sale(sale).build();
        em.persist(finalPaymentPrice);
        //when 유저 결제
        Payment payment = Payment.builder().name("결제").finalPaymentPrice(finalPaymentPrice).build();
        Reservation reservation = Reservation.builder().scheduleName("예약5명요").build();
        reservation.setPayment(payment);
        em.persist(payment);
        em.persist(reservation);

        //예약내역
        user.addReservation(reservation);
        studio.addReservation(reservation);

        em.flush();
        em.clear();
        /*
        예약된 상품 등록 아 상품이 아니라 예약이 등록되는 거구나
         */
        Studio expectedStudio = studioRepository.findById(studio.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        User expectedUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디X"));
        //then
        assertThat(expectedStudio.getReservations().get(0)).isEqualTo(expectedUser.getReservations().get(0));
        assertThat(expectedStudio.getReservations().get(0).getPayment().getRealPrice()).isEqualTo(expectedUser.getReservations().get(0).getPayment().getRealPrice());
        assertThat(expectedStudio.getReservations().get(0).getPayment().getRealPrice()).isEqualTo(1000);
    }
}