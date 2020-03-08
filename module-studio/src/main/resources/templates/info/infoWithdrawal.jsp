<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- left_area -->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
<%-- 	<script src="${contextPath}/resources/js/jquery.js"></script> --%>
<%-- 	<script src="${contextPath}/resources/js/common-util.js"></script> --%>
<%-- 	<script src="${contextPath}/resources/js/jquery.form.js"></script> --%>

		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">회원정보 수정</h2>
			</div>
			<div class="inner">
			<div class="box_area full">
					<!-- s:contetns -->
			    <div class="section"> 
			            <div class="wrap view">
			            <div class="viewHeader">
			                <div class="infoBox" style="margin-left:0px;width:100%;background-color:white;">
						            <h1 class="lead">회원 탈퇴</h1>
						            <div class="content">
						                <p>회원 탈퇴 일로부터 계정과 닉네임을 포함한 계정 정보(아이디/이메일/닉네임)는 <strong>'개인 정보 보호 정책'에
						                따라 60일간 보관(잠김) 되며,</strong> 60일이 경과된 후에는 모든 개인 정보는 완전히 삭제되며 더 이상 복구할 수 없게 됩니다.</p>
						                <p><strong>작성된 게시물은 삭제되지 않으며, 익명처리 후 (주)포더모먼트로 소유권이 귀속됩니다.</strong></p>
						                <p><strong>게시물 삭제가 필요한 경우에는 관리자(<a href="mailto:zzikza@4themoment.co.kr">zzikza@4themoment.co.kr</a>)로 문의해 주시기 바랍니다.</strong></p>
						            <div class="text-center">
						            <input type="button" value="예, 탈퇴하겠습니다." class="btn btn-danger">
						            </div>
						            </div>
			                </div>
			            </div>  
			        </div>
					<div style="height:50px;">
					&nbsp;
					</div>
			    </div>
			    <!-- e:contetns -->
			</div>
		</div>		
		</div>
		<!--// left_area -->
		
<script	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.ui.widget.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.fileupload.js"></script>

<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script> -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>

<style>
.text-center {
    text-align: center;
}

.lead {
    line-height: 36px;
    font-size: 28px;
    font-weight: 500;
    margin-bottom: 8px;
}

.btn-danger {
    color: #fff;
    background-color: #d9534f;
    border-color: #d43f3a;
}

.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
/*     font-weight: normal; */
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
}

.btn-default {
    color: #333;
    background-color: #fff;
    border-color: #ccc;
}

p {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
}

.content p {
    line-height: 28px;
    letter-spacing: -.4px;
    color: #44484b;
    font-size: 17px;
}
</style>
<script src="${contextPath}/resources/js/info/infoWithdrawal.js"></script>
