<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "java.util.List"%>
<%@ page import = "dto.ContentDTO"%>
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
ContentDTO dto = (ContentDTO) request.getAttribute("contentDTO");
   
%>
<div class="wrap_registar aux2">
<p class="Title"><% out.print(dto.getTitle()); %></p>

<form action="PlayersTallerThan" method="post">
	<b>Title</b> <input class="inputstyle_aux" type="text" id="id_title" name="title" placeholder="Title" value="<%= dto.getTitle() %>" disabled>
	<input type="button" id="id_bttitle" name="editTitle" onclick="editElement('id_title', 'id_bttitle')" value="Edit" formnovalidate>
	<input type="hidden" name="content_id" value="<%=dto.getID()%>"> 
</form>

<form action="PlayersTallerThan" method="post">
	<b>Director</b> <input class="inputstyle_aux" type="text" id="id_director" name="director" placeholder="Director" value="<%= dto.getDirector() %>" disabled>
	<input type="button" id="id_btdirector" name="editDirector" onclick="editElement('id_director', 'id_btdirector')" value="Edit" formnovalidate>
	<input type="hidden" name="content_id" value="<%=dto.getID()%>"> 
</form>

<form action="PlayersTallerThan" method="post">
  		<b>Category</b> <select  name="category" id="id_category" disabled> 
			  <%for(String cat : categories){
					%><option value="<%= cat %>" <% if(cat.equals(dto.getCategory())) { %> selected <% } %>><%out.println(cat);%></option>
				<% } %>
		</select>
       	<input type="hidden" name="content_id" value="<%=dto.getID()%>">
        <input type="button" id="id_btcategory" name="editCategory" onclick="editElement('id_category', 'id_btcategory')" value="Edit" formnovalidate>	
  	</form>

<form action="PlayersTallerThan" method="post">
	<b>Year</b> <input class="inputstyle_aux" type="number" id="id_year" name="year" placeholder="Year" value="<%= dto.getYear() %>" disabled>
	<input type="button" id="id_btyear" name="editYear" onclick="editElement('id_year', 'id_btyear')" value="Edit" formnovalidate>
	<input type="hidden" name="content_id" value="<%=dto.getID()%>"> 
</form>
</div>	

<script type="text/javascript">
	function editElement(elementID, btID)
	{
	    var elem = document.getElementById(elementID);
	    var bt = document.getElementById(btID);
	    if (elem.hasAttribute("disabled"))
	    {
	    	elem.removeAttribute("disabled");
	        bt.value = 'Save';
	        elem.style.color="#ffffff";
	    }
	    else
	    {
	    	bt.value = 'Edit';
	    	bt.type = 'submit';
	    }
	}
</script>


<form action="PlayersTallerThan" method="get">	
 		 <input type="submit" class="botoes" name="backEditContent" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
</form>

</body>
</html>