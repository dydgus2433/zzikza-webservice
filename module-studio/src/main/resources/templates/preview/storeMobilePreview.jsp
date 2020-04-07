<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$( document ).ready(function() {
    function checkMenu(){
        var windowWidth = $( window ).width();
        if(windowWidth < 481) {
            $('.header .wrap .menu').hide();
            $('.footer').css('margin-bottom','80px');
        } else {
            $('.header .wrap .menu').show();
            $('.footer').css('margin-bottom','80px');
        }
    }
    
    checkMenu();
    
    $(window).resize(function() { 
        checkMenu();   
    });    
    
    var swiper = new Swiper('.goodsSilde', {
        loop : true, 
        slidesPerView: 1,
        autoplay : {
            delay : 4000, // 딜레이 0
        },
        speed : 300, // 슬라이드 속도 2초
                pagination : {
            el : '.swiper-pagination',
            clickable : true, 
        },
    });
    
    var swiperStudio = new Swiper('.studioSilde', {
        loop : true, 
        slidesPerView: 1,
        autoplay : {
            delay : 4000, // 딜레이 0
        },
        speed : 300, // 슬라이드 속도 2초
                pagination : {
            el : '.swiper-pagination',
            clickable : true, 
        },
    });
});
   
</script>  
<!-- {openTime=1, weekendOpenTime=1, dateType=W, dateVal=, keywords=1, weekendCloseTime=14, files=http://localhost/img/thumb/c2fe31e4-2dc4-47ab-8652-ccf1b7910d1e.jpg, studioDescription=테스트2, weekVal=0, closeTime=14} -->
<!-- s:contetns -->
    <div class="section"> 
        <div class="top">
            <div class="wrap">
                <div class="dir">
                    <a href="#">홈</a>
                    <a href="#">상세보기</a>
                </div>
                <a class="mn"></a>
            </div>    
        </div>
        <div class="wrap view">
            <div class="viewHeader">
                
            </div>
            <div class="studioBox">
                <div class="studioSilde">
                    <div class="swiper-wrapper">
                   		<c:forEach items="${files }" var="item">
                       	<div class="swiper-slide" style="background-image: url(${item});"></div>
                        </c:forEach>
                    </div>
                    <div class="swiper-pagination"></div>
                </div> 
            </div>
            
            <div class="studioInfo">
                <div class="contWrap">
                    <p class="tit">${studio.studioName }</p>
                    <div class="cont" style="white-space: pre-wrap;"><c:out value="${studio.studioDescription }"></c:out></div>
                    <!--                     <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d25321.327943563098!2d127.02990940300094!3d37.50400295780005!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca405861e4a67%3A0x73ec1c047764908!2z7ISc7Jq47Yq567OE7IucIOqwleuCqOq1rCDthYztl6TrnoDroZw!5e0!3m2!1sko!2skr!4v1564976538213!5m2!1sko!2skr" frameborder="0" style="border:0" allowfullscreen></iframe> -->
<%--                     <iframe src="https://www.google.com/maps/embed/v1/place?q=${studio.addr }&key=AIzaSyC2tsFNel3LV_XJjVC1QyGkhVsKvM9KoPo" frameborder="0" style="border:0" allowfullscreen></iframe> --%>
                    <iframe src="https://www.google.com/maps/embed/v1/place?q=${studio.lttd },${studio.lgtd }&key=AIzaSyC2tsFNel3LV_XJjVC1QyGkhVsKvM9KoPo" frameborder="0" style="border:0" allowfullscreen></iframe>
                    <div class="address">
                        <p><span>주소</span>${studio.addr }</p>
                        <p><span>연락처</span>${studio.tel}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- e:contetns -->  
    <div class="bottomBtns">
          <div class="info">
          </div>
          <div class="btns">
              <a href="#" class="btn_type_y"><img src="$/preview/img/ico_share.png" alt="">공유하기</a>
              <a href="#" class="btn_type_y"><img src="$/preview/img/ico_question.png" alt="">문의하기</a>
              <a href="#" class="btn_type_y"><img src="$/preview/img/ico_reservation.png" alt="">예약하기</a>
          </div>
      </div>     