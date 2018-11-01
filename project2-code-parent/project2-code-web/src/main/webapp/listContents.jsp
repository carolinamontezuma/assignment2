<%@page import="ejb.ContentEJB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>

<head>
	
<title>listContents.jsp</title>
</head>
<body>
<%
Object ob = request.getAttribute("action");
String action = ob.toString();
%>




<p>ALO </p>
<!--  APRESENTAÇÃO INICIAL DOS CONTEUÚDOS TODOS COM A HIPÓTESE DE ALTERAR ORDENAÇÃO -->


<c:if test= "${action == 'allContents'}">
<p>Ordenar por:</p>

<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="ascAll" id ="asc"  value="Ordem ascendente">
 </form>
 
 <form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="descAll" id ="desc"  value="Ordem descendente">
 </form>
<% 
 ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");
// print the information about every category of the list
for(ContentDTO content : list) {
    out.println(content.getTitle());
    out.println("");
    out.println(content.getYear());
    out.println("");
    out.println(content.getCategory());
    out.println("");
}
%>


<!--  SELECIONAR OPÇÃO DOS FILTROS -->
<p>Filtrar por:</p>
<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="filtroD" id ="asc"  value="Director">
 </form>
 
 <form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="filtroC" id ="desc"  value="Category">
 </form>
 
 <form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="filtroY" id ="desc"  value="Years">
 </form>
 </c:if>
 
<!-- Listar todos os nomes dos directores -->

<c:if test= "${action == 'listDirectors'}">
  
   <%  out.println("Ordenar por:");%>
   <form action="PlayersTallerThan" method="get"> 
   <input type="submit" name="ascDirector" id ="asc"  value="Ordem ascendente">
 	</form>
 
 	<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="descDirector" id ="desc"  value="Ordem descendente">
 	</form>
	<% 
 	ArrayList<String> list = (ArrayList<String>) request.getAttribute("allContents");
	for(String content : list) {
    	out.println(content);
    	out.println("\n");
	}
%>
   
</c:if>

<!-- Listar todas as categorias-->

<c:if test= "${action == 'listCategories'}">
  
   <%  out.println("Ordenar por:");%>
   <form action="PlayersTallerThan" method="get"> 
   <input type="submit" name="ascCategory" id ="asc"  value="Ordem ascendente">
 	</form>
 
 	<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="descCategory" id ="desc"  value="Ordem descendente">
 	</form>
	<% 
 	ArrayList<String> list = (ArrayList<String>) request.getAttribute("allContents");
	for(String content : list) {
    	out.println(content);
    	out.println("\n");

	}
	%>
   
</c:if>






<!-- /////  -->

<c:if test= "${action == 'director'}">
   <% 
   out.println("Director");

   ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("listDirector");

  // print the information about every category of the list
  for(ContentDTO content : list) {
      out.println(content.getTitle());
      out.println(content.getYear());
      out.println(content.getCategory());
  }
   %>
   
</c:if>



<c:if test= "${action == 'category'}">
    <% 
    out.println("Category");

 ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("listCategory");
// print the information about every category of the list
for(ContentDTO content : list) {
    out.println(content.getTitle());
    out.println(content.getYear());
    out.println(content.getCategory());
}
%>
</c:if>

<c:if test= "${action == 'years'}">
<% 
	out.println("Years");

 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("listYears");
	for(ContentDTO content : list) {
    	out.println(content.getTitle());
   	 	out.println(content.getYear());
    	out.println(content.getCategory());
	}
%>
</c:if>





</body>
</html>