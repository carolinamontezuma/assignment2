<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login.jsp</title>
</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

	<p>Login</p>
	<form action="PlayersTallerThan" method="post">
		<div class="container">
			<label for="uname"><b>Email</b></label>
			<input type="email" placeholder="Email" name="fmail" required>
			<label for="psw"><b>Password</b></label>
			<input type="password" placeholder="Password" name="fpass" required>

			<button type="submit" name="login">Login</button>
			<label>
				<input type="checkbox" checked="checked" name="remember" value="true"> Remember me
			</label>
		</div>
	</form>
	<br>
	Don't have an account yet?
	 <form action="PlayersTallerThan" method="get"> 
   	<input type="submit" name="Registar" id ="Registar" value="Register">
 </form> 
</body>
</html>