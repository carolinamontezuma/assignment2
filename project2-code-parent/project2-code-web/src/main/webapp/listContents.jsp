<%@page import="ejb.ContentEJB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>
<%@ page import ="javax.ejb.Local"%>
<!DOCTYPE html>
<head>
	
<title>Insert title here</title>
</head>
<body>

<p>ALO </p>
<%  
// retrieve your list from the request, with casting 
ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("listDirector");

// print the information about every category of the list
for(ContentDTO content : list) {
    out.println(content.getTitle());
    out.println(content.getYear());
    out.println(content.getCategory());
}
%>
   

</body>
</html>