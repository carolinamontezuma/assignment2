<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Welcome, user!</p>
	<!--  <a href="Login.jsp">Edit personal information</a>
	<a href="listContents.jsp">Search for all the content</a>
	<a href="listContents.jsp">Search for all the content of a given category</a>
	<a href="listContents.jsp">Search for all the content of a given director</a>
	<a href="listContents.jsp">Search for all the content within a range of years</a>
	<a href="listContents.jsp">See my watch list</a>
	<a href="index.jsp">Delete my account</a>	-->
	
	<form action="PlayersTallerThan" method="get"> 
    	Item's name : <input type="text" name="director" id="director"><br>
    <input type="submit" value="Register">
 	</form>
</body>
</html>