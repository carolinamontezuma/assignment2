<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Management</title>
<link rel="stylesheet" href="index.css">
</head>
<body>

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

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);
String mensagem = (String)request.getAttribute("message");
%>

	<p>Welcome, <% out.println(request.getSession().getAttribute("loginName")); %>!</p>

 	<form action="PlayersTallerThan" method="get"> 
   		<input class="botoes manbut" type="submit" name="newContent" id ="newContent" value="Add new content">
 	</form>
 	<form action="PlayersTallerThan" method="get"> 
    	<input class="botoes manbut" type="submit" name="editContent" id ="editContent" value="Edit content">
 	</form>
 	
 	<form action="PlayersTallerThan" method="get"> 
    	<input class="botoes manbut" type="submit" name="deleteContent" id ="deleteContent" value="Delete content">
 	</form>
 	
 	<form action="PlayersTallerThan" method="post"> 
   		<input class="botoes logout manlogaux" type="submit" name="logout" id="logout" value="Logout">
 	</form>

</body>
</html>