<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Registar</p>
	<div class="container">
    <label for="uname"><b>Email</b></label>
    <input type="text" placeholder="Enter email" name="email" required>
    
    <label for="uname"><b>Username</b></label>
    <input type="text" placeholder="Enter username" name="user" required>
     
    <label for="psw"><b>Cartão de Crédito</b></label>
    <input type="password" placeholder="Enter credit card" name="credit" required>
    
    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required>

    <button type="submit">Login</button>
    <label>
      <input type="checkbox" checked="checked" name="remember"> Remember me
    </label>
</body>
</html>