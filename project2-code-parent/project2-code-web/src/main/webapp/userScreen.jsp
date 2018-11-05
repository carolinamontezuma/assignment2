<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userScreen.jsp</title>
</head>
<body>
<%
if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
%>
	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %>!</p>
	
	<p>Suggested Content</p>
	
	
	
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="listWatchList" id ="listWatchList" value="Ver watchlist">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="search" id ="search" value="Pesquisar conteúdos">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listAll" id ="listAll"  value="Listar conteúdos">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="editPersonal" id="editPersonal" value="Editar conta">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input type="submit" name="logout" id="logout" value="Logout">
 	</form>
 	
</body>
</html>