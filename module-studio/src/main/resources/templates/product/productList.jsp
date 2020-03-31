<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- left_area -->
		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">상품목록</h2>
			</div>
			<div class="inner">
				<div class="box_area full">
					<h3 class="sub_stit">상품목록</h3>
					<table class="tCont">
						<caption>상품관리 목록입니다</caption>
						<colgroup>
							<col class="td01">
							<col style="">
							<col class="td02">
							<col class="td02">
							<col class="td03">
						</colgroup>
						<thead>
							<tr>
								<th>글번호</th>
								<th>상품명</th>
								<th>가격</th>
								<th>등록날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="item">
								<tr class="prdRow" data-seq='${item.prdId }'>
									<td>${item.prdId }</td>
									<td class="textL">${item.title }</td>
									<td><fmt:formatNumber type="number">${item.price}</fmt:formatNumber></td>
									<td>
									<fmt:parseDate value="${item.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT"/>
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
<script src="${contextPath}/resources/js/product/productList.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
	function fn_paging(index){
		location.href=contextPath + '/prod/list?nowPage='+index;
	}
</script>
