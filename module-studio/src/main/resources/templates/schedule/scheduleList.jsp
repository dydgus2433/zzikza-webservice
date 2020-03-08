<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- left_area -->
<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">예약관리 [일별]</h2>
			</div>
			<div class="inner">
				<div class="box_area full">
					<div class="book_day_head">
						<div class="left">
							<input type="hidden" id="dateVal" value="${date.year}-${date.month}-${date.day}">
							<button type="button" class="btn_prev_month" onclick="javascript:prevDay('${date.year }','${date.month-1}','${date.day}')">이전달</button>
							<h1>${date.year }년 ${date.month}월 ${date.day}일</h1>
							<button type="button" class="btn_next_month" onclick="javascript:nextDay('${date.year }','${date.month-1}','${date.day}')">다음달</button>
						</div>
						<div class="right">
							<button type="button" class="btn_book" onclick="javascript:monthSchedule('${date.year }','${date.month}')">월별보기</button>
							<button type="button" class="btn_book_new">일정등록</button>
						</div>
					</div>
					<div class="book_day_list">
						<c:forEach var="list" items="${reservationList }">
							<ul class="reservation" seq="${list.rsrvId }">
								<li class="tit"><c:out value="${list.rsrvStrtHour }:${list.rsrvStrtMin } ~ ${list.rsrvEndHour }:${list.rsrvEndMin }"></c:out><em><c:out value="${list.userNm } 고객님 ${list.scheduleNm }"></c:out></em></li>
								<li>
									<span class="stit">연락처</span>
									<p class="text">${list.tel }</p>
								</li>
								<li>
									<span class="stit">촬영인원</span>
									<p class="text">${list.ppCnt }명</p>
								</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>			
<!--// left_area -->
<script src="${contextPath}/resources/js/schedule/scheduleList.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>