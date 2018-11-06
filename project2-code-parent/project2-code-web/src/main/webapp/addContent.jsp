<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<form action="PlayersTallerThan" method="get">
  		Tile: <input type="text" name="ftitle" placeholder="Title" required><br>
  		Director: <input type="text" name="fdirector" placeholder="Director" required><br>
  		Category: 
		<select name="fcategory" required>
  			<option value="Action">Action</option>
  			<option value="Comedy">Comedy</option>
  			<option value="Crime">Crime</option>
  			<option value="Drama">Drama</option>
  			<option value="Fantasy">Fantasy</option>
  			<option value="Historical">Historical</option>
  			<option value="Science fiction">Science fiction</option>
  			<option value="Thriller">Thriller</option>
		</select>  		
		<br>
		  Year: <input type="text" name="fyear" placeholder="Year" required><br>
		 <input type="submit" name="addContent" id ="addContent" value="Confirm">
		
	</form>
</body>
</html>