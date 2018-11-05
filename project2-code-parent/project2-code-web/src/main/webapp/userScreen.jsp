<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userScreen.jsp</title>
</head>
<body>

	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %> !</p>
		<% int idUser = (Integer)request.getSession().getAttribute("loginToken"); %>			
				
	
	<p>Suggested Content</p>
	
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="listWatchList" id ="listWatchList" value="See watchlist">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="search" id ="search" value="Pesquisar conteúdos">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listAll" id ="listAll"  value="Listar conteúdos">
        <input type="hidden" name="idUser" value="<%=idUser%>">     	
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="editPersonal" id="editPersonal" value="Editar conta">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input type="submit" name="logout" id="logout" value="Logout">
 	</form>
 	
</body>
</html>