<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List"%>
<%@ page import = "dto.ContentDTO"%>
<%@ page import = "java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userScreen.jsp</title>
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
    setTimeout(showSlides, 2000); // mudar imagem de 2 em 2 segs
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
		<%
		List<ContentDTO> suggestedContent = (List<ContentDTO>)request.getAttribute("suggestedContent");
		List<String> nomes = new ArrayList<String>();
		for(ContentDTO c : suggestedContent){
			nomes.add(c.getMultimedia());
		}
	%>
	
	<div class="slideshow-container">
  	<!-- Full-width images with number and caption text -->
  	<div class="mySlides fade">
    	<div class="numbertext">1 / 3</div>
    	<img src="breakingbad-john.png" style="width:50%">
    	<div class="text">Caption Text</div>
  	</div>
  	<div class="mySlides fade">
    	<div class="numbertext">2 / 3</div>
    	<img src="breakingbad-john.png" style="width:50%">
    	<div class="text">Caption Two</div>
  	</div>
  	<div class="mySlides fade">
    	<div class="numbertext">3 / 3</div>
    	<img src="breakingbad-john.png" style="width:50%">
    	<div class="text">Caption Three</div>
  	</div>
  	<div class="mySlides fade">
    	<div class="numbertext">3 / 3</div>
    	<img src="breakingbad-john.png" style="width:50%">
    	<div class="text">Caption Three</div>
  	</div>
  	<div class="mySlides fade">
    	<div class="numbertext">3 / 3</div>
    	<img src="breakingbad-john.png" style="width:50%">
    	<div class="text">Caption Three</div>
  	</div>

  <!-- Next and previous buttons -->
  <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
  <a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>
<br>

<!-- The dots/circles -->
<div style="text-align:center">
  <span class="dot" onclick="currentSlide(1)"></span> 
  <span class="dot" onclick="currentSlide(2)"></span> 
  <span class="dot" onclick="currentSlide(3)"></span>
  <span class="dot" onclick="currentSlide(4)"></span> 
  <span class="dot" onclick="currentSlide(5)"></span>  
</div>

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
   		<input class="b_userscreen" type="submit" name="logout" id="logout" value="Logout">
 	</form>
 </div>	

</body>
</html>