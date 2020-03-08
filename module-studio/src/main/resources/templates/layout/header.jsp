<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- head -->
<c:set var="absoluteUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}"/> 
<div class="head">
	<div class="logo_area">
		<h1 class="logo"><a href="${contextPath}/board/notice"><img src="${contextPath}/resources/images/common/logo.png" alt="찍자"></a></h1>
		<span class="login_info">
			<a href="${contextPath}/info/view">${sessionVo.stdoNm }</a>
		</span>
	</div>
	<ul class="gnb_area">
		<li><a href="/logout?returl=${absoluteUrl}" class="logout" target="_parent"><i></i>로그아웃</a></li>
		<li><a href="#"><i class="all_menu">전체메뉴</i></a></li>
	</ul>
</div>
