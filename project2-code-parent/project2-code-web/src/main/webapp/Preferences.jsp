<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "dto.ContentDTO"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<% Object ob = request.getAttribute("user");
String name = ob.toString(); %>
<p>Welcome, </p> <% out.println(name); %>
<% ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");

%>


</body>
</html>