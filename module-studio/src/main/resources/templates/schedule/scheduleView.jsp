<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- left_area -->
<div class="left_content_area">
	<div class="left_content_head">
		<h2 class="sub_tit">예약관리</h2>
	</div>
	<div class="inner">
		<div class="box_area full">
			<div id="calendar"></div>
		</div>
	</div>
</div>		
<!--// left_area -->	
<script src="${contextPath}/resources/js/moment.min.js"></script>
<script src="${contextPath}/resources/js/fullcalendar.js"></script>
<script src="${contextPath}/resources/js/schedule/scheduleView.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
	var customYear = '${date.year}';
	var customMonth = '${date.month}';
</script>