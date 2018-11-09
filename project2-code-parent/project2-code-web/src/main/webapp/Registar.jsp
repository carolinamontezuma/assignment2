<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" href="index.css">

</head>
<body>

<!--  ___________________________________ LOGO ________________________________________ -->
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
        position:absolute;
 }
    
    .WEB{ fill:white;
   }
    
    .FLIX{ fill: white;
}
    
        .recta{fill: white;
    width: 61%;

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
<!--  ___________________________________ LOGO ________________________________________ -->

<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

String usedName = (String)request.getAttribute("usedName");
usedName = usedName == null ? "" : usedName;
String usedEmail = (String)request.getAttribute("usedEmail");
usedEmail = usedEmail == null ? "" : usedEmail;
String usedPassword = (String)request.getAttribute("usedPassword");
usedPassword = usedPassword == null ? "" : usedPassword;
String usedCard1 = (String)request.getAttribute("usedCard1");
usedCard1 = usedCard1 == null ? "" : usedCard1;
String usedCard2 = (String)request.getAttribute("usedCard2");
usedCard2 = usedCard2 == null ? "" : usedCard2;
String usedCard3 = (String)request.getAttribute("usedCard3");
usedCard3 = usedCard3 == null ? "" : usedCard3;
String usedCard4 = (String)request.getAttribute("usedCard4");
usedCard4 = usedCard4 == null ? "" : usedCard4;
String message = (String)request.getAttribute("message");
%>
<div class="wrap_registar aux2">
<p class="Title">Register</p>

<div class="Text">
	<form action="PlayersTallerThan" method="post">
  		<b>Name </b> <input class="inputstyle"  type="text" name="fname" placeholder="Name" value="<%= usedName %>" required><br>
  		<b>Email</b> <input class="inputstyle" type="email" name="fmail" placeholder="example@email.com" value="<%= usedEmail %>" required><br>
  		<b>Password</b> <input class="inputstyle" type="password" name="fpass" placeholder="Password" value="<%= usedName %>" required><br>
  		<b>Credit card</b>
  		<input class="inputstyle" type="number" id="id_fcard1" name="fcard1" oninput="limitLength('id_fcard1', 4, 'id_fcard2')" value="<%= usedCard1 %>" required>
  		<input class="inputstyle" type="number" id="id_fcard2" name="fcard2" oninput="limitLength('id_fcard2', 4, 'id_fcard3')" value="<%= usedCard2 %>" required>
  		<input class="inputstyle" type="number" id="id_fcard3" name="fcard3" oninput="limitLength('id_fcard3', 4, 'id_fcard4')" value="<%= usedCard3 %>" required>
  		<input class="inputstyle" type="number" id="id_fcard4" name="fcard4" oninput="limitLength('id_fcard4', 4, null)" value="<%= usedCard4 %>" required>
  		<br>
  		<% if(message!=null){ %>
  		<p class="Text"><%out.print(message); %></p>
  		<%} %>
  		<input class="botoes" type="submit" name="registar" value="Create account">
	</form>
</div>
</div>
	<script type="text/javascript">
	function limitLength(elementID, maxLength, nextElementID)
	{
	    var elem = document.getElementById(elementID);

	    if (elem.value.length > maxLength)
	    	elem.value = elem.value.substring(0, maxLength);

    	if (elem.value.length == maxLength && nextElementID != null)
    		document.getElementById(nextElementID).focus();
	}
	</script>
</body>
</html>