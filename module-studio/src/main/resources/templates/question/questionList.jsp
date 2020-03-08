<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- left_area -->
		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">고객문의관리</h2>
			</div>
			<div class="inner">
				<div class="box_area full">
					<h3 class="sub_stit">고객문의관리</h3>
					<table class="tCont">
						<caption>고객문의관리 목록입니다</caption>
						<colgroup>
							<col class="td01">
							<col style="">
							<col class="td02">
							<col class="td03">
						</colgroup>
						<thead>
							<tr>
								<th>고객명</th>
								<th>문의내용</th>
								<th>문의날짜</th>
								<th>문의상태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="item">
								<tr>
									<td><c:out value="${item.userNm }"></c:out></td>
									<td class="textL" style=""><c:out value="${item.qstnContent }"></c:out></td>
									<td>
									<fmt:parseDate value="${item.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT"/>
									</td>
									<td>
										<c:if test="${item.qstnStatCd eq 'Q'}">
											<span class="fBlue">답변대기</span>
											<button type="button" class="btn_insert" data-seq='${item.stdoQstnId}'>답변하기</button>
										</c:if>
										<c:if test="${item.qstnStatCd ne 'Q'}">
											<span class="fRed">답변완료</span>
											<button type="button" data-seq='${item.stdoQstnId}' class="btn_insert btn_qna_view">답변보기</button>
										</c:if>
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
					
				</div>
			</div>
		</div>		
		<!--// left_area -->
<script src="${contextPath}/resources/js/moment.min.js"></script>
<script src="${contextPath}/resources/js/fullcalendar.js"></script>
<script src="${contextPath}/resources/js/question/questionList.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
	function fn_paging(index){
		location.href=contextPath + '/question/list?nowPage='+index;
	}
</script>
