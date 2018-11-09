<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
<title>Multimedia</title>
<script>
function showImage(){
    document.getElementById('loadingImage').style.visibility=visible;
}

function myVideo() {
	var popup = document.getElementById("myPopup");
	popup.classList.toggle("show");

	if (popup.paused){ 
	    popup.play(); 
	    }
	  else{ 
	    popup.pause();
	    }
}

</script>
</head>
<%
String pathImage = request.getParameter("pathImage");
String pathMovie = request.getParameter("pathMovie");
String titulo = request.getParameter("titulo");

 %>
<c:set var="testImage" value="${pathImage}" />
<c:set var="testVideo" value="${pathMovie}" />

<body >
<form action="PlayersTallerThan" method="get">	
	<input type="submit" class="botoes" name="backUser" id="backUser" value="Back">
</form>
<form action="PlayersTallerThan" method="post"> 
	<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
</form>

<p class="Title"><% out.print(titulo); %></p>
<img class="imagem" id="loadingImage" src="${testImage}" style="visibility:visible"/>

<div class="popup" onclick="myVideo()" >
<button class="botoes">Watch video</button>
<video class="popuptext" id="myPopup" style="width:800px;" >
<source src="${testVideo}" type="video/mp4">
</video>
</div>

</body>
</html>