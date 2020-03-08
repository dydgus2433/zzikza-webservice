<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<!-- popup -->
	<div class="popup popup_book" style="display: none;">
		<form id="popupFrm">
		<input type="hidden" id="rsrvId" name="rsrvId" value="${detail.rsrvId }">
		<div class="inner">
			<div class="popup_head">
				<h1>일정등록</h1>
				<button type="button" class="btn_popup_close">창닫기</button>
			</div>
			<div class="popup_body">
				<table class="tInsert">
					<caption></caption>
					<colgroup>
						<col style="width:20%">
						<col style="width:80%">
					</colgroup>
					<tbody>
						<tr>
							<th>시작시간</th>
							<td>
								<input type="text" class="date datepicker" id="rsrvStrtDt" name="rsrvStrtDt" value="${detail.rsrvStrtDt }">
								<select id="rsrvStrtHour" name="rsrvStrtHour">
									<c:forEach begin="0" end="23" var="item">
										<option value="<fmt:formatNumber minIntegerDigits="2" value="${item}" />"
											${item eq detail.rsrvStrtHour ? 'selected' : ''}><fmt:formatNumber minIntegerDigits="2" value="${item}" />시</option>
									</c:forEach>
								</select>
								<select id="rsrvStrtMin" name="rsrvStrtMin">
									<c:forEach begin="0" end="59" var="item">
										<option value="<fmt:formatNumber minIntegerDigits="2" value="${item}" />"
											${item eq detail.rsrvStrtMin ? 'selected' : ''}><fmt:formatNumber minIntegerDigits="2" value="${item}" />분</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>종료시간</th>
							<td>
								<input type="text" class="date datepicker" id="rsrvEndDt" name="rsrvEndDt" value="${detail.rsrvEndDt }">
								<select id="rsrvEndHour" name="rsrvEndHour">
									<c:forEach begin="0" end="23" var="item">
										<option value="<fmt:formatNumber minIntegerDigits="2" value="${item}" />"
											${item eq detail.rsrvEndHour ? 'selected' : ''}><fmt:formatNumber minIntegerDigits="2" value="${item}" />시</option>
									</c:forEach>
								</select>
								<select id="rsrvEndMin" name="rsrvEndMin">
									<c:forEach begin="0" end="59" var="item">
										<option value="<fmt:formatNumber minIntegerDigits="2" value="${item}" />"
											${item eq detail.rsrvEndMin ? 'selected' : ''}><fmt:formatNumber minIntegerDigits="2" value="${item}" />분</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>고객명</th>
							<td><input type="text" name="userNm" value="${detail.userNm }" placeholder="고객명을 입력해주세요."></td>
						</tr>
						<tr>
							<th>연락처</th>
							<td><input type="text" name="tel" value="${detail.tel }" placeholder="연락처를 입력해주세요."></td>
						</tr>
						<tr>
							<th>일정명</th>
							<td><input type="text" name="scheduleNm" value="${detail.scheduleNm }"  placeholder="일정명을 입력해주세요."></td>
						</tr>
						<tr>
							<th>촬영인원</th>
							<td><input type="number" name="ppCnt" value="${detail.ppCnt }" placeholder="촬영인원을 입력해주세요."></td>
						</tr>
						<tr>
							<th>일정상세</th>
							<td><textarea class="w100" name="cstmReq" placeholder="일정상세를 입력해주세요.">${detail.cstmReq }</textarea></td>
						</tr>
						<tr>
							<th>촬영금액</th>
							<td><input type="number" class="" id="prdPrc" name="prdPrc" value="${detail.prdPrc }" placeholder="촬영금액을 입력해주세요."><span class="text_won">원</span></td>
						</tr>
						<tr>
							<th>예약상태</th>
							<td>
							<select id="rsrvStatCd" name="rsrvStatCd" >
								<c:forEach items="${reservationCodeList }" var="code">
									<option value="${code.commCd }" <c:if test="${ code.commCd eq detail.rsrvStatCd }">selected</c:if> >${code.commCdNm }</option>
								</c:forEach>
							</select>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="pop_btn_area">
					<c:choose>
						<c:when test="${empty stdoId}"><button type="button" onclick="javascript:alert('세션이 만료되었습니다.');location.href='/';">확인</button></c:when>
						<c:when test="${not empty detail }"><button type="button" class="btn_pop_cancel" id="btnCancel">취소</button><button type="button" class="btn_pop_mod" id="btnMod">일정수정</button></c:when>
						<c:when test="${empty detail }"><button type="button" id="btnOk">확인</button></c:when>
						<c:otherwise><button type="button" onclick="javascript:alert('세션이 만료되었습니다.');location.href='/'">확인1</button></c:otherwise>
					</c:choose>
					<!--button type="button" class="btn_pop_del">일정삭제</button-->
				</div>
			</div>
		</div>
		</form>
	</div>
	<!--// popup -->
	<script>
	$(document).ready(function(){
		console.log("예약관리목록");
		
		$( ".datepicker" ).datepicker({
			dateFormat: "yy-mm-dd"
	    });
		
		var settingDate = '${date}';
		if($("#rsrvStrtDt").val() && $("#rsrvStrtDt").val()){
			
		}else{
			$( ".datepicker" ).datepicker('setDate' , new Date('${date}'));
		}
		//기본날짜 세팅
		
		
		$(".popup").hide();
		$(".btn_book_new").off('click').on('click',function () {
			$.ajax({
				url : contextPath + '/schedule/popup',
				data : {date : $("#dateVal").val()},
			}).done( function(result){
				console.log(result);
				$("#popup").html('');
				$("#popup").html(result);
				$(".popup_book").show();
			}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
			$("#startDt").val($("#dateVal").val());
		});
		
		$(".reservation").off('click').on('click',function(){
			var rsrvId = $(this).attr("seq");
			console.log(rsrvId);
			
			$.ajax({
				url : contextPath + '/schedule/popup',
				data : {rsrvId : rsrvId , date : $("#dateVal").val()},
			}).done( function(result){
				console.log(result);
				$("#popup").html('');
				$("#popup").html(result);
				$(".popup_book").show();
			}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
		});

		$(".btn_qna_view").off('click').on('click',function () {
			$(".popup_qna").show();
		});

		$(".btn_popup_close").off('click').on('click',function () {
			$(".popup").hide();
		});
		
		$("#btnCancel").off('click').on('click',function () {
			$(".popup").hide();
		});
		
		//숫자만 입력
		$("input[type='number']").off('keypress').on('keypress',function(e){
	    	if (e.which && (e.which  > 47 && e.which  < 58 || e.which == 8)) {
	    		
	    	  } else {
	    	    event.preventDefault();
	    	  }
	    });
		
		
		$("#btnOk").off('click').on('click',function(){
			if(!validate()){
				return;
			}
			
			var formData = new FormData($("#popupFrm")[0])
			
			
			$.ajax({
				url : contextPath + '/api/insertReservation',
				data : formData,
				method : 'post',
				processData: false,
		        contentType: false,
			}).done(function(result){
				alert('등록되었습니다.')
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
			
		});
		

		$("#btnMod").off('click').on('click',function(){
			if(!validate()){
				return;
			}
			
			var formData = new FormData($("#popupFrm")[0])
			
			$.ajax({
				url : contextPath + '/api/updateReservation',
				data : formData,
				method : 'post',
				processData: false,
		        contentType: false,
			}).done(function(result){
				console.log(result);
				alert('수정되었습니다.')
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
			
		});
		
		//예약취소가 맞을 듯 
		$("#btnDel").off('click').on('click',function(){
			
			if(confirm("환불처리가 됩니다. 진행하시겠습니까?")){
				
			}
			
			var formData = new FormData($("#popupFrm")[0])
			
			$.ajax({
				url : contextPath + '/api/deleteReservation',
				data : formData,
				method : 'post',
				processData: false,
		        contentType: false,
			}).done(function(result){
				console.log(result);
				alert('삭제되었습니다.')
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
		});
	});
	

	function validate(){
		var inputs = $("input[type='text']");
		for(var i = 0; i < inputs.length; i++){
			var input = inputs[i];
			if($(input).val() == ""){
				var name = input.name;
				alert(input.placeholder);
				$(input).focus();
				return false;
			}
		}
		
		inputs = $("input[type='number']");
		for(var i = 0; i < inputs.length; i++){
			var input = inputs[i];
			if($(input).val() == ""){
				var name = input.name;
				alert(input.placeholder);
				$(input).focus();
				return false;
			}
		}
		
		inputs = $("textarea[name='cstmReq']");
		for(var i = 0; i < inputs.length; i++){
			var input = inputs[i];
			if($(input).val() == ""){
				alert("스튜디오 설명을 입력하세요.");
				$(input).focus();
				return false;
			}
		}
		
		var startDateTm = $("#rsrvStrtDt").val()+'-'+$("#rsrvStrtHour").val()+':'+ $("#rsrvStrtMin").val() ;
		var endDateTm = $("#rsrvEndDt").val()+'-'+$("#rsrvEndHour").val()+':'+ $("#rsrvEndMin").val() ;
		startDateTm = startDateTm.replace(/-/gi,'/');
		endDateTm = endDateTm.replace(/-/gi,'/');
		if (new Date(startDateTm) >= new Date(endDateTm)) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

		if($("#prdPrc").val() == ''){
			$("#prdPrc").val(0);
		}
		
		return true;
	}

	</script>