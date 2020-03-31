<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
    
$( document ).ready(function() {
    
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
    
    //tab 
    $('.viewTab li:nth-child(1)').click(function(){
        $('.viewTab li:nth-child(1) a').addClass('on');
        $('.viewTab li:nth-child(2) a').removeClass('on');
        $('.studioSilde').show();
        $('.studioInfo').show();
        $('.reviewBox').hide();
    });
    $('.viewTab li:nth-child(2)').click(function(){
        $('.viewTab li:nth-child(1) a').removeClass('on');
        $('.viewTab li:nth-child(2) a').addClass('on');
        $('.studioSilde').hide();
        $('.studioInfo').hide();
        $('.reviewBox').show();
    });
    
});
   
</script>  
<style>

.page-load-status {
  display: none; /* hidden by default */
  padding-top: 20px;
  border-top: 1px solid #DDD;
  text-align: center;
  color: #777;
}
#payFrm{

	line-height: 28px;
	box-sizing: border-box;
	width: 50%; 
	float : right;
}

select{
	border: 1px solid #d9dbe5;
	background: #fbfbfe;
    line-height: 28px;
    box-sizing: border-box;
    width: 100%;
}
</style>
<fmt:parseNumber var="reservationFee" value="${product.price * (studio.fee / 100)}" integerOnly="true"/>
<fmt:formatNumber var="reservationFee" value="${reservationFee}" type="number"/>
<!-- s:contetns -->
    <div class="section"> 
        <div class="top">
            <div class="wrap">
                <div class="dir">
                    <a href="#">홈</a>
                    <a href="#">${code.commCdNm}</a>
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
<%--                             	<span class="pre">${product.price}</span><span class="now"><strong>${product.prdSalePrc}</strong>원</span> --%>

								<c:if test="${not empty  product.prdSalePrc}"><span class="pre">${product.price}</span><span class="now"><strong  class="prdPrice">${product.prdSalePrc}</strong>원</span></c:if>
								<c:if test="${empty  product.prdSalePrc}"><span class="now"><strong class="prdPrice"><fmt:formatNumber type="number">${product.price}</fmt:formatNumber></strong>원</span></c:if>
                            	
                            </td>
                        </tr>
                        <tr>
                            <th>예약금</th>
                            <td><span class="now"><strong  class="reservPrice">${reservationFee }</strong>원</span></td>
                        </tr>
                    </table>
                    
                    <div class="time">촬영시간 : <strong><c:if test="${not empty  product.productHour}"><c:out value="${product.productHour }시간 "></c:out></c:if><c:if test="${not empty  product.productMinute}">${product.productMinute }분</c:if></strong>
                    	<form id="payFrm" name="payFrm" class="payFrm" action="/goods/order/${prdId}" method="post">
                    		<c:if test="${not empty product.exhId}">
                    			<select class="saleCd" name="saleCd" id="saleCd">
		                    	 <option value="">할인대상선택</option>
		                    	 <c:forEach items="${saleCodes}" var="item">
		                   			 <option value="${item.saleCd}" data-price="${item.saleVal }" data-calc-cd="${item.calcCd}">${item.saleNm}</option>
		                    	 </c:forEach>
		                   		</select> 
                    		</c:if>
		                    <input type="hidden" name="exhId" id="exhId" value="${product.exhId }"/>
		                    <input type="hidden" name="reservPrice" id="reservPrice"/>
		                    <input type="hidden" name="price" id="price"/>
                    	</form>
                    </div>
                    
                    
                   <div class="price">
                    	<c:choose>
                    		<c:when test="${not empty wish}">
                    		<a class="wish" data-prd-id="${product.prdId }">♥ 찜하기</a>
                    		</c:when>
                    		<c:otherwise>
                    		<a class="wish" data-prd-id="${product.prdId }">♡ 찜하기</a>
                    		</c:otherwise>
                    	</c:choose>
                    	
                  		 결제금액 <span class="now"><strong  class="reservPrice">${reservationFee }</strong>원</span></div>
                    <div class="btns">
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_share.png" alt="">공유하기</a>
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_question.png" alt="">문의하기</a>
                        <a href="#" class="btn_type_y"><img src="${contextPath}/resources/preview/img/ico_reservation.png" alt="">예약하기</a>
                    </div>
                </div>
                
            </div>
              <div class="contentsView" style="white-space: pre-wrap;text-align: initial;"><c:out value="${product.productBriefDesc }"></c:out></div>
            <div class="contentsView ql-editor" style="white-space: pre-wrap;text-align: initial;"><c:out value="${product.productDescription }" escapeXml="false" /></div>
            <ul class="viewTab">
                <li><a class="on">정보</a></li>
                <li><a>후기</a></li>
            </ul>
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
                 <!-- s: 20191021 상세추가 -->
            <div class="reviewBox">
            	<ul class="reviews_list">
                </ul>
            	 <div class="reviewWriteBox">
                	<form id="reviewFrm">
	                   	<div class="profile">    
	                   		<input type="hidden" id="grade" name="grade">
							<div id="rateitRepl">
							</div>
<!-- 	                        <script type="text/javascript"> -->
<!-- // 							    $(function () { $('#rateitRepl').rateit({ max: 5, step: 0.5, backingfld: '#grade' }); }); -->
<!-- 							</script> -->
	                   	
	                        
	                    </div>  
<!-- 	                    <div class="upload"> -->
<!-- 	                        <label><input multiple="multiple"  type="file" name="" />※ 이미지는 5개까지 등록 가능합니다.</label> -->
<!-- 	                        <ul class="uploadPreview"> -->
<!-- 	                            <li><a></a><img src="img/sample.jpg" alt=""></li> -->
<!-- 	                            <li><a></a><img src="img/sample.jpg" alt=""></li> -->
<!-- 	                            <li><a></a><img src="img/sample.jpg" alt=""></li> -->
<!-- 	                            <li><a></a><img src="img/sample.jpg" alt=""></li> -->
<!-- 	                            <li><a></a><img src="img/sample.jpg" alt=""></li> -->
<!-- 	                        </ul> -->
<!-- 	                    </div> -->
<!-- 	                    <input type="hidden" value="3" name="grade"> -->
	                     <input type="hidden" value="${prdId}" name="prdId">
	                    <textarea name="content" id="reviewContent" placeholder="리뷰작성하기"></textarea>    
                    <input type="button" value="작성" class="btn_type_y" id="reviewSubmit">
                    </form>              
                </div>
            </div>
            <div class="bottomBtns">
                <!-- s:20190923 div.web_minSize 추가 -->
            	<div class="web_minSize">
                <div class="info">
                       <strong>${product.title}</strong>결제금액 <span  class="reservPrice">${reservationFee}</span>원
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