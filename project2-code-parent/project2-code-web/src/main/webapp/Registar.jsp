<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registar.jsp</title>
</head>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

<p>Registar</p>
	<form action="PlayersTallerThan" method="post">
  		Nome: <input type="text" name="fname" placeholder="Name" required><br>
  		Email: <input type="email" name="fmail" placeholder="example@email.com" required><br>
  		Password: <input type="password" name="fpass" placeholder="Password" required><br>
  		Cartão de crédito:
  		<input type="number" name="fcard1" maxlength="4" required>
  		<input type="number" name="fcard2" maxlength="4" required>
  		<input type="number" name="fcard3" maxlength="4" required>
  		<input type="number" name="fcard4" maxlength="4" required><br>
  		<input type="submit" name="registar" value="Criar conta">
	</form>
</body>
</html>