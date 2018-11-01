<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registar.jsp</title>
</head>
<body>
<p>Registar</p>
	<form action="PlayersTallerThan" method="post">
  		Name: <input type="text" name="fname" placeholder="Name" required><br>
  		Email: <input type="email" name="fmail" placeholder="example@email.com" required><br>
  		Password: <input type="password" name="fpass" placeholder="Password" required><br>
  		Credit card: <input type="number" name="fcard" placeholder="COLOCAR UM PLACEHOLDER" required><br>
  		<input type="submit" name="registar" value="Submit">
	</form>
</body>
</html>