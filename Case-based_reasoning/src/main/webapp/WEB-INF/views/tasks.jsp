<!DOCTYPE html>
<html lang="en">
<head>
<title>CBR</title>
<meta charset="utf-8">
<link rel="stylesheet" href="./resources/styles/layout.css" type="text/css">
<script src="./resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	tableContent = {"task" : null};
	$("#create_new").click(function() {
		$("div[id=taskCreator] input[type=number]").val("");
	});
});
</script>
<script src="./resources/js/table.js"></script>
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
        <a id="user_name">${fName} ${lName}</a>
        
      </fieldset>
    </form>
    
  </header>
  <nav>
     <div id='cssmenu'>
<ul>
   <li><a href='precedents'><span>Precedents</span></a></li>
   <li class='active'><a href='tasks'><span>Tasks</span></a></li>
   <li><a href='#'><span>Devices...</span></a>
   <ul>
         <li><a href='#'><span>Devices</span></a></li>
         <li><a href='#'><span>Device-State</span></a></li>
      </ul>
   </li>
   <li><a href='#'><span>States</span></a></li>
   <li><a href='#'><span>Hardware...</span></a>
     <ul>
         <li><a href='#'><span>Processors</span></a></li>
         <li><a href='#'><span>Network adapters</span></a></li>
         <li><a href='#'><span>Memory</span></a></li>
         <li><a href='#'><span>Battery</span></a></li>
      </ul>
   </li>
   <li class='last'><a href='#'><span>More...</span></a>
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
<div id="mainContnent" class="wrapper content">
  <div id="container" class="clear">
    <!-- content body -->
    <ul id="mainContentTabs" class="tabs">
    <li><a href="#task">Tasks</a></li>
</ul>
	<div id = "task">
	<div class="tab_container">
    	<table class="tab_content defTable">
    	<thead><tr><th>Task</th><th>Computational complexity</th>
    	<th> Memory Requirements(GB)</th>
    	<th>Volume of data downloaded(MB)</th>
    	<th>Volume of data uploaded(MB)</th>
    	<th>Running time requirements(s)</th></tr>
    	</thead>
    	<tbody></tbody>
    	</table>
    </div>
    	<div id ="taskPageList" class="pageList">
    </div>
	</div>
<div>
      <button rel="popuprel" id="create_new" data-current-table="none" class = "popup">Create new</button>
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
 <div style="width:50%; height:50%;"class="popupbox" id="taskCreator">
 <form id="taskCreationForm" action="addTask" method="POST">
    <table class="defForm">
    <tr><td>Computational complexity</td><td><input name="complex" type="number" min="1.25e-10" max="3.4028235e+38" step="0.1" required></td></tr>
    <tr><td> Memory Requirements(GB)</td><td><input name="memoryNeed" type="number" min="1.25e-10" max="3.4028235e+38" step="0.1" required></td></tr>
    <tr><td>Volume of data downloaded(MB)</td><td><input name="dataToDown" type="number" min="1.25e-10" max="3.4028235e+38" step="0.1" required></td></tr>
    <tr><td>Volume of data uploaded(MB)</td><td><input name="dataToUp" type="number" min="1.25e-10" max="3.4028235e+38" step="0.1" required></td></tr>
    <tr><td>Running time requirements(s)</td><td><input name="timeReq" type="number" min="1.25e-10" max="3.4028235e+38" step="0.1" required></td></tr>
    <tr><td><input type="submit" class="button" value="Add"></td><td><button id="cancel">Cancel</button></td></tr>
    </table>
    <div id="error">${Error}</div>
</form>
 </div>
<div id="fade"></div>
</body>
</html>
