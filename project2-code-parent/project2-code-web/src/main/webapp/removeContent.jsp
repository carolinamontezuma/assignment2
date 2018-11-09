<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
table {
    border-spacing: 0;
    width: 100%;
    border: 1px solid #ddd;
}

th, td {
    text-align: left;
    padding: 16px;
    border: 1px solid #ddd;
}

</style>
<link rel="stylesheet" href="index.css">
</head>
<title>Remove content</title>
<body>

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("list"); 
    if(list.size()==0){
 		out.println("No contents were found!");%>
 	<% 
 	}
    else{ 
 	%>
<div id="botoesBackLog">
<form action="PlayersTallerThan" method="get">	
	<input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
	<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
</form>
</div>

 	 <table class="Text" id="myTable">
 	  <tr>
	    <th id="tituloTabela">Title</th>
	    <th id="tituloTabela">Category</th>
	   <th id="tituloTabela">Director</th>
	    <th id="tituloTabela">Year</th>
	  </tr>
	<%
 	for(ContentDTO content : list) {
 	    %>
 	  <tr>
 	    <td><% out.println(content.getTitle()); %></td>
 	    <td><% out.println(content.getCategory()); %></td>
 	   	<td><% out.println(content.Director()); %></td>
 	    <td><% out.println(content.getYear()); 
 	    	%><form action="PlayersTallerThan" method="get"> <input type="hidden" name="content_id" value="<%=content.getID()%>"> <input class="botoes bot_aux removeFromWL_aux3"  type="submit" name="remove" id ="remove" value="Remove"></form>
 	    	<% } %> </td>
 	    	
 	  </tr>
 	  </table>
 	  <% } %>

	<form action="PlayersTallerThan" method="post"> 

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