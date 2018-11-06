<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<% 
 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("list");
	for(ContentDTO content : list) {
    	out.println(content.getTitle());
    	int id = content.getID();%>     	
     	<form action="PlayersTallerThan" method="get"> 
    	<input type="hidden" name="user_id" value="<%=id%>">    	
        <input type="submit" name="remove" id ="remove"  value="Remove">
        </form>
		<% 
		
	}
%>
</body>
</html>