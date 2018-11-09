<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
<%@ page import = "dto.ContentDTO"%>
<%@ page import = "java.util.ArrayList"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" href="index.css">
<script type="text/javascript">
var slideIndex = 0;
showSlides();

function showSlides() {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none"; 
    }
    slideIndex++;
    if (slideIndex > slides.length) {slideIndex = 1} 
    slides[slideIndex-1].style.display = "block"; 
    setTimeout(showSlides, 2000); // mudar imagem de 3 em 3 segs
}
</script>
</head>
<body onload="showSlides()">
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
%>
	<p class="Text">Welcome, <% out.println(request.getSession().getAttribute("loginName")); %> !</p>
	
	<p class="Subtitle">Suggested Content</p>
		<div class="slideshow-container">
		<%
		List<ContentDTO> suggestedContent = (List<ContentDTO>)request.getAttribute("suggestedContent");
		List<String> nomes = new ArrayList<String>();
		for(ContentDTO c : suggestedContent){
			nomes.add(c.getMultimedia());
		}
		for(String s: nomes){
			StringBuilder s1= new StringBuilder();
			s1.append(s);
			s1.append(".png");
			String new1= s1.toString();
	%>
	<c:set var="imagem" value="${new1}" />
  	<!-- Full-width images with number and caption text -->
  	<div class="mySlides fade">
    	<img src="${imagem}" style="width:50%">
  	</div>
<% } %>
</div>
<br>


<div id="user_bot_wrap">
	<form action="PlayersTallerThan" method="get" class="form_userscreen"> 
   		<input class="b_userscreen" type="submit" name="listWatchList" id ="listWatchList" value="My watchlist">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get" class="form_userscreen"> 
    	<input class="b_userscreen" type="submit" name="listAll" id ="listAll"  value="List content">    	
 	</form>
 	
 	<form action="PlayersTallerThan" method="get" class="form_userscreen"> 
   		<input class="b_userscreen" type="submit" name="editPersonal" id="editPersonal" value="Edit account">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post" class="form_userscreen"> 
   		<input class="b_userscreen" type="submit" name="logout" id="logout_screen" value="Logout">
 	</form>
 </div>	

</body>
</html>