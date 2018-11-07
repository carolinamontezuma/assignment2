<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body onload="redirect()">
	<form action="PlayersTallerThan" method="get" id="redirect_form">
		<input type="hidden" name="nullSource" value="">
	</form>
	
	<script>
		function redirect()
		{
			document.getElementById("redirect_form").submit();
		}
	</script>
</body>
</html>