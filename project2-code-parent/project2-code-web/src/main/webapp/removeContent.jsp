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
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/index.jsp").forward(request, response);

request.setAttribute("source", "removeContent.jsp");

if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
else
	if(!((boolean)request.getSession().getAttribute("loginIsAdmin")))
		request.getRequestDispatcher("/userScreen.jsp").forward(request, response);
%>

	<% 
 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("list");
	for(ContentDTO content : list) {
    	out.println(content.getTitle());
    	int id = content.getID();%>     	
     	<form action="PlayersTallerThan" method="get"> 
    	<input type="hidden" name="content_id" value="<%=id%>">    	
        <input type="submit" name="remove" id ="remove"  value="Remove">
        </form>
		<% 
		
	}
%>
</body>
</html>