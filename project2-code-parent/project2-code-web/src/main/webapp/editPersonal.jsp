<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="changeEmail" id="changeEmail" value="Change E-mail">
 	</form>
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="changePass" id ="changePass" value="Change Password">
 	</form>
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listDirector" id ="listDirector" value="Change credit card">
 	</form>
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listDirector" id ="listDirector" value="Delete account">
 	</form>
 	<c:if test= "${action == 'foj'}">
   <% 
   out.println("Imprimir");
	Object ob = request.getAttribute("fo");
	   out.println(ob.toString());

   %>
   
</c:if>
 	
</body>
</html>