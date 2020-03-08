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
						<li><a href="<c:url value='/board/${code.commCd}'/>" <c:if test="${commonCode.commCd eq (code.commCd)}"> class='active'</c:if>>${code.commCdNm}</a></li>
					</c:forEach>
				</ul>
				<div class="box_area full">
					<h3 class="sub_stit">${commonCode.commCdNm}</h3>
					<table class="tView">
						<caption>${commonCode.commCdNm} 상세내용입니다</caption>
						<colgroup>
							<col style="width:100px">
							<col style="">
						</colgroup>
						<thead>
							<tr>
								<th colspan="2">
									<c:out value="${ board.title}"></c:out>
									<p class="date">
									<fmt:parseDate value="${board.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss"  timeZone="GMT"/>
									</p>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty files }">
								<tr>
									<td colspan="2" class="file">
										<c:forEach items="${files }" var="file">
											<p class="file_name"><a href="<c:url value='/api/fileDownload?fileName=${file.flNm}&originName=${file.flSrcNm}'/>"><c:out value="${file.flSrcNm}"></c:out></a></p>
										</c:forEach>
									</td>
								</tr>
							</c:if>
							
							<tr>
								<td colspan="2" style="white-space: pre-wrap;">${ board.content}</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th>이전글<i></i></th>
							<c:choose>
								<c:when test="${not empty prev}">
									<td><a href="<c:url value='/board/${prev.brdCateCd}/view/${prev.brdId}'/>"><c:out value="${prev.title }"></c:out></a></td>
								</c:when>
								<c:otherwise>
									<td><a href="javascript:void(0);">이전글이 없습니다</a></td>
								</c:otherwise>
							</c:choose>
							</tr>
							<tr>
								<th>다음글<i></i></th>
							<c:choose>
								<c:when test="${not empty next}">
									<td><a href="<c:url value='/board/${next.brdCateCd}/view/${next.brdId}'/>"><c:out value="${next.title }"></c:out></a></td>
								</c:when>
								<c:otherwise>
									<td><a href="javascript:void(0);">다음글이 없습니다</a></td>
								</c:otherwise>
							</c:choose>
							</tr>
						</tfoot>
					</table>
					<div class="btn_area right">
						<button type="button" class="btn_form" id="listBtn">목록</button>
					</div>
				</div>
			</div>
		</div>		
		<!--// left_area -->
<script src="${contextPath}/resources/js/board/boardView.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>