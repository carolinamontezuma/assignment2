<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
</head>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>

	<% 
 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("list");
	for(ContentDTO content : list) {
    	out.println(content.getTitle());
    	int id = content.getID();%>     	
     	<form action="PlayersTallerThan" method="get"> 
    	<input type="hidden" name="content_id" value="<%=id%>">    	
        <input class="botoes" type="submit" name="remove" id ="remove"  value="Remove">
        </form>
		<% 
		
	}
%>
</body>
<form action="PlayersTallerThan" method="get">	
 		 <input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
 	</form>
</html>