<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="ko"> <!--<![endif]-->
<tiles:insertAttribute name="head" />
{{>layout/head}}
<body>
	<tiles:insertAttribute name="header" />
	<div class="container">
		<tiles:insertAttribute name="left-panel" />
		<tiles:insertAttribute name="content" />
	</div>
	
	
	<!-- popup -->
	<div id="popup"></div>
	<!--// popup -->
</body>
</html>
