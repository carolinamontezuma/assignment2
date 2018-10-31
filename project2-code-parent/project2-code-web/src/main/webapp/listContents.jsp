<%@page import="ejb.ContentEJB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
<%@ page import = "ejb.ContentEJBRemote"%>
<%@ page import = "dto.ContentDTO"%>
<%@ page import ="javax.ejb.Local"%>
<!DOCTYPE html>
<head>
<%
@EJB
ContentEJB ejb = new ContentEJB();;
ContentDTO content = new ContentDTO(); 
Object ob =request.getAttribute("listDirector");
String directorName = ob.toString();
%>
<title>Insert title here</title>
</head>
<body>

<p>ALO </p>




 	
  <%
  List<ContentDTO> list = ejb.seeContentFromDirector(directorName);
  for(int i = 0; i < list.size(); i++) {
                content = list.get(i);
                //out.println(actor.getId());
                //out.println(actor.getFirstname());
                //out.println(actor.getLastname());
  }
   %>
   

</body>
</html>