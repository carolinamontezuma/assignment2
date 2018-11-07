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

boolean isAdmin = (boolean)request.getSession().getAttribute("loginIsAdmin");
%>

<p>Hello</p>
 <% if(!isAdmin){ %>
<form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="userScreen" id ="userScreen" value="User Screen">
 </form>
 <% } %>
  
 <% if(isAdmin){ %>
<form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="managerScreen" id ="managerscreen" value="Manager Screen">
   	
</form>
 <% } %>
<form action="PlayersTallerThan" method="post"> 
   	<input type="submit" name="logout" id ="Logout" value="Logout">
 </form> 	
</body>
</html>