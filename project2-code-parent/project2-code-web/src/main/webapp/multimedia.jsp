<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
<title>multimedia.jsp</title>
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
String pathMovie = (String)request.getAttribute("pathMovie");
String pathImage = (String)request.getAttribute("pathImage");
 %>

<body >
<p class="Title"> Content Details</p>
<img class="imagem" id="loadingImage" src=pathImage style="visibility:visible"/>

<div class="popup" onclick="myVideo()" >
<button class="botoes">Watch video</button>
<video class="popuptext" id="myPopup" style="width:800px;" >
<source src=pathMovie type="video/mp4">
</video>
</div>
<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes" type="submit" name="logout" id="logout" value="Logout">
 	</form>

</body>
</html>