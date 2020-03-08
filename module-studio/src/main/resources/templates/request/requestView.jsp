<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>
<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">요청사항</h2>
			</div>
			<div class="inner">
<!-- 				<ul class="tabs"> -->
<%-- 					<c:forEach items="${commonCodes}" var="code" > --%>
<%-- 						<li><a href="<c:url value='/detail/${code.commCd}'/>" <c:if test="${commonCode.commCd eq (code.commCd)}"> class='active'</c:if>>${code.commCdNm}</a></li> --%>
<%-- 					</c:forEach> --%>
<!-- 				</ul> -->
				<div class="box_area full">
<!-- 				{reqId=REQ0000000027, userNm=Steven Spark, reqTitle=조금 어려운 신청 , reqContent=조금 어려운 신청  -->
<!-- 조금 어려운 신청 조금 어려운 신청  -->
<!-- 조금 어려운 신청  -->
<!-- 조금 어려운 신청  -->
<!-- 조금 어려운 신청 , reqStat=W, regDt=2019-11-16 01:26:17.0, regId=USER0000000002} -->
<!-- [reqId, userNm, reqTitle, reqContent, reqStat, regDt, regId] -->
<!-- {userNm=Steven Spark, regDt=2019-11-16 01:26:17.0, reqContent=조금 어려운 신청  -->
<!-- 조금 어려운 신청 조금 어려운 신청  -->
<!-- 조금 어려운 신청  -->
<!-- 조금 어려운 신청  -->
<!-- 조금 어려운 신청 , reqTitle=조금 어려운 신청 , regId=USER0000000002, reqStat=W, reqId=REQ0000000027} -->
				
					<h3 class="sub_stit">${detail.reqTitle}</h3>
					<table class="tView">
						<caption>${commonCode.commCdNm} 상세내용입니다</caption>
						<colgroup>
							<col style="width:100px">
							<col style="">
						</colgroup>
						<thead>
							<tr>
								<th colspan="2">
									<c:out value="${ detail.reqTitle}"></c:out>
									<p class="date">
									<fmt:parseDate value="${detail.regDt}" var="regDt" pattern="yyyy-MM-dd HH:mm:ss.S"/>
									<fmt:formatDate value="${regDt}" pattern="yyyy-MM-dd HH:mm:ss"  timeZone="GMT"/>
									</p>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="2" style="white-space: pre-wrap;">${ detail.reqContent}</td>
							</tr>
						</tbody>
						<c:if test="${not empty files }">
								<tr>
									<td colspan="2" class="file">
										<c:forEach items="${files }" var="file">
<%-- 											<p class="file_name"><a href="<c:url value='/api/fileDownload?fileName=${file.flNm}&originName=${file.flSrcNm}'/>"><c:out value="${file.flSrcNm}"></c:out></a></p> --%>
											<p class="file_name">
											<img src="/img/${file.flNm}" onerror="this.src='/resources/images/common/no_img.gif'" style="width: 100%">
											</p>
										</c:forEach>
									</td>
								</tr>
							</c:if>
					</table>
					<div class="btn_area right">
						<button type="button" class="btn_form btn_req" data-seq="${ detail.reqId}">신청하기</button>
						<button type="button" class="btn_form" id="listBtn">목록</button>
					</div>
				</div>
			</div>
		</div>		
		<!--// left_area -->
		
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.ui.widget.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.fileupload.js"></script>
<script src="${contextPath}/resources/js/request/requestView.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>