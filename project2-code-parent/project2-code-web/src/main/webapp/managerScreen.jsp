<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Management</title>
<link rel="stylesheet" href="index.css">
</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %>!</p>

 	<form action="PlayersTallerThan" method="get"> 
   		<input class="botoes" type="submit" name="newContent" id ="newContent" value="Add new content">
 	</form>
 	<form action="PlayersTallerThan" method="get"> 
    	<input class="botoes" type="submit" name="editContent" id ="editContent" value="Edit content">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input class="botoes" type="submit" name="deleteContent" id ="deleteContent" value="Delete content">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
 	</form>

</body>
</html>