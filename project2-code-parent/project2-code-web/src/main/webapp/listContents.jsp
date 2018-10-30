<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> I have been invoked by 
<% out.print(request.getAttribute("servletName").toString());%>
</h1>
</body>
</html>