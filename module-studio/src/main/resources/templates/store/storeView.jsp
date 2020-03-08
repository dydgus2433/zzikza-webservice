<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="left_content_area">
	<div class="left_content_head">
		<h2 class="sub_tit">매장관리</h2>
	</div>
	<div class="inner">
		<div class="box_area full">
			<h3 class="sub_stit">
				스튜디오 소개 사진 <span>스튜디오를 대표하는 사진으로 올려주세요 (최대 9장)</span>
			</h3>
			<div class="shop_fileup_area">
				<span class="tit">파일 사이즈</span>
				<div class="filebox">
					<input class="upload-name"	value="<c:if test="${fn:length(files) > 0}"><c:out value="${fn:length(files)}"/>개의 파일</c:if><c:if test="${fn:length(files) <= 0}">파일선택</c:if>"
						disabled="disabled"><label for="detailFiles">찾아보기</label>
					<input type="file" id="detailFiles" class="upload-hidden"
						multiple="multiple" name="detailFiles">
				</div>
			</div>
			<ul class="shop_img_list" id="sortable">
				<c:forEach items="${files}" var="file" varStatus="status">
					<li index="${file.stdoDtlFlId}" id="img_id_${file.stdoDtlFlId}">
						<div class="img_area">
							<button type="button" class="btn_del_img" >삭제</button>
							<c:if test="${empty file.flNm }">
								<img src="${contextPath}/resources/images/common/no_img.gif" alt="등록 이미지 없음">
							</c:if>
							<c:if test="${not empty file.flNm }">
								<img src="${contextPath}/img/thumb/${file.flNm}" onerror="this.src='${contextPath}/resources/images/common/no_img.gif'" >
							</c:if>
						</div>
						<div class="text">${file.flOrd + 1}</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- form  -->
		<form id="stdoFrm" action="${contextPath}/api/updateStudioDetail"			method="post">
			<div class="box_area full">
				<h3 class="sub_stit">스튜디오 설명</h3>
				<textarea class="textarea_shop" name="stdoDsc" id="stdoDsc"
					placeholder="스튜디오 설명을 작성해주세요 (1,000자 이내)">${detail.stdoDsc }</textarea>
			</div>
			<%-- 				${detail } --%>
			<div class="box_area full">
				<h3 class="sub_stit">영업시간 관리</h3>
				<ul class="shop_time_select">
					<li><span class="tit">평일</span> <select name="openDayStartTm"
						id="openDayStartTm" class="time">
							<c:forEach begin="0" end="23" var="item">
								<option value="${item}"	${item eq detail.openDayStrtTm ? 'selected' : ''}>${item}시</option>
							</c:forEach>
					</select> ~ <select name="openDayEndTm" id="openDayEndTm" class="time">
							<c:forEach begin="0" end="23" var="item">
								<option value="${item}"	${item eq detail.openDayEndTm ? 'selected' : ''}>${item}시</option>
							</c:forEach>
					</select></li>
					<li><span class="tit">주말</span> <select name="wkndStartTm"
						id="wkndStartTm" class="time">
							<c:forEach begin="0" end="23" var="item">
								<option value="${item}"	${item eq detail.wkndStrtTm ? 'selected' : ''}>${item}시</option>
							</c:forEach>
					</select> ~ <select name="wkndDayEndTm" id="wkndDayEndTm" class="time">
							<c:forEach begin="0" end="23" var="item">
								<option value="${item}"	${item eq detail.wkndEndTm ? 'selected' : ''}>${item}시</option>
							</c:forEach>
					</select></li>
				</ul>
			</div>

			<div class="box_area full">
				<h3 class="sub_stit">휴무일 관리</h3>
				<ul class="shop_time_select">
					<li><select name="dateType" id="dateType">
							<option value="W">매주</option>
							<option value="D">지정일</option>
					</select>  <select id="weekVal" name="weekVal">
					</select> <input type="text" name="dateVal" id="dateVal"
						class="form-control" style="display: none; width: 122px;" readonly />
						<button type="button" id="btnAdd" class="btn_s_form">추가</button></li>
					<li>
						<div class="multi vgT">
							<ul id="holiday" name="holiday"></ul>
							<!-- 								<select name="holiday" id="holiday" class="form-control" multiple="multiple" readonly> -->
							<!-- 								</select> -->
						</div>
						<button type="button" id="btnDel" class="btn_form_del">삭제</button>
					</li>
				</ul>
				
<!-- 				<div class="btn_area"> -->
<!-- 					<button type="button" class="btn_form" id="mobileBtn">Mobile 미리보기</button> -->
<!-- 					<button type="button" class="btn_form" id="pcBtn">PC 미리보기</button> -->
<!-- 					<button type="button" class="btn_save" id="submitBtn">저장</button> -->
<!-- 				</div> -->
			</div>

			<div class="box_area full">
				<h3 class="sub_stit">키워드 관리</h3>
				<div class="keyword_left">
					<div class="multi">
						<ul id="keyword">
						</ul>
					</div>
				</div>
				<div class="keyword_btn">
					<button type="button" class="btn_left" id="leftBtn">좌측이동</button>
					<button type="button" class="btn_right" id="rightBtn">우측이동</button>
				</div>
				<div class="keyword_right">
					<div class="multi">
						<ul id="keywordCate"></ul>
					</div>
				</div>
<!-- 				<p class="keyword_text">주소 및 전화번호 변경은 02-0000-0000로 문의 바랍니다.</p> -->
				<div class="btn_area">
					<button type="button" class="btn_form" id="mobileBtn">Mobile 미리보기</button>
					<button type="button" class="btn_form" id="pcBtn">PC 미리보기</button>
					<button type="button" class="btn_save" id="submitBtn">저장</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.ui.widget.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.fileupload.js"></script>
<!-- .content -->
<script src="${contextPath}/resources/js/store/storeView.js"></script>
<script>
	var contextPath = '${contextPath}';
	// 	var brdCateCd = '${brdCateCd}';
</script>