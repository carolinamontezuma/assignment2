<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	%>

	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
<title>Login.jsp</title>
</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

<div class="container aux">

	<p class="Title">Login</p>
	
	<form action="PlayersTallerThan" method="post">
			<% String message= (String)request.getAttribute("message"); %>
			<label class ="Text"for="uname"><b>Email</b></label>
			<input type="email" placeholder="Email" name="fmail" required>
			<label class="Text" for="psw"><b>Password</b></label>
			<input type="password" placeholder="Password" name="fpass" required>
			<button class ="botoes" type="submit" name="login">Login</button>
			<% if(message!=null){ %>
			<p class="Text"><% out.print(message); %>
			<% } %>
		
	</form>
	
	<br>
	
	<div class="Text">Don't have an account yet?</div>
	 <form action="PlayersTallerThan" method="get"> 
   	<input class ="botoes" type="submit" name="Registar" id ="Registar" value="Register">
 </form> 
 </div>
</body>
</html>