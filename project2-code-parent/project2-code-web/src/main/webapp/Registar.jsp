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
%>

<p class="Text">Registerr</p>
<div class="Text">
	<form action="PlayersTallerThan" method="post">
  		Name: <input type="text" name="fname" placeholder="Name" value="<%= usedName %>" required><br>
  		Email: <input type="email" name="fmail" placeholder="example@email.com" value="<%= usedEmail %>" required><br>
  		Password: <input type="password" name="fpass" placeholder="Password" value="<%= usedName %>" required><br>
  		Credit card:
  		<input type="number" id="id_fcard1" name="fcard1" oninput="limitLength('id_fcard1', 4, 'id_fcard2')" value="<%= usedCard1 %>" required>
  		<input type="number" id="id_fcard2" name="fcard2" oninput="limitLength('id_fcard2', 4, 'id_fcard3')" value="<%= usedCard2 %>" required>
  		<input type="number" id="id_fcard3" name="fcard3" oninput="limitLength('id_fcard3', 4, 'id_fcard4')" value="<%= usedCard3 %>" required>
  		<input type="number" id="id_fcard4" name="fcard4" oninput="limitLength('id_fcard4', 4, null)" value="<%= usedCard4 %>" required>
  		<br>
  		<input class="botoes" type="submit" name="registar" value="Create account">
	</form>
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