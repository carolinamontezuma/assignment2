<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>managerScreen.jsp</title>
</head>
<body>
<c:if test= "${action == 'newcontent'}">

	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="newContent" id ="newContent" value="Add new content">
 	</form>
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="editContent" id ="editContent" value="Edit content">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="deleteContent" id ="deleteContent" value="Delete content">
 	</form>
 </c:if>
 <c:if test= "${action == 'teste'}"> 
 	<c:if test= "${valor == 1}"> 
	<p> Sucess creating new content!</p>
	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="continueManager" id ="continueManager" value="Continue">
 	</form>
 	</c:if>
 	<c:if test= "${valor == 0}"> 
 		<p>The content already exists!</p>
 	</c:if>
 </c:if>
 
</body>
</html>