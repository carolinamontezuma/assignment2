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
else
	if((boolean)request.getSession().getAttribute("loginIsAdmin"))
		request.getRequestDispatcher("/managerScreen.jsp").forward(request, response);
%>
	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %> !</p>
	
	<p>Suggested Content</p>
	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="Home" id ="Home" value="Home">
 	</form>
	
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="listWatchList" id ="listWatchList" value="My watchlist">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listAll" id ="listAll"  value="List content">    	
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="editPersonal" id="editPersonal" value="Edit account">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input type="submit" name="logout" id="logout" value="Logout">
 	</form>
 	
</body>
</html>