<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <!-- snb_area -->
 <c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
 <c:set var="absoluteUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}"/> 
<div id="snb">			
	<button type="button" class="all_close">닫기</button>
	<div class="snb_menu_area">
		<ul class="snb_menu">
			<li class="snb_logout"><a href="/logout?returl=${absoluteUrl}"  target="_parent">로그아웃</a></li>
			<c:forEach var="item" items="${menuList}" varStatus="status">
			<li><a href="#">${item.menuNm}</a>
				<ul class="snb_menu_s"  <c:if test="${!fn:startsWith(path, item.menuUrl)}">style='display:none'</c:if>><c:forEach var="item2" items="${menuList2}"><c:if test="${item.menuCd eq item2.parentMenuCd}"><li class="fa  ${item2.menuIcon}"><a class="<c:if test="${fn:startsWith(path, item2.menuUrl)}">active</c:if>" href="${contextPath}${item2.menuUrl}">${item2.menuNm}</a></li></c:if></c:forEach></ul>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>
<!--// snb_area -->