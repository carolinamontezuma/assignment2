<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registar.jsp</title>
<link rel="stylesheet" href="index.css">

</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

<p class="Text">Registar</p>
<div class="Text">
	<form action="PlayersTallerThan" method="post">
  		Name <input type="text" name="fname" placeholder="Name" required><br>
  		Email <input type="email" name="fmail" placeholder="example@email.com" required><br>
  		Password <input type="password" name="fpass" placeholder="Password" required><br>
  		Credit Card
  		<input type="number" name="fcard1" maxlength="1" required>
  		<input type="number" name="fcard2" maxlength="1" required>
  		<input type="number" name="fcard3" maxlength="1" required>
  		<input type="number" name="fcard4" maxlength="1" required><br>
  		<input class="botoes" type="submit" name="registar" value="Create account">
	</form>
</div>
</body>
</html>