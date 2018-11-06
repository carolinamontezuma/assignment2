<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "dto.UserDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/index.jsp").forward(request, response);

if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
else
	if((boolean)request.getSession().getAttribute("loginIsAdmin"))
		request.getRequestDispatcher("/managerScreen.jsp").forward(request, response);
%>

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
<title>editPersonal.jsp</title>
</head>
<body>
<%
if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);

UserDTO dto = (UserDTO) request.getAttribute("userDTO");
String card1 = "" + dto.getCreditCard().subSequence(0, 4);
String card2 = "" + dto.getCreditCard().subSequence(4, 8);
String card3 = "" + dto.getCreditCard().subSequence(8, 12);
String card4 = "" + dto.getCreditCard().subSequence(12, 16);

%>
	<p>Edit account</p>
	
	<form action="PlayersTallerThan" method="post">
  		Name: <input type="text" name="fname" placeholder="Name" value="<%= dto.getUsername() %>"><br>
  		Email: <input type="email" name="fmail" placeholder="example@email.com" value="<%= dto.getEmail() %>"><br>
  		Password: <input type="password" name="fpass" placeholder="Password" value="<%= dto.getPassword() %>"><br>
  		Credit card:
  		<input type="number" name="fcard1" maxlength="4" value="<%= card1 %>">
  		<input type="number" name="fcard2" maxlength="4" value="<%= card2 %>">
  		<input type="number" name="fcard3" maxlength="4">
  		<input type="hidden" name="fcard3_hidden" maxlength="4" value="<%= card3 %>">
  		<input type="number" name="fcard4" maxlength="4">
  		<input type="hidden" name="fcard4_hidden" maxlength="4" value="<%= card4 %>">
  		<br><br>
  		<input type="submit" name="deleteAccount" value="Delete account" formnovalidate>
  		<br><br>
  		<input type="submit" name="cancelEdit" value="Cancel" formnovalidate>
  		<input type="submit" name="saveEdit" value="Save">
	</form>
 	
</body>
</html>