<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
<%@ page import = "dto.ContentDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userScreen.jsp</title>
</head>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>
	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %> !</p>
	
	<p>Suggested Content</p>
	<%
		List<ContentDTO> suggestedContent = (List<ContentDTO>)request.getAttribute("suggestedContent");
		for(ContentDTO c : suggestedContent)
			out.println("<p>" + c.getTitle() + "</p>");
	%>
	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="Home" id ="Home" value="Home">
 	</form>
	
	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="listWatchList" id ="listWatchList" value="My watchlist">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="listAll" id ="listAll"  value="List content">    	
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
   		<input type="submit" name="editPersonal" id="editPersonal" value="Edit account">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input type="submit" name="logout" id="logout" value="Logout">
 	</form>
 	
</body>
</html>