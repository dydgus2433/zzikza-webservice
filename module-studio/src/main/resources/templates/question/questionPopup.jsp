<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<!-- popup -->
	<div class="popup popup_qna" style="display: none;">
		<div class="inner">
			<form action="" id="popupFrm">
			<input type="hidden" value="${detail.stdoQstnId}" name="stdoQstnId"/>
			<input type="hidden" value="A" name="qstnStatCd"/>
			<div class="popup_head">
				<h1>${detail.userName } 고객님 <span>문의시간 : ${detail.regDt }</span></h1>
				<button type="button" class="btn_popup_close">창닫기</button>
			</div>
			<div class="popup_body">
				<table class="tInsert">
					<caption></caption>
					<colgroup>
						<col style="width:100%">
					</colgroup>
					<tbody>
						<tr>
							<th>일정상세</th>
						</tr>
						<tr>
							<td><textarea class="w100 h120" readonly="readonly">${detail.qstnContent }</textarea></td>
						</tr>
						<tr>
							<th>답변내용</th>
						</tr>
						<tr>
							<td><textarea name="replContent" class="w100 h120" <c:if test="${detail.qstnStatCd ne 'Q' }">readonly</c:if>>${detail.replContent }</textarea></td>
						</tr>
					</tbody>
				</table>
				<div class="pop_btn_area">
				
					<c:choose>
						<c:when test="${detail.qstnStatCd eq 'Q' }">
							<button type="button" class="btn_pop_del" id="btnCancel">취소</button>
							<button type="button" id="btnOk">확인</button>
						</c:when>
						<c:when test="${detail.qstnStatCd ne 'Q' }">
							<button type="button" id="btnCancel">확인</button>
						</c:when>
						<c:otherwise>
							<button type="button" id="btnCancel">확인</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</form>
		</div>
	</div>
	<!--// popup -->
	<script>
	$(document).ready(function(){
		console.log("문의관리목록");
		
		$( ".datepicker" ).datepicker({
			dateFormat: "yy-mm-dd",
	    });
		
		$(".popup").hide();
// 		$(".btn_qna_new").off('click').on('click',function () {
// 			$(".popup_qna").show();
// 		});

// 		$(".btn_qna_view").off('click').on('click',function () {
// 			$(".popup_qna").show();
// 		});

		$(".btn_popup_close").off('click').on('click',function () {
			$(".popup").hide();
		});
		
		$("#btnCancel").off('click').on('click',function () {
			$(".popup").hide();
		});
		
		$("#btnOk").off('click').on('click',function(){
			var formData = new FormData($("#popupFrm")[0])
			
			$.ajax({
				url : contextPath + '/api/updateQuestionRepl',
				data : formData,
				method : 'post',
				processData: false,
		        contentType: false,
			}).done(function(result){
				alert('등록되었습니다.');
				$(".popup").hide();
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown) {
		    	console.error('FAIL REQUEST: ', textStatus);
				alert('처리중 오류가 발생하였습니다.');
		    }).always(function() {
		    	console.log('DONE');
		    });
			
		});

	});
	</script>