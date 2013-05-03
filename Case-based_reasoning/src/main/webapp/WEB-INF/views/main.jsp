<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://datatables.net/download/build/jquery.dataTables.js"></script>
<style type="text/css">
body{
	width : 90%;
}
div.dataTables_length {
	float: left;
}
div.dataTables_filter {
	float: right;
}
div.dataTables_info {
	float: left;
}
.dataTables_paginate {
	float: right;
	text-align: right;
}
.paging_two_button {
	cursor: pointer;
}
</style>
<script type="text/javascript">
function init() {
	$(document).ready( function () {
		$('#content').dataTable( {
			"sDom": 'T<"clear">lfrtip',
			"oTableTools": {
				"sRowSelect": "multi",
				"aButtons": [ "select_all", "select_none" ]
			}
		} );
	} );

}</script>
	<title>Welcome</title>
</head>
<body onload="init()">
<h1>
	Welcome!  
</h1>

<P>${userName}</P>
<p>Unconfirmed users</p>
<div >
<table id="content">
    <thead>
        <tr>
            <th>First name</th>
            <th>Second name</th>
            <th>Email</th>
            <th>User type</th>
        </tr>
    </thead>
    <tbody>
	<c:forEach items="${users}" var="user">
	<tr>
		<td>${user.getFName()}</td>
		<td>${user.getLName()}</td>
		<td>${user.getEmail()}</td>
		<td>${user.getUserType().getUserTypeName()}</td>
	</tr>
	</c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
