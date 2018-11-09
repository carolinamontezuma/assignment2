<%@page import="ejb.ContentEJB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "ejb.ContentEJB"%>
<%@ page import = "dto.ContentDTO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<%! public String diretorName, categoriaName; %>
<html>
<head>
<title>Contents</title>
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
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

boolean isAdmin = (boolean)request.getSession().getAttribute("loginIsAdmin");

Object ob = request.getAttribute("action");
String action = ob.toString();
%>


<!--  APRESENTAÇÃO DA TABELA COM TODOS OS CONTEÚDOS -->

<div class="Text">
<c:if test= "${action == 'allContents'}">
<% 
ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");
ArrayList<String> diretores = (ArrayList<String>) request.getAttribute("diretores");
ArrayList<String> categorias = (ArrayList<String>) request.getAttribute("categorias");
ArrayList<ContentDTO> wl = (ArrayList<ContentDTO>) request.getAttribute("wl");

String lastDirectorName = (String)request.getAttribute("lastDirectorName");
String lastCategoryName = (String)request.getAttribute("lastCategoryName");
String lastMinYear = (String)request.getAttribute("lastMinYear");
lastMinYear = lastMinYear == null? "" : lastMinYear;
String lastMaxYear = (String)request.getAttribute("lastMaxYear");
lastMaxYear = lastMaxYear == null? "" : lastMaxYear;

if(list.size()==0){
	out.println("No contents were found!");
	%>
<% 
}
else{
%>

<script type="text/javascript">
	function setParams(elementIDs)
	{
		document.getElementById(elementIDs[0]).value = document.getElementById('id_of_director').value;
		document.getElementById(elementIDs[1]).value = document.getElementById('id_of_category').value;
		document.getElementById(elementIDs[2]).value = document.getElementById('id_min_year').value;
		document.getElementById(elementIDs[3]).value = document.getElementById('id_max_year').value;
	}
	</script>

<table class="Text" id="myTable">
  <tr>
    <th id="tituloTabela">>Title
    <form action="PlayersTallerThan" method="get" onsubmit="setParams(['t_dir', 't_cat', 't_ymin', 't_ymax'])">
		<input type="submit" class="botoesOrdem" name="OrderTitleAsc" id ="OrderTitleAsc" value="Asc">
		<input type="submit" class="botoesOrdem" name="OrderTitleDesc" id ="OrderTitleDesc" value="Desc">
		<input type="hidden" id="t_dir" name="directorName">
		<input type="hidden" id="t_cat" name="categoryName">
		<input type="hidden" id="t_ymin" name="minYear">
		<input type="hidden" id="t_ymax" name="maxYear">
		<input type="hidden" name="action" value="<%= action %>">
	</form></th>
    <th id="tituloTabela">Category
	<form action="PlayersTallerThan" method="get" onsubmit="setParams(['c_dir', 'c_cat', 'c_ymin', 'c_ymax'])">
		<input type="submit" class="botoesOrdem" class="botoes"name="OrderCategoryAsc" id ="OrderCategoryAsc" value="Asc">
		<input type="submit" class="botoesOrdem" name="OrderCategoryDesc" id ="OrderCategoryDesc" value="Desc">
		<input type="hidden" id="c_dir" name="directorName">
		<input type="hidden" id="c_cat" name="categoryName">
		<input type="hidden" id="c_ymin" name="minYear">
		<input type="hidden" id="c_ymax" name="maxYear">
		<input type="hidden" name="action" value="<%= action %>">
	</form></th>
   <th id="tituloTabela">Director
   <form action="PlayersTallerThan" method="get" onsubmit="setParams(['d_dir', 'd_cat', 'd_ymin', 'd_ymax'])">
		<input type="submit" class="botoesOrdem" name="OrderDirectorAsc" id ="OrderDirectorAsc" value="Asc">
		<input type="submit" class="botoesOrdem" name="OrderDirectorDesc" id ="OrderDirectorDesc" value="Desc">
		<input type="hidden" id="d_dir" name="directorName">
		<input type="hidden" id="d_cat" name="categoryName">
		<input type="hidden" id="d_ymin" name="minYear">
		<input type="hidden" id="d_ymax" name="maxYear">
		<input type="hidden" name="action" value="<%= action %>">
	</form></th>
    <th id="tituloTabela">Year
    <form action="PlayersTallerThan" method="get" onsubmit="setParams(['y_dir', 'y_cat', 'y_ymin', 'y_ymax'])">
    	<input type="submit" class="botoesOrdem" name="OrderYearAsc" id ="OrderYearAsc" value="Asc">
    	<input type="submit" name="OrderYearDesc" class="botoesOrdem" id ="OrderYearDesc" value="Desc">
    	<input type="hidden" id="y_dir" name="directorName">
		<input type="hidden" id="y_cat" name="categoryName">
		<input type="hidden" id="y_ymin" name="minYear">
		<input type="hidden" id="y_ymax" name="maxYear">
		<input type="hidden" name="action" value="<%= action %>">
   	</form></th>
  </tr>
  
  
<%
for(ContentDTO content : list) {
	String mult = content.getMultimedia();
    %>

  <tr>
    <td>
    <form action="PlayersTallerThan" method="get">
    <input type="hidden" name="multimedia" value="<%=mult%>"> 
    <input type="submit" class="botaoTitulo" name="botaoTitulo" value="<% out.println(content.getTitle()); %>">
    </form>
    </td>
    <td><% out.println(content.getCategory()); %></td>
   	<td><% out.println(content.getDirector()); %></td>
    <td><% out.println(content.getYear()); 
    	if(!wl.contains(content)){ 
    	%>
    	<form action="PlayersTallerThan" method="get">
    		<input type="hidden" name="content_id" value="<%=content.getID()%>">
    		<input type="submit" class="botoes" name="addtowl" id ="addtowl" value="Add to WL">
    		<input type="hidden" name="action" value="<%= action %>">
    	</form> 
    	<%} else{ %>
    	<form action="PlayersTallerThan" method="get">
    		<input type="hidden" name="content_id" value="<%=content.getID()%>">
    		<input type="submit" class="botoes" name="removeFromWL" id ="removeFromWL" value="Remove from WL">
    		<input type="hidden" name="action" value="<%= action %>">
    	</form>
    	<% } %> </td>
    	
  </tr>
  
<%
}
%>
</table>

<!--  PARTE DOS FILTROS -->
<!--  SELECT BOX PARA APARECEREM TODOS OS DIRETORES -->
<div class="filtros">
<form action="PlayersTallerThan" method="get"> 
<select class="select-box" class="Text" id="id_of_director" name="directorName">
<option><%out.println("-"); %></option>
  <%
	for(String diretor : diretores){
		%><option value="<%= diretor %>" <% if(lastDirectorName != null && lastDirectorName.equals(diretor)) {%> selected <%} %>>
  		<%out.println(diretor);
  		diretorName = diretor;%></option>
	<% } %>
</select>

<!--  SELECT BOX PARA APARECEREM TODAS AS CATEGORIAS -->
<select class="select-box" class="Text" id="id_of_category" name="categoryName">
<option><%out.println("-"); %> </option>
  <%
	for(String categoria : categorias){
		%><option value="<%= categoria %>" <% if(lastCategoryName != null && lastCategoryName.equals(categoria)) {%> selected <%} %>>
  		<%out.println(categoria);%></option>
	<% } %>
</select>
<!--  TEXT BOX PARA ESCOLHER O RANGE DE YEARS -->

<input class="Text" type="number" placeholder="Minimum year" id="id_min_year" name="minYear" value="<%= lastMinYear %>">
<input class="Text" type="number" placeholder="Maximum year" id="id_max_year" name="maxYear" value="<%= lastMaxYear %>">
<input type="hidden" name="action" value="<%= action %>">
<button class="botoes" type="submit" name="filtrar" id="filtrar">Filter</button>
 

</form>
<%} %>
</div>
 </c:if>



<!--  EDITAR CONTEUDO [MANAGER] -->
<c:if test= "${action == 'edit'}">
  
   <%  
   ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("allContents");
   for(ContentDTO content : list) {
	    String title = content.getTitle();
	    out.println(title);
	   String director = content.getDirector();
	    int year = content.getYear();
	    String category=content.getCategory();
	    int id = content.getID();
	    %>
	    <br>
	     <form action="PlayersTallerThan" method="get"> 
	       	<input type="hidden" name="content_id" value="<%=id%>"> 
	    	<input type="submit" name="buttonEdit" id ="buttonEdit" value="Edit">
	 	</form>
	    <% 
   }
	%>
   
</c:if>

<!--  LISTAR A WATCH LIST DO USER -->
<c:if test= "${action == 'watchlist'}">
    <% 
 	ArrayList<ContentDTO> list = (ArrayList<ContentDTO>) request.getAttribute("wl"); 
    if(list.size()==0){
 		out.println("You have no contents in your watchlist!");%>
 		<% 
 	}
    else{ 
 	%>
 	
 	 <table class="Text" id="myTable">
 	  <tr>
	    <th id="tituloTabela">Title
	    <form action="PlayersTallerThan" method="get">
			<input type="submit" class="botoesOrdem" name="OrderTitleAsc" id ="OrderTitleAsc" value="Asc">
			<input type="submit" class="botoesOrdem" name="OrderTitleDesc" id ="OrderTitleDesc" value="Desc">
			<input type="hidden" name="action" value="<%= action %>">
		</form></th>
	    <th id="tituloTabela">Category
		<form action="PlayersTallerThan" method="get">
			<input type="submit" class="botoesOrdem" class="botoes"name="OrderCategoryAsc" id ="OrderCategoryAsc" value="Asc">
			<input type="submit" class="botoesOrdem" name="OrderCategoryDesc" id ="OrderCategoryDesc" value="Desc">
			<input type="hidden" name="action" value="<%= action %>">
		</form></th>
	   <th id="tituloTabela">Director
	   <form action="PlayersTallerThan" method="get">
			<input type="submit" class="botoesOrdem" name="OrderDirectorAsc" id ="OrderDirectorAsc" value="Asc">
			<input type="submit" class="botoesOrdem" name="OrderDirectorDesc" id ="OrderDirectorDesc" value="Desc">
			<input type="hidden" name="action" value="<%= action %>">
		</form></th>
	    <th id="tituloTabela">Year
	    <form action="PlayersTallerThan" method="get">
	    	<input type="submit" class="botoesOrdem" name="OrderYearAsc" id ="OrderYearAsc" value="Asc">
	    	<input type="submit" name="OrderYearDesc" class="botoesOrdem" id ="OrderYearDesc" value="Desc">
	    	<input type="hidden" name="action" value="<%= action %>">
	   	</form></th>
	  </tr>
	<%
 	for(ContentDTO content : list) {
 	    %>
 	  <tr>
 	    <td><% out.println(content.getTitle()); %></td>
 	    <td><% out.println(content.getCategory()); %></td>
 	   	<td><% out.println(content.getDirector()); %></td>
 	    <td><% out.println(content.getYear()); 
 	    	%>
 	    	<form action="PlayersTallerThan" method="get">
 	    		<input class="botoes" type="hidden" name="content_id" value="<%=content.getID()%>">
 	    		<input class="botoes bot_aux removeFromWL_aux" type="submit" name="removeFromWL" id ="removeFromWL" value="Remove from WL">
 	    		<input type="hidden" name="action" value="<%= action %>">
 	    	</form>
 	    	<% } %> </td>
 	    	
 	  </tr>
 	  </table>
 	  <% } %>
</c:if>
</div>
<form action="PlayersTallerThan" method="get">	
 		 <input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
	<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
 	</form>
</body>
</html>