<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!-- Main Quill library -->
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>

<!-- Theme included stylesheets -->
<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
<link href="https://cdn.quilljs.com/1.3.6/quill.bubble.css" rel="stylesheet">

<!-- Core build with no theme, formatting, non-essential modules -->
<link href="https://cdn.quilljs.com/1.3.6/quill.core.css" rel="stylesheet">
<script src="https://cdn.quilljs.com/1.3.6/quill.core.js"></script>

<!-- left_area -->
		<div class="left_content_area">
			<div class="left_content_head">
				<h2 class="sub_tit">게시판</h2>
			</div>
			<div class="inner">
				<ul class="tabs">
					<c:forEach items="${commonCodes}" var="code" >
						<li><a href="${contextPath}/board/${code.commCd}" <c:if test="${commonCode.commCd eq (code.commCd)}"> class='active'</c:if>>${code.commCdNm}</a></li>
					</c:forEach>
				</ul>
				<div class="box_area full">
<!-- 					<h3 class="sub_stit">Q&A</h3> -->
					<form id="boardFrm" enctype="multipart/form-data">
					<input type="hidden" name="brdCateCd" value="${brdCateCd}"/>
					<table class="tInsert">
						<caption>입력폼입니다.</caption>
						<colgroup>
							<col class="td02">
							<col style="">
						</colgroup>
						<thead>
							<tr>
								<th colspan="2" class="textL"><input type="text" class="w600" name="title" placeholder="제목을 입력해주세요"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="2">
<!-- 									<textarea rows="" cols="" name="content" class="textarea_shop" style="display: none;"></textarea> -->
									<link href="https://cdn.quilljs.com/1.0.0/quill.snow.css" rel="stylesheet">

									<div id="quill">
									  <p></p>
									</div>
									
									<script src="https://cdn.quilljs.com/1.0.0/quill.js"></script>
								</td>
								
								
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th>첨부파일(용량제한 2MB)</th>
								<td>
									<div class="filebox">
									  <input class="upload-name" value="파일선택" disabled="disabled"><label for="fileupload">찾아보기</label>
									  <input name="files" id="fileupload"  class="upload-hidden"	 type="file" multiple />
									</div>
									<table>
										<tr>
											<td colspan="2" class="file" id="fileList"></td>
										</tr>
									</table>
								</td>
							</tr>
						</tfoot>
					</table>
					<div class="btn_area right">
						<button type="button" class="btn_form" id="btnInsert">등록</button>
					</div>
					</form>
				</div>
			</div>
		</div>		
		<!--// left_area -->
<script src="${contextPath}/resources/js/board/boardWrite.js"></script>
<script>
var contextPath = '${contextPath }'; 
var brdCateCd = '${brdCateCd}';
</script>
