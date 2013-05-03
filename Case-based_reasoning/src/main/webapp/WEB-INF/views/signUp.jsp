<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<title>CBR</title>
<meta charset="utf-8">
<link rel="stylesheet" href="./resources/styles/layout.css" type="text/css">
<script type="text/javascript">
function init() {
	$("#email").blur(function(){
		if (event.target.checkValidity()) {
        $.ajax({
			url : "./signUp/validEmail",
			type : "POST",
			data: ({email : event.target.value}),
			success: function(isValid){
				if (isValid == "true")
					document.location.href = "./precedents";
				else
					document.getElementById("email").setCustomValidity("User with this email already exists.");
			}
		});
		}
    });
}
</script>
</head>
<body onload="init()">
<div class="wrapper head">
  <header id="header" class="clear">
    <hgroup>
      <h1>CBR</h1>
      <h2>Case-based reasoning</h2>
    </hgroup>
    <div id="add_info">
      <fieldset><button style="width:100px;" onclick="location.href='./signIn'" id="signIn">Sign in</button>        
      </fieldset>
    </div>
    
  </header>
</div>
<!-- content -->
<div class="wrapper content">
  <div id="container" class="clear">
    <!-- content body -->
    <div style="position: absolute; top: 20%; left: 40%; width: 500px; height: 400px;">
<form action="signUp" method="POST">
	<table>
	<tr><td>First name</td><td><input type="text" value="" name="fName" 
		maxlength="20" required></td><td id="f_name_error"></td></tr>
	<tr><td>Last name</td><td><input type="text" value="" name="lName" 
		maxlength="20" required></td><td id="l_name_error"></td></tr>
	<tr><td>E-mail</td><td><input id= "email" type="email" value="" name="email" 
		maxlength="20" required></td><td id="email_error"></td></tr>
	<tr><td>Password</td><td><input type="password" 
		pattern=".{5,}" title="Minmimum 5 letters or numbers." value="" 
		name="password" maxlength="20" required></td>
		<td id="password_error"></td></tr>
	<tr><td>User type</td><td><select name="usertypeId" size="1">
	<c:forEach items="${userTypes}" var="userType">
	<option value=${userType.key}>${userType.value}</option>
	</c:forEach>
	</select></td><td id="userType_error"></td></tr>
	<tr><td></td><td colspan="2" align="center"><input type="submit" id="signUp" class="button" value="Sign up"></td></tr>
	<tr><td colspan="3" id="error">${Error}</td></tr>
	</table>
</form>
</div>
    <!-- / content body -->
  </div>
</div>
<!-- Footer -->
<div class="wrapper foot">
  <footer id="footer" class="clear">
    <p>Copyright &copy; 2013 - Dmitriy Gaydashenko</p>
  </footer>
</div>
</body>
</html>
