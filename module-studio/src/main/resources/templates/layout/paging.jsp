<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="totCount" value="${param.totCount}" /><!-- 조회 개수 -->
<c:set var="totPage" value="${totPage}" /><!-- 조회 개수 -->
<c:set var="nowPage" value="${param.nowPage}" /><!-- 현재 페이지 -->
<c:set var="pageSize" value="${param.pageSize}" /><!-- 페이징 넘버 개수 10-->
<c:set var="pagePerOnce" value="${param.pagePerOnce}" /><!-- 한화면에 출력할 목록 개수 10-->

<fmt:parseNumber var="lastPageNo" value="${((totCount - 1) / pagePerOnce) + 1 }" integerOnly="true"/>
<fmt:parseNumber var="pageOffset" value="${( (nowPage-1) / pageSize)}" integerOnly="true"/>
<fmt:parseNumber var="startNo" value="${pageOffset*pageSize +1}" integerOnly="true"/>
<fmt:parseNumber var="endNo" value="${pageOffset*pageSize + pageSize }" integerOnly="true"/>

<c:if test="${endNo > lastPageNo }">
	<fmt:parseNumber var="endNo" value="${lastPageNo }" integerOnly="true"/>
</c:if>

<c:set var="prevPage" value="" />
<c:set var="nextPage" value="" />

<c:if test="${nowPage > 1 }">
	<fmt:parseNumber var="prevPage" value="${nowPage-1 }" integerOnly="true"/>
</c:if>

<c:if test="${nowPage < lastPageNo }">
	<fmt:parseNumber var="nextPage" value="${nowPage +1 }" integerOnly="true"/>
</c:if>
<script type="text/javascript">
	function fn_movePage(pageNo) {
		eval("${param.pagingFnc}" + "(" + pageNo +");");
	}
</script>

					
<c:if test="${totCount > 0 }">
					<div class="tPages">
						<ul class="pages">
							<c:choose>
								<c:when test="${prevPage eq '' }">
									<li class="prev">
										<a href="javascript:void(0);"><img src="${contextPath}/resources/images/common/prev02.png" alt="이전페이지로 이동"></a>
									</li>
									<li class="prev">
										<a href="javascript:void(0);"><img src="${contextPath}/resources/images/common/prev.png" alt="이전페이지로 이동"></a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="prev">
										<a href="javascript:fn_movePage('1');"><img src="${contextPath}/resources/images/common/prev02.png" alt="이전페이지로 이동"></a>
									</li>
									<li class="prev">
										<a href="javascript:fn_movePage('${prevPage }');"><img src="${contextPath}/resources/images/common/prev.png" alt="이전페이지로 이동"></a>
									</li>
								</c:otherwise>
							</c:choose>
							<c:forEach var="idx" begin="${startNo}" end="${endNo}" step="1">
								<c:if test="${nowPage eq idx }">
									<li class="active">
										<a href="javascript:void('0');"><span>${idx }</span></a>
									</li>
								</c:if>	
								<c:if test="${nowPage ne idx }">
									<li>
										<a href="javascript:fn_movePage('${idx }');">${idx }</a>
									</li>
								</c:if>	
							</c:forEach>
							<c:choose>
								<c:when test="${nextPage eq '' }">
												<li class="next">
													<a href="javascript:void(0);"><img src="${contextPath}/resources/images/common/next.png" alt="다음페이지로 이동"></a>
												</li>
												<li class="next">
													<a href="javascript:void(0);"><img src="${contextPath}/resources/images/common/next02.png" alt="다음페이지로 이동"></a>
												</li>
								</c:when>
								<c:otherwise>
												<li class="next">
													<a href="javascript:fn_movePage('${nextPage }');"><img src="${contextPath}/resources/images/common/next.png" alt="다음페이지로 이동"></a>
												</li>
												<li class="next">
													<a href="javascript:fn_movePage('${lastPageNo }');"><img src="${contextPath}/resources/images/common/next02.png" alt="다음페이지로 이동"></a>
												</li>
								</c:otherwise>
							</c:choose>
												
						</ul>
					</div>
</c:if>