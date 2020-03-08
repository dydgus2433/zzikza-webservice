<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- left_area -->
		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">요청관리</h2>
			</div>
			<div class="inner">
				<div class="box_area full">
					<h3 class="sub_stit">요청관리목록</h3>
					<table class="tCont">
						<caption>요청관리 목록입니다</caption>
						<colgroup>
							<col class="td01">
							<col style="">
							<col class="td02">
							<col class="td03">
						</colgroup>
						<thead>
							<tr>
								<th>고객명</th>
								<th>요청제목</th>
								<th>요청날짜</th>
								<th>요청상태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="item">
								<tr data-seq='${item.reqId}'>
									<td><c:out value="${item.userNm }"></c:out></td>
									<td><c:out value="${item.reqTitle }"></c:out></td>
									<td>
									<fmt:parseDate value="${item.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT"/>
									</td>
									<td>
										<c:if test="${item.reqStatCd eq 'W'}">
											<span class="fBlue">요청대기</span>
											<button type="button" class="btn_insert" data-seq='${item.reqId}'>신청하기</button>
										</c:if>
										<c:if test="${item.reqStatCd ne 'W'}">
											<span class="fRed">마감</span>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!--- pager --->
					<!-- 페이징 -->
					<jsp:include page="/WEB-INF/views/tilesView/paging.jsp">
						<jsp:param value="${totCount}" name="totCount"/>
						<jsp:param value="${nowPage}" name="nowPage"/>
						<jsp:param value="10" name="pageSize"/>
						<jsp:param value="${pagePerOnce}" name="pagePerOnce"/>
						<jsp:param value="fn_paging" name="pagingFnc"/>
					</jsp:include>
					
				</div>
			</div>
		</div>		
		<!--// left_area -->
<script src="${contextPath}/resources/js/moment.min.js"></script>
<script src="${contextPath}/resources/js/fullcalendar.js"></script>
<script src="${contextPath}/resources/js/request/requestList.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
	function fn_paging(index){
		location.href=contextPath + '/request/list?nowPage='+index;
	}
</script>
