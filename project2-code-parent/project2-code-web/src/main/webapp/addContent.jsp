<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

List<String> categories = (List<String>) request.getAttribute("categories");
%>

	<form action="PlayersTallerThan" method="get">
  		Tile: <input type="text" name="ftitle" placeholder="Title" required><br>
  		Director: <input type="text" name="fdirector" placeholder="Director" required><br>
  		Category: 
  		<select name="fcategory">
			  <%for(String category : categories){
					%><option value="<%= category %>">
			  		<%out.println(category);%></option>
				<% } %>
		</select> 		
		<br>
		  Year: <input type="number" name="fyear" placeholder="Year" required><br>
		 <input type="submit" name="addContent" id ="addContent" value="Confirm">
		
	</form>
</body>
</html>