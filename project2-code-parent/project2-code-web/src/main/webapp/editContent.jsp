<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
</head>
<title>Edit content</title>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

List<String> categories = (List<String>) request.getAttribute("categories");
%>

<%! public int id; %>
<c:if test= "${action == 'selectEdit'}">
  
   <% 
	String title =request.getParameter("content_title");
	String director = request.getParameter("content_director");
	String category = request.getParameter("content_category");
	int year = Integer.parseInt(request.getParameter("content_year"));
	id = Integer.parseInt(request.getParameter("content_id"));
	out.println("Title:  "+title);
	out.println("ID: "+id);
	%>
	<form action="PlayersTallerThan" method="get"> 
	<input type="hidden" name="id1" value="<%=id%>"> 
    <input type="submit" name="editTitle" id ="editTitle"  value="Edit">
 	</form>
 	<br>
	<%
	out.println("Director:  "+director);
	%>
	<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="editDirector" id ="editDirector"  value="Edit">
 	</form>
 	<br>
 	<%
 	out.println("Category:  "+category);
 	%>
	<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="editCategory" id ="editCategory"  value="Edit">
 	</form>
 	<br>
 	<%
 	out.println("Year:  "+year);
 	%>
 	<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="editYear" id ="editYear"  value="Edit">
 	</form>
</c:if>


<c:if test= "${action == 'edittitle'}">
	<form action="PlayersTallerThan" method="get">
  		New title: <input type="text" name="newT" placeholder="Title" required><br>
       	<input type="hidden" name="opcaoEdit" value="<%=1%>"> 
       	<input type="hidden" name="id" value="<%=id%>"> 
       <input type="submit" name="editarTitulo" id ="editarTitulo" value="Confirm">
       	
  	</form>
</c:if>

<c:if test= "${action == 'editdirector'}">
	<form action="PlayersTallerThan" method="get">
  		New director: <input type="text" name="newD" placeholder="Director" required><br>
       	<input type="hidden" name="opcaoEdit" value="<%=2%>"> 
       	<input type="hidden" name="id" value="<%=id%>"> 
       <input type="submit" name="editarDirector" id ="editarDirector" value="Confirm">
  	</form>
</c:if>

<c:if test= "${action == 'editcategory'}">
	<form action="PlayersTallerThan" method="get">
  		Category: <select name="newC">
			  <%for(String category : categories){
					%><option value="<%= category %>">
			  		<%out.println(category);%></option>
				<% } %>
		</select> 	
		<input type="hidden" name="opcaoEdit" value="<%=3%>"> 
       	<input type="hidden" name="id" value="<%=id%>"> 
        <input type="submit" name="editarCategoria" id ="editarCategoria" value="Confirm">	
  	</form>
</c:if>
<c:if test= "${action == 'edityear'}">
	<form action="PlayersTallerThan" method="get">
  		New year: <input type="text" name="newY" placeholder="Year" required><br>	
  		<input type="hidden" name="opcaoEdit" value="<%=4%>"> 
       	<input type="hidden" name="id" value="<%=id%>"> 
        <input type="submit" name="editarAno" id ="editarAno" value="Confirm">	
  	</form>
</c:if>


</body>
</html>