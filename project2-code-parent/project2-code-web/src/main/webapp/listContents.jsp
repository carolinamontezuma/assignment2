<%@page import="ejb.ContentEJB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<%! public String diretorName, categoriaName; %>
<head>
	
<title>listContents.jsp</title>
<style>
table {
    border-spacing: 0;
    width: 100%;
    border: 1px solid #ddd;
}

th, td {
    text-align: left;
    padding: 16px;
}

tr:nth-child(even) {
    background-color: #f2f2f2
}
</style>
</head>
<body>
<%
if(request.getSession().getAttribute("loginToken") == null)
	request.getRequestDispatcher("/Login.jsp").forward(request, response);

boolean isAdmin = (boolean)request.getSession().getAttribute("loginIsAdmin");

Object ob = request.getAttribute("action");
String action = ob.toString();
%>


<!--  APRESENTAÇÃO DA TABELA COM TODOS OS CONTEÚDOS -->


<c:if test= "${action == 'allContents'}">
<p>Ordenar por:</p>

<form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="ascAll" id ="asc"  value="Ordem ascendente">
 </form>
 
 <form action="PlayersTallerThan" method="get"> 
    <input type="submit" name="descAll" id ="desc"  value="Ordem descendente">
 </form>

    <table id="myTable">
  <tr>
    <th>Title</th>
    <th>Category</th>
    <th>Director</th>
    <th>Year</th>
  </tr>
  
  
<% 
ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");
ArrayList<String> diretores = (ArrayList<String>) request.getAttribute("diretores");
ArrayList<String> categorias = (ArrayList<String>) request.getAttribute("categorias");
if(list.size()==0){
	out.println("Não existem dados!");
}
for(ContentDTO content : list) {
    %>

  <tr>
    <td><% out.println(content.getTitle()); %></td>
    <td><% out.println(content.getCategory()); %></td>
   	<td><% out.println(content.Director()); %></td>
    <td><% out.println(content.getYear()); %></td>
  </tr>
    <% 
}
%>
</table>
<!--  PARTE DOS FILTROS -->
<!--  SELECT BOX PARA APARECEREM TODOS OS DIRETORES -->
<form action="PlayersTallerThan" method="get"> 
<select id="id_of_director">
<option><%out.println("-"); %></option>
  <%
	for(String diretor : diretores){
		%><option value="diretor">
  		<%out.println(diretor);
  		diretorName = diretor;%></option>
	<% } %>
</select>

<!--  SELECT BOX PARA APARECEREM TODAS AS CATEGORIAS -->
<select id="id_of_category">
<option><%out.println("-"); %> </option>
  <%
	for(String categoria : categorias){
		%><option value="categoria">
  		<%out.println(categoria);%></option>
	<% } %>
</select>
<button type="submit" name="filtrar" id="filtrar" onclick="myFunction()">Filtrar</button>
<input type="hidden" name="directorName" id="directorName" value="" />
<input type="hidden" name="categoryName" id="categoryName" value="" />
</form>
<script>
function myFunction() {
    var x = document.getElementById("id_of_category").value;
    var y =document.getElementById("id_of_director").value;
}
document.getElementById('directorName').value = x;
document.getElementById('categoryName').value = y;
</script>


</c:if>



 


<!--  EDITAR CONTEUDO [MANAGER] -->

<c:if test= "${action == 'edit'}">
  
   <%  
   ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");
   for(ContentDTO content : list) {
	    String title = content.getTitle();
	    out.println(title);
	   String director = content.Director();
	    int year = content.getYear();
	    String category=content.getCategory();
	    int id = content.getID();
	    %>
	    <br>
	     <form action="PlayersTallerThan" method="get"> 
	     	<input type="hidden" name="content_title" value="<%=title%>">    
	     	<input type="hidden" name="content_director" value="<%=director%>">     
	    	<input type="hidden" name="content_year" value="<%=year%>">    
	       	<input type="hidden" name="content_category" value="<%=category%>"> 
	       	<input type="hidden" name="content_id" value="<%=id%>"> 
	    	<input type="submit" name="buttonEdit" id ="buttonEdit" value="Edit">
	 	</form>
	    <% 
   }
	%>
   
</c:if>



<!-- /////  -->




<c:if test= "${action == 'details'}">
    <% 
    out.println("DETAILS");
    %>
    <br>
    <% 
    out.println("Director:");
    String director = request.getParameter("content_director");
	out.println(director);
	%>
	<br>
	<% 
	out.println("Category:");
	String category = request.getParameter("content_category");
	out.println(category);
	%>
	<br>
	<%
	out.println("Year:");
	int year = Integer.parseInt(request.getParameter("content_year"));
	out.println(year);
	%>
</c:if>

<!--  LISTAR A WATCH LIST DO USER -->
<c:if test= "${action == 'watchlist'}">
    <% 
    out.println("Watch List:");

 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("watchList");
	for(ContentDTO content : list) {
    	out.println(content.toString());
    	%>
    	<form action="PlayersTallerThan" method="get"> 
    	<input type="submit" name="removeFromWL" id ="removeFromWL"  value="Remove">
 		<input type="hidden" name="content_id" value="<%=content.getID()%>"> 
 		</form>
 		<% 
}
%>
</c:if>





</body>
</html>