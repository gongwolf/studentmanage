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
	$("#content-wrapper > div").append('<br> '+"Beginning sync portal student profile ......");
	$.ajax({url: "SynPortalData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
			updateApplications();
        }
	});
}

function updateApplications(){
	$("#content-wrapper > div").append("<br> Beginning sync application list data ......");
	$.ajax({url: "SynPortalApplicationData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
        }
	});
}