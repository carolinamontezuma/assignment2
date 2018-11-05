<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<body>
<%
if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
%>

<p>Hello</p>
<form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="userScreen" id ="userScreen" value="User Screen">
 </form>
<form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="managerScreen" id ="managerscreen" value="Manager Screen">
</form>
<form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="Login" id ="Login" value="Login">
 </form>
 
 <form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="Registar" id ="Registar" value="Registar">
 </form> 	
</body>
</html>