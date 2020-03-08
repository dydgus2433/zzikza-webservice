<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- left_area -->
	<script src="${contextPath}/resources/js/jquery.js"></script>
	<script src="${contextPath}/resources/js/common-util.js"></script>
<%-- 	<script src="${contextPath}/resources/js/jquery.form.js"></script> --%>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">회원정보 수정</h2>
			</div>
			<div class="inner">
			<div class="box_area full">
					<h3 class="sub_stit">비밀번호 수정</h3>
				<form id="infoFrm" action="${contextPath}/api/updateInfoPassword" method="post">
					<div class="box_area full">
						<h3 class="sub_stit">비밀번호</h3>
						<input type="password"  name="pw" id="pw" class="form_goods" placeholder="비밀번호를 입력해 주세요." value="">
					</div>
					<div class="box_area full">
						<h3 class="sub_stit">변경비밀번호</h3>
						<input type="password" name="changePw" id="changePw" class="form_goods" placeholder="비밀번호를 입력해 주세요." value="">
					</div>
					<div class="box_area full">
						<h3 class="sub_stit">변경 비밀번호 확인</h3>
						<input type="password"  name="pwChk" id="pwChk" class="form_goods" placeholder="비밀번호를 입력해 주세요." value="">
						<div class="btn_area">
							 <button type="submit" id="joinBtn" class="btn_form">회원정보수정</button>
						</div>
					</div>
				</form>
			</div>
		</div>		
		</div>
		<!--// left_area -->
		
<script	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.ui.widget.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.fileupload.js"></script>

<script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
<script src="${contextPath}/resources/js/info/infoPassword.js"></script>
	<script src="${contextPath}/resources/js/jquery.validate.min.js"></script> 
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>
