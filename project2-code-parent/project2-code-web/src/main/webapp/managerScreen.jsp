<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>managerScreen.jsp</title>
</head>
<body>
	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %>!</p>
 	
</body>
</html>