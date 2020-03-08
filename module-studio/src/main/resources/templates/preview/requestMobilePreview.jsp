<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<fmt:parseNumber var="reservationFee" value="${product.prdPrc * (studio.fee / 100)}" integerOnly="true"/>
<fmt:formatNumber var="reservationFee" value="${reservationFee}" type="number"/>
<!-- s:contetns -->
    <div class="section"> 
        <div class="top">
            <div class="wrap">
                <div class="dir">
                    <a href="#">홈</a>
                    <a href="#">요청하기</a>
                </div>
                <a class="mn"></a>
            </div>    
        </div>
        <div class="wrap view">
            <div class="viewHeader">
                <div class="goodsSilde">
                    <div class="swiper-wrapper">
                    	<c:forEach items="${files }" var="item">
                       	<div class="swiper-slide"><img src="${item}" alt=""></div>
                        </c:forEach>
                    </div>
                    <div class="swiper-pagination"></div>
                </div>
                <div class="infoBox">
                	<c:forEach var="category" items="${keywords }">
                		 <span class="category">${category.keywordNm}</span>
                	</c:forEach>
                    <p class="tit">${product.title}</p>
                    <table>
                        <tr>
                            <th>판매가</th>
                            <td>
<%--                             	<span class="pre">${product.prdPrc}</span><span class="now"><strong>${product.prdSalePrc}</strong>원</span> --%>

								<c:if test="${not empty  product.prdSalePrc}"><span class="pre">${product.prdPrc}</span><span class="now"><strong>${product.prdSalePrc}</strong>원</span></c:if>
								<c:if test="${empty  product.prdSalePrc}"><span class="now"><strong><fmt:formatNumber type="number">${product.prdPrc}</fmt:formatNumber></strong>원</span></c:if>
                            	
                            </td>
                        </tr>
                        <tr>
                            <th>예약금</th>
                            <td><span class="now"><strong>${reservationFee }</strong>원</span></td>
                        </tr>
                    </table>
                    <div class="time">촬영시간 : <strong><c:if test="${not empty  product.prdHour}"><c:out value="${product.prdHour }시간 "></c:out></c:if><c:if test="${not empty  product.prdMin}">${product.prdMin }분</c:if></strong></div>
                    <div class="price">결제금액 <span class="now"><strong>${reservationFee }</strong>원</span></div>
                    <div class="btns">
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_share.png" alt="">공유하기</a>
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_question.png" alt="">문의하기</a>
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_reservation.png" alt="">예약하기</a>
                    </div>
                </div>
                
            </div>
            <div class="contentsView"  style="white-space: pre-wrap;"><c:out value="${product.prdDsc }"></c:out></div> 
            
            <div class="studioBox">
                <div class="studioSilde">
                    <div class="swiper-wrapper">
                   		<c:forEach items="${list }" var="item">
                       	<div class="swiper-slide" style="background-image: url(${contextPath}/img/${item.flNm});"></div>
                        </c:forEach>
                    </div>
                    <div class="swiper-pagination"></div>
                </div> 
            </div>
            
            <div class="studioInfo">
                <div class="contWrap">
                    <p class="tit">${studio.stdoNm }</p>
                    <div class="cont" style="white-space: pre-wrap;"><c:out value="${studio.stdoDsc }"></c:out></div>
                    <!--                     <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d25321.327943563098!2d127.02990940300094!3d37.50400295780005!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca405861e4a67%3A0x73ec1c047764908!2z7ISc7Jq47Yq567OE7IucIOqwleuCqOq1rCDthYztl6TrnoDroZw!5e0!3m2!1sko!2skr!4v1564976538213!5m2!1sko!2skr" frameborder="0" style="border:0" allowfullscreen></iframe> -->
<%--                     <iframe src="https://www.google.com/maps/embed/v1/place?q=${studio.addr }&key=AIzaSyC2tsFNel3LV_XJjVC1QyGkhVsKvM9KoPo" frameborder="0" style="border:0" allowfullscreen></iframe> --%>
                    <iframe src="https://www.google.com/maps/embed/v1/place?q=${studio.lgtd },${studio.lttd }&key=AIzaSyC2tsFNel3LV_XJjVC1QyGkhVsKvM9KoPo" frameborder="0" style="border:0" allowfullscreen></iframe>
                    <div class="address">
                        <p><span>주소</span>${studio.addr }</p>
                        <p><span>연락처</span>${studio.tel}</p>
                    </div>
                </div>
            </div>
            
            <div class="bottomBtns">
                <div class="info">
                    <strong>${product.title}</strong>결제금액 ${reservationFee }원
                </div>
                <div class="btns">
                    <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_share.png" alt="">공유하기</a>
                    <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_question.png" alt="">문의하기</a>
                    <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_reservation.png" alt="">예약하기</a>
                </div>
            </div>    
        </div>
    </div>
    <!-- e:contetns -->   