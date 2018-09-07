/**
 * 
 */

jQuery(document).ready(function($) {
		
});

$("#syncportal").click(function() {
	updateStudentDB();
});

function updateStudentDB(){
	$.ajax({url: "SynPortalData",
		type: "GET", 
        dataType:'json',
		success: function(result){
			console.log(JSON.stringify(result));
			$("#content-wrapper > div").html(result.text);
        }
	});
}