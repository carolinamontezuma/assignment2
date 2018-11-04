<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login.jsp</title>
</head>
<body>

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
	
	<p> Ainda não tem conta? <a href="Registar.jsp">Registe-se</a> </p>
</body>
</html>