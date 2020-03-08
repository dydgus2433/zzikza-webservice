<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>찍자 - 에러</title>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/n_admin.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/admin_default.css" />" />
	<style type="text/css">
	</style>
</head>
<body>
	<div id="ad_wrap">
		${msg}
		<a href="${contextPath }/">홈으로 이동</a>
	</div>
	<jsp:include page="/WEB-INF/views/tilesView/footer.jsp" flush="false"></jsp:include>
</body>
</html>
