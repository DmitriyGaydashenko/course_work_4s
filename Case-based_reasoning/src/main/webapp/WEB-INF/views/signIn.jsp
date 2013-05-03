<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
<script src="./resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
function init() {
	initEvents();
}
var Errors = null;
var Form = null;
function getErrorHandler() {
	return $("#message");
}
function initEvents() {
	if(Form == null)
	Form = {login : document.getElementById("login"),
			email : document.getElementById("email"),
			password : document.getElementById("password")};
	Errors = {"NOT_EXISTS" : "Authentication failed. Please try again.",
			"NOT_VERIFIED" : "Your type was not confirmed."+ 
			"Please, contact administrator."};
}
function signIn() {
		$.ajax({
			url : "./signIn",
			type : "POST",
			data: ({email : Form.email.value, password : Form.password.value}),
			success: function(userState){
				if (userState == "redirect:Home")
					document.location.href = "./precedents";
				getErrorHandler().html(Errors[userState]);
			}
		});
	return false;
}
</script>
<title>CBR</title>
<meta charset="utf-8">
<link rel="stylesheet" href="./resources/styles/layout.css" type="text/css">

</head>
<body onload="init()">
<div class="wrapper head">
  <header id="header" class="clear">
    <hgroup>
      <h1>CBR</h1>
      <h2>Case-based reasoning</h2>
    </hgroup>
    <div id="add_info">
      <fieldset><button style="width:100px;" onclick="location.href='./signUp'" id="signUp">Sign up</button>        
      </fieldset>
    </div>
    
  </header>
</div>
<!-- content -->
<div class="wrapper content">
  <div id="container" class="clear">
    <!-- content body -->
    <div style="position: absolute; top: 20%; left: 40%; width: 500px; height: 400px;">
	    <form action="" onSubmit="return signIn();" id="logninForm">
	    	<table>
				<tr><td id="name_email">E-mail</td><td><input type="email" value="" id="email" maxlength="20" required></td></tr>
				<tr><td id="name_password">Password</td><td><input type="password" value="" id="password"
				 	maxlength="20" required></td></tr>
				<tr><td></td><td><input type="submit" style="width:100px;" id="login" class="button" value="Sign in"/></td></tr>
				<tr><td style="color:#FF0000;" colspan="2" id="message">${message}</td></tr>
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
