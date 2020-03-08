<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${not empty detail }">
<script>
	
</script>
</c:if>
<!-- left_area -->
		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">요청관리 [촬영신청]</h2>
			</div>
			<div class="inner">
				
				<form id="reqFrm" action="${contextPath}/api/insertRequestRepl" method="post">
				<div class="box_area full">
					<h3 class="sub_stit">상품명</h3>
					<input type="text" name="title" class="form_goods" subtitle="상품명" placeholder="상품명을 입력해 주세요." value="${detail.title }" required="required">
				</div>
				<div class="box_area full">
					<h3 class="sub_stit">
						상품 소개 사진
						<c:if test="${empty detail }"><span>상품을 대표하는 사진을 올려주세요 (최대 10장)</span></c:if>
					</h3>
					<c:if test="${empty detail }">
					<div class="shop_fileup_area">
						<span class="tit">파일 사이즈</span>
						<div class="filebox">
						  <input class="upload-name"	value="<c:if test="${fn:length(files) > 0}"><c:out value="${fn:length(files)}"/>개의 파일</c:if><c:if test="${fn:length(files) <= 0}">파일선택</c:if>"
						disabled="disabled"><label for="detailFiles">찾아보기</label>
					<input type="file" id="detailFiles" class="upload-hidden"
						multiple="multiple" name="detailFiles">
						</div>
					</div>
					</c:if>
					<ul class="shop_img_list" <c:if test="${empty detail }">id="sortable"</c:if>>
						<c:forEach items="${files}" var="file" varStatus="status">
							<li index="${file.stdoDtlFlId}" id="img_id_${status.index }">
								<div class="img_area">
									<c:if test="${empty detail }">
									<button type="button" class="btn_del_img">삭제</button>
									</c:if>
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
				<div class="box_area full">
					<h3 class="sub_stit">상품 금액</h3>
					<input type="number" name="prdPrc" class="form_goods number" placeholder="상품 금액을 입력해 주세요." value="${detail.prdPrc }">
				</div>
				
				<div class="box_area full">
				<h3 class="sub_stit">촬영소요시간</h3>
				<ul class="shop_time_select">
					<li>
							<input type="number" class="form_time" name="prdHour" value="${detail.prdHour }" required="required" placeholder="2" min="0">
							<span class="tit">시간</span>
							<input type="hidden" class="form_time"  name="prdMin" value="00" required="required" placeholder="30" min="0" max="59">
<%-- 							<input type="number" class="form_time"  name="prdMin" value="${detail.prdMin }" required="required" placeholder="30" min="0" max="59"> --%>
<!-- 							<span class="tit">분</span> -->
						</li>
				</ul>
				</div>
				<div class="box_area full">
					<h3 class="sub_stit">상품 요약설명</h3>
					<textarea class="textarea_shop" name="prdBrfDsc" placeholder="상품  요약설명은 고객에게 상품구성 및 소개를 간략히하는 영역입니다." >${detail.prdBrfDsc }</textarea>					
				</div>
				<div class="box_area full">
					
					<input type="hidden" id="reqId" name="reqId" value="${reqId }">
					<h3 class="sub_stit">상품 설명</h3>
<%-- 					<textarea class="textarea_shop" id="quill" name="prdDsc" placeholder="상품설명은 고객에게 상품구성 및 소개를 하는 영역입니다. 상세히 적어주세요." >${detail.prdDsc }</textarea> --%>
					
					<div id="quill">
						${detail.prdDsc }
					</div>
					<div class="btn_area">
						<button type="button" class="btn_form" id="mobileBtn">Mobile 미리보기</button>
						<button type="button" class="btn_form" id="pcBtn">PC 미리보기</button>
						<c:if test="${empty detail }"><button type="button" class="btn_save">촬영신청</button></c:if>
						
					</div>
					
				</div>
				</form>
			</div>
		</div>		
		<!--// left_area -->
		
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.ui.widget.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.iframe-transport.js"></script>
<script type="text/javascript"	src="${contextPath}/resources/js/upload/jquery.fileupload.js"></script>
<script src="${contextPath}/resources/js/request/requestProductView.js"></script>
<script>
	var contextPath = '${contextPath}';
	var brdCateCd = '${brdCateCd}';
</script>