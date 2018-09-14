/**
 * 
 */

jQuery(document).ready(function($) {
		
});

$("#syncportal").click(function() {
	updateStudentDB();
});

function updateStudentDB(){
	$("#content-wrapper > div").html("Begin Sync portal Data");
	$.ajax({url: "SynPortalData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").html(result.text);
			updateApplications();
        }
	});
}

function updateApplications(){
	$("#content-wrapper > div").html("Begin Sync portal Data");
	$.ajax({url: "SynPortalApplicationData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").html(result.text);
        }
	});
}