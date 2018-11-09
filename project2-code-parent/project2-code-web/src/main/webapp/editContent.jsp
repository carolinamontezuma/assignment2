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

<!-- ________________________________________________LOGO_____________________________________________ -->
  <?xml version="1.0" encoding="utf-8"?>
<!-- Generator: Adobe Illustrator 20.1.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 1527.2 1104.3" style="enable-background:new 0 0 1527.2 1104.3;" xml:space="preserve">
<style type="text/css">
	.st0{fill:none;}
	.st1{fill:#FFFFFF;}
	.st2{font-family:'Helvetica'; font-weight: Bold;}
	.st3{font-size:325.0382px;}
    
    #Layer_1{width: 15%;
        margin-left: 40%;
        animation-name: reduz;
    animation-duration: 6s;
    animation-timing-function: ease-in-out;}
    
    .WEB{ fill:white;
        animation-name: oi;
    animation-duration: 4s;
    animation-timing-function: ease-in-out;}
    
    .FLIX{ fill: white;
        animation-name: oi;
    animation-duration: 4s;
    animation-timing-function: ease-in-out;}
    
        .recta{fill: white;
    width: 61%;
        -webkit-animation-name: barra_anda; /* Safari 4.0 - 8.0 */
    -webkit-animation-duration: 2s; /* Safari 4.0 - 8.0 */
    animation-name: barra_anda;
    animation-duration: 2s;
    animation-timing-function: ease-in-out;
    }
    
    
    .out_intro{
    
    animation-name: oi;
    animation-duration: 10s;
    animation-timing-function: ease-in-out;
    }
 
</style>
<rect x="34.3" y="278.1" class="st0" width="1458.5" height="306.5"/>
<text transform="matrix(1 0 0 1 34.3447 517.3128)" class="st1 st2 st3 WEB">WEB</text>
<rect x="763.6" y="584.6" class="st0" width="729.3" height="306.5"/>
<text transform="matrix(1 0 0 1 763.6103 823.8597)" class="st1 st2 st3 FLIX">FLIX</text>
<g>
	<rect x="538.1" y="544.7" class="st1 recta" width="859.3" height="27.3"/>
	<g>
	
	</g>
</g>
</svg>
<!-- ________________________________________________LOGO_____________________________________________ -->

<form action="PlayersTallerThan" method="get">	
	<input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
	<input class="botoes logout" type="submit" name="logout" id="logout" value="Logout">
</form>	
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


</body>
</html>