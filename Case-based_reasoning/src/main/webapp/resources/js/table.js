var MAX = 10;
$(document).ready(function() {
	initMainContentTabs();
	 //On Click Event
	 $("#mainContentTabs li").click(changeMainContentTab);
	// Now define one more function which is used to fadeout the 
	// fade layer and popup window as soon as we click on fade layer
	$('#fade').click(close);
	$('#cancel').click(close);
	$("#create_new").click(createNew);
});
function changePage() {
	 if($(this).attr("class").indexOf("active") !== -1)
		 return;
	 parentId = $(this).parent().get(0).id;
	 $("#" + parentId+" a").removeClass("active"); //Remove any "active" class
	 $(this).addClass("active"); //Add "active" class to selected tab
	 getData(parentId.substring(0, parentId.length - "PageList".length), ($(this).html() - 1)*MAX);
}
function changeMainContentTab() {
	 if($(this).attr("class").indexOf("active") !== -1)
		 return;
 	$("#mainContnentTabs.tabs li").removeClass("active"); //Remove any "active" class
 	$(this).addClass("active"); //Add "active" class to selected tab
 	$(".tab_content").hide(); //Hide all tab content
 	var activeHref = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
 	$(activeHref).fadeIn(); //Fade in the active ID content
 	 getData(activeHref.substring(1, activeHref.length), 0);
 	$("#create_new").data("current-table", activeHref);
 	 return false;
 }
function initMainContentTabs() {
	 //When page loads...
	 $(".tab_content").hide(); //Hide all content
	 active = $("#mainContentTabs li:first"); //Activate first tab
	 active.addClass("active").show();
	 activeHref =  $(active).find("a").attr("href");
	 $("#create_new").data("current-table", activeHref);
	 $(".tab_content:first").show(); //Show first tab content
	 tableName = activeHref.substring(1, activeHref.length);
	 getDataCount(tableName);
	 getData(tableName, 0);
}
function getData(tableName, from) {
	$.ajax({
		url : "./get"+tableName,
		type : "POST",
		data : {from : from, max : MAX},
		success: function(data){
			tableContent[tableName] = data;
			initTable(tableName);
		}
	});
	
}
function getDataCount(tableName) {
	$.ajax({
		url : "./get"+tableName+"Num",
		type : "POST",
		success: function(num){
			pageNum = num/MAX + (num%MAX == 0 ? 0 : 1);
			initPages(tableName, pageNum);
			$("#" +tableName+"PageList a").click(changePage);
		}
	});
}
function initPages(tableName, num) {
	result = " <a class=\"button active\">1</a>";
	for(var i = 2; i <= num; i++) {
		result += " <a class=\"button\">"+i+"</a>";
	}
	$("#"+tableName+"PageList").html(result);
}
function createNew() {
	showPopup($("#create_new").data("current-table")+ "Creator");
}
function close(){
	// Add markup ids of all custom popup box here 						  
	$('#fade ,' + $("#create_new").data("current-table")+ "Creator").fadeOut();
	return false;
}
function showPopup(popupid) {  
	  
	// Now we need to popup the marked which belongs to the rel attribute
	// Suppose the rel attribute of click link is popuprel then here in below code
	// #popuprel will fadein
	$(popupid).fadeIn();
	  
	  
	// append div with id fade into the bottom of body tag
	// and we allready styled it in our step 2 : CSS
	$('#fade').css({'filter' : 'alpha(opacity=80)'}).fadeIn();
	  
	  
	// Now here we need to have our popup box in center of
	// webpage when its fadein. so we add 10px to height and width
	var popuptopmargin = ($(popupid).height() + 10) / 2;
	var popupleftmargin = ($(popupid).width() + 10) / 2;
	  
	  
	// Then using .css function style our popup box for center allignment
	$(popupid).css({
	'margin-top' : -popuptopmargin,
	'margin-left' : -popupleftmargin
	});

}
function initTable(elementName) {
	result = "";
	for (var i = 0; i < tableContent[elementName].length; i++) {
		result += "<tr>";
		for (var j = 0; j < tableContent[elementName][i].length; j++)
			result += "<td>" + tableContent[elementName][i][j] + "</td>";
		result += "<td><a class=\"action\">Edit</a>/<a class=\"action\">Remove</a></td>";
		result += "</tr>";
	}
	result += "</tr>";
	$("#" + elementName + "> div > table > tbody").html(result);
}