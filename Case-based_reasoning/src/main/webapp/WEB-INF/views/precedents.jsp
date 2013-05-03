<!DOCTYPE html>
<html lang="en">
<head>
<title>CBR</title>
<meta charset="utf-8">
<link rel="stylesheet" href="./resources/styles/layout.css" type="text/css">
<script>
tableContent = {"precedent" : null};  
</script>
<script src="./resources/js/jquery-1.9.1.min.js"></script>
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
   <li class='active'><a href='precedents'><span>Precedents</span></a></li>
   <li><a href='tasks'><span>Tasks</span></a></li>
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
    <li><a href="#precedent">Precedents</a></li>
</ul>
	<div id = "precedent">
	<div class="tab_container">
    	<table class="tab_content defTable">
    	<thead><tr><th>Precedent</th><th>Task</th><th>Device</th><th>State</th><th>Need time to solve</th><th>Is solved correctly</th><th>Action</th></tr>
    	</thead>
    	<tbody></tbody>
    	</table>
    </div>
    	<div id ="precedentPageList" class="pageList">
    </div>
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
 <div class="popupbox" id="precedentCreator">
	<ul class="tabs">
    <li><a href="#task">Tasks</a></li>
    <li><a href="#device">Devices</a></li>
    <li><a href="#state">Tasks</a></li>
    <li><a href="#save">Save</a></li>
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
      <a class="button active">1</a><a class="button">2</a>
    </div>
	</div>
</div>
<div id="fade"></div>
</body>
</html>
