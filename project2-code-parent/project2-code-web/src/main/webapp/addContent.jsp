<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
</head>
<title>Add content</title>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

List<String> categories = (List<String>) request.getAttribute("categories");
%>
<div class="wrap_registar aux2">
<p class="Title">Add Content</p>
	<form class="Text" action="PlayersTallerThan" method="get">
  		<b>Title</b> <input class="inputstyle_aux" type="text" name="ftitle" placeholder="Title" required><br>
  		<br>
  		<b>Director</b> <input class="inputstyle" type="text" name="fdirector" placeholder="Director" required><br>
  		<br>
  		<b>Category </b>
  		<select class="Text" class="select-box inputstyle" name="fcategory">
			  <%for(String category : categories){
					%><option value="<%= category %>">
			  		<%out.println(category);%></option>
				<% } %>
		</select> 		
		<br>
		<br>
		 <b> Year</b> <input class="inputstyle" type="number" name="fyear" placeholder="Year" required><br>
		  <br>
		 <input class="botoes" type="submit" name="addContent" id ="addContent" value="Confirm">
		
	</form>
</body>
<form action="PlayersTallerThan" method="get">	
 		 <input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
 	</form>
		</div>
</body>

 
</html>