<!DOCTYPE html>
<html lang="en">
<head>
<title>CBR</title>
<meta charset="utf-8">
<link rel="stylesheet" href="./resources/styles/layout.css" type="text/css">
<script src="./resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
function getUserData() {
	$.getJSON("trial", {}, function(user) {
        document.write(user.email);
    });

}
PermisionEnum = {SET_NOT: -1, SET: 2};
tableHeader = {"precedent" : [{"name" : "Precedent", "type" : "uint", "required" : true}, 
                              {"name" : "Task", "type" : "uint", "required" : true},
                              {"name" : "Device", "type" : "uint", "required" : true},
                              {"name" : "State", "type" : "uint", "required" : true},
                              {"name" : "Need time to solve", "type" : "ufloat", "required" : true},
                              {"name" : "Is solved correctly", "type" : "boolean", "required" : true}]};
tableContent = {"precedent" : [[1, 1, 1, 1, 0.655, true], [2, 1, 2, 1, 0.655, false], [2, 1, 2, 2, 0.655, false],
                [2, 2, 2, 1, 0.655, false]]};
$(document).ready(function() {
	 
	 //When page loads...
	 getUserData();
	 $(".tab_content").hide(); //Hide all content
	 active = $("ul.tabs li:first"); //Activate first tab
	 active.addClass("active").show();
	 $("#create_new").data("current-table", $(active).find("a").attr("href"));
	 $(".tab_content:first").show(); //Show first tab content
	 
	 //On Click Event
	 $("ul.tabs li").click(function() {
	 
	  $("ul.tabs li").removeClass("active"); //Remove any "active" class
	  $(this).addClass("active"); //Add "active" class to selected tab
	  $(".tab_content").hide(); //Hide all tab content
	 
	  var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
	  $(activeTab).fadeIn(); //Fade in the active ID content
	  $("#create_new").data("current-table", activeTab);
	  return false;
	 });
	 initTable("precedent");
	 
	// Here we will write a function when link click under class popup	
	$('button.popup').click(initEmptyForm);
	// Now define one more function which is used to fadeout the 
	// fade layer and popup window as soon as we click on fade layer
	$('#fade').click(close);
	$('#cancel').click(close);
});
function close(){
	// Add markup ids of all custom popup box here 						  
	$('#fade , #popuprel').fadeOut();
	return false;
}
function input(fieldData) {
	this.ToString = function() {
		return "";
	}
}
function createInput(fieldData) {
	result = sprintf('<input type=\"number\" step=\"1\" min=0 />', 'cracker', 'Polly', 'wants');;
}
function initEmptyForm() {
	result = "<form><table class=\"defForm\">";
	elementId = $(this).data("current-table").substr(1);
	for (var hNameId = 0; hNameId < tableHeader[elementId].length; hNameId++) {
		result += "<tr><td>" + tableHeader[elementId][hNameId]["name"] + "</td><td></td>";
	}
	result += "<tr><td colspan=\"2\"><button type=\"submit\">Create new</button>";
	result += "<button id=\"cancel\">Cancel</button></td></tr></table></form>";
	showPopup(this);
	$('#popuprel').html(result);
}
function showPopup(call_elem) {
	
	
	var popupid = $(call_elem).attr('rel');
	  
	  
	// Now we need to popup the marked which belongs to the rel attribute
	// Suppose the rel attribute of click link is popuprel then here in below code
	// #popuprel will fadein
	$('#' + popupid).fadeIn();
	  
	  
	// append div with id fade into the bottom of body tag
	// and we allready styled it in our step 2 : CSS
	$('#fade').css({'filter' : 'alpha(opacity=80)'}).fadeIn();
	  
	  
	// Now here we need to have our popup box in center of
	// webpage when its fadein. so we add 10px to height and width
	var popuptopmargin = ($('#' + popupid).height() + 10) / 2;
	var popupleftmargin = ($('#' + popupid).width() + 10) / 2;
	  
	  
	// Then using .css function style our popup box for center allignment
	$('#' + popupid).css({
	'margin-top' : -popuptopmargin,
	'margin-left' : -popupleftmargin
	});

}
function initTable(elementId) {
	result = "";
	for (var hNameId = 0; hNameId < tableHeader[elementId].length; hNameId++) {
		result += "<th>" + tableHeader[elementId][hNameId]["name"] + "</th>";
	}
	result += "<th>Action</th>";
	for (var i = 0; i < tableContent[elementId].length; i++) {
		result += "<tr>";
		for (var j = 0; j < tableContent[elementId][i].length; j++)
			result += "<td>" + tableContent[elementId][i][j] + "</td>";
		result += "<td><a class=\"action\">Edit</a>/<a class=\"action\">Remove</a></td>";
		result += "</tr>";
	}
	result += "</tr>";
	document.getElementById(elementId).innerHTML = result;
}
</script>
</head>
<body>
<div class="wrapper head">
  <header id="header" class="clear">
    <hgroup>
      <h1>CBR</h1>
      <h2>Case-based reasoning</h2>
    </hgroup>
    <form action="#" method="post">
      <fieldset><button id="exit">EXIT</button>
        <a id="user_name">Dmitriy Gaydashenko</a>
        
      </fieldset>
    </form>
    
  </header>
  <nav>
     <div id='cssmenu'>
<ul>
   <li class='active'><a href='precedents.html'><span>Precedents</span></a></li>
   <li><a href='#'><span>Tasks</span></a></li>
   <li><a href='#'><span>States</span></a></li>
   <li><a href='#'><span>Devices</span></a></li>
   <li><a href='#'><span>Hardware</span></a>
     <ul>
         <li><a href='#'><span>Processors</span></a></li>
         <li><a href='#'><span>Network adapters</span></a></li>
         <li><a href='#'><span>Memory</span></a></li>
         <li><a href='#'><span>Battery</span></a></li>
      </ul>
   </li>
   <li class='last'><a href='#'><span>More</span></a>
   <ul>
         <li><a href='#'><span>Manufacturers</span></a></li>
         <li><a href='#'><span>Hardware families</span></a></li>
      </ul>
   </li>
</ul>
</div>
    </nav>
</div>
<!-- content -->
<div class="wrapper content">
  <div id="container" class="clear">
    <!-- content body -->
    <ul class="tabs">
    <li><a href="#precedent">Precedents</a></li>
</ul>
<div class="tab_container">
    <table id="precedent" class="tab_content defTable">
    </table>
</div>
<div>
      <button rel="popuprel" id="create_new" data-current-table="none" class = "popup">Create new</button><button style="float:right;">Save</button>
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
 <div class="popupbox" id="popuprel">
</div>
<div id="fade"></div>
</body>
</html>
