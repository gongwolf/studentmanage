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
	$("#content-wrapper > div").append('<br> '+"Beginning sync portal student profile from portal ......");
	$.ajax({url: "../home/SynPortalData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
			updateApplications();
        }
	});
}

function updateApplications(){
	$("#content-wrapper > div").append("<br> Beginning sync application list data from portal ......");
	$.ajax({url: "../home/SynPortalApplicationData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
			updateProfileMentor();
        }
	});
}


function updateProfileMentor(){
	$("#content-wrapper > div").append("<br> Beginning sync mentor data from portal ......");
	$.ajax({url: "../home/SynPortalMentorData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
			updateSelfReportData();
        }
	});
}

function updateSelfReportData(){
	$("#content-wrapper > div").append("<br> Beginning sync self report data from portal ......");
	$.ajax({url: "../home/SynPortalSelfReportData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			$("#content-wrapper > div").append(result.text);
			$("#content-wrapper > div").append("<br>  Sync portal Data Done !!!!!")
        }
	});
}