<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>managerScreen.jsp</title>
<link rel="stylesheet" href="index.css">
</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
String mensagem = (String)request.getAttribute("message");
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

 <c:if test= "${action == 'teste'}"> 
 	<c:if test= "${valor == 1}"> 
	<p> Sucess creating new content!</p>
	<form action="PlayersTallerThan" method="get"> 
    	<input class="botoes" type="submit" name="continueManager" id ="continueManager" value="Continue">
 	</form>
 	</c:if>
 	<c:if test= "${valor == 0}"> 
 		<p>The content already exists!</p>
 	</c:if>
 </c:if>

</body>
</html>