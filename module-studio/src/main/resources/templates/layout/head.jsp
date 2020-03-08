<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5shiv.js"></script>
	<script type="text/javascript" src="js/html5shiv.printshiv.js"></script>
	<![endif]-->
<!-- 	<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script> -->
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-152403132-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-152403132-1');
</script>


	<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
	<link href="//cdn.quilljs.com/1.3.4/quill.core.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/include.css" /> 
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/font/font.css" /> 
	<script src="${contextPath}/resources/js/jquery.js"></script>
    <script src="${contextPath}/resources/js/jquery-ui.js"></script>
    <script src="${contextPath}/resources/js/bootstrap-select.min.js"></script> <!-- selectbox -->
	<script src="${contextPath}/resources/js/bootstrap.bundle.min.js"></script> <!-- selectbox -->
    <script src="${contextPath}/resources/js/style.js"></script>
	<script src="${contextPath}/resources/js/common-util.js"></script>
	<script src="${contextPath}/resources/js/jquery.form.js"></script>
	<script src="${contextPath}/resources/js/jquery.validate.min.js"></script>
	 <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
	 <script src="${contextPath}/resources/js/quill.custom.js"></script>
	 
    <link rel="apple-touch-icon" href="${contextPath}/favicon.ico">
    <link rel="shortcut icon" href="${contextPath}/favicon.ico">
    <title>${title }</title>
	<meta name="description" content="${title}">
	<meta property="og:type" content="website"> 
	<meta property="og:title" content="${title}">
	<link rel="canonical" href="https://studio.zzikza.com">
	<c:if test="${empty product}">
	<meta property="og:description" content="${title}">  
	<meta property="og:url" content="https://studio.zzikza.com">
	<meta property="og:image" content="https://studio.zzikza.com/favicon.ico">
	</c:if>
</head>
     

    
