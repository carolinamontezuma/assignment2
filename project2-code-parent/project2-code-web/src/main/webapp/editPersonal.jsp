<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "dto.UserDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="index.css">
<title>Edit account</title>
</head>
<body>
<%
if(request.getAttribute("source")  == null)
	request.getRequestDispatcher("/Dummy.jsp").forward(request, response);

UserDTO dto = (UserDTO) request.getAttribute("userDTO");
String card1 = "" + dto.getCreditCard().subSequence(0, 4);
String card2 = "" + dto.getCreditCard().subSequence(4, 8);
String card3 = "" + dto.getCreditCard().subSequence(8, 12);
String card4 = "" + dto.getCreditCard().subSequence(12, 16);
%>

	<p>Edit account</p>
	
	<form action="PlayersTallerThan" method="post">
  		Name: <input type="text" id="id_fname" name="fname" placeholder="Name" value="<%= dto.getUsername() %>" disabled>
  		<input type="button" id="id_btfname" name="editName" onclick="editElement('id_fname', 'id_btfname')" value="Edit" formnovalidate>
	</form>
	
  	<form action="PlayersTallerThan" method="post">
  		Email: <input type="email" id="id_fmail" name="fmail" placeholder="example@email.com" value="<%= dto.getEmail() %>" disabled>
  		<input type="button" id="id_btfmail" name="editMail" onclick="editElement('id_fmail', 'id_btfmail')" value="Edit" formnovalidate>
  	</form>
  	
  	<form action="PlayersTallerThan" method="post">
  		Password: <input type="password" id="id_fpass" name="fpass" placeholder="Password" value="<%= dto.getPassword() %>" disabled>
  		<input type="button" id="id_btfpass" name="editPass" onclick="editElement('id_fpass', 'id_btfpass')" value="Edit" formnovalidate>
  	</form>
  	
  	<form action="PlayersTallerThan" method="post">
  		Credit card:
  		<input type="number" id="id_fcard1" name="fcard1" oninput="limitLength('id_fcard1', 4, 'id_fcard2')" value="<%= card1 %>" disabled>
  		<input type="number" id="id_fcard2" name="fcard2" oninput="limitLength('id_fcard2', 4, 'id_fcard3')" value="<%= card2 %>" disabled>
  		<input type="number" id="id_fcard3" name="fcard3" oninput="limitLength('id_fcard3', 4, 'id_fcard4')" value="" disabled>
  		<input type="number" id="id_fcard4" name="fcard4" oninput="limitLength('id_fcard4', 4, null)" value="" disabled>
  		<input type="button" id="id_btcredit" name="editCredit" onclick="editCreditCard('id_btcredit')" value="Edit" formnovalidate>
  	</form>
  	
  		<br>
	<form action="PlayersTallerThan" method="post">  		
  		<input type="submit" name="deleteAccount" value="Delete account" formnovalidate>
 	</form>
	
	<script type="text/javascript">
	function editElement(elementID, btID)
	{
	    var elem = document.getElementById(elementID);
	    var bt = document.getElementById(btID);
	    if (elem.hasAttribute("disabled"))
	    {
	    	elem.removeAttribute("disabled");
	        bt.value = 'Save';
	    }
	    else
	    {
	    	bt.value = 'Edit';
	    	bt.type = 'submit';
	    }
	}

	function limitLength(elementID, maxLength, nextElementID)
	{
	    var elem = document.getElementById(elementID);

	    if (elem.value.length > maxLength)
	    	elem.value = elem.value.substring(0, maxLength);

    	if (elem.value.length == maxLength && nextElementID != null)
    		document.getElementById(nextElementID).focus();
	}

	function editCreditCard(btID)
	{
		var elem1 = document.getElementById('id_fcard1');
		var elem2 = document.getElementById('id_fcard2');
		var elem3 = document.getElementById('id_fcard3');
		var elem4 = document.getElementById('id_fcard4');
	    var bt = document.getElementById(btID);
	    if (elem1.hasAttribute("disabled"))
		{
	    	elem1.removeAttribute("disabled");
	    	elem2.removeAttribute("disabled");
	    	elem3.removeAttribute("disabled");
	    	elem4.removeAttribute("disabled");
	        bt.value = 'Save';
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