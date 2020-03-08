<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- left_area -->
<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">게시판</h2>
			</div>
			<div class="inner">
				<ul class="tabs">
					<c:forEach items="${commonCodes}" var="code" >
						<li><a href="${contextPath}/board/${code.commCd}" <c:if test="${commonCode.commCd eq (code.commCd)}"> class='active'</c:if>>${code.commCdNm}</a></li>
					</c:forEach>
				</ul>
				<div class="box_area full">
					<h3 class="sub_stit">${commonCode.commCdNm}</h3>
					<c:if test="${commonCode.commCd eq 'qna'}">
					<div class="btn_area top_right">
						<button type="button" class="btn_form" id="btnQna">질문하기</button>
					</div>
					</c:if>
					<table class="tCont list">
						<caption>공지사항 목록입니다</caption>
						<colgroup>
							<col class="td04">
							<col style="">
							<col class="td02">
							<col class="td03">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
							</tr>
						</thead>
						<tbody>
<!-- 							list -->
							<c:forEach items="${list}" var="board" varStatus="idx">
								<tr seq='${board.brdId}' cate='${board.brdCateCd}' >
									<td>${board.brdId}</td>
									<td class="textL">
										<a href="javascript:void(0);"><c:out value="${board.title }"></c:out></a>
									</td>
									<td><c:out value="${board.regId }"></c:out></td>
									<td>
									<fmt:parseDate value="${board.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- 페이징 -->
					<jsp:include page="/WEB-INF/views/tilesView/paging.jsp">
						<jsp:param value="${totCount}" name="totCount"/>
						<jsp:param value="${nowPage}" name="nowPage"/>
						<jsp:param value="10" name="pageSize"/>
						<jsp:param value="${pagePerOnce}" name="pagePerOnce"/>
						<jsp:param value="fn_paging" name="pagingFnc"/>
					</jsp:include>
					<!-- 페이징 -->
				</div>
			</div>
		</div>		
		<!--// left_area -->
<script src="${contextPath}/resources/js/board/boardList.js"></script>
<script>
var brdCateCd = "${brdCateCd}";
var contextPath = "${contextPath}";
function fn_paging(index){
	location.href=contextPath + '/board/'+brdCateCd+'?nowPage='+index;
}
</script>
