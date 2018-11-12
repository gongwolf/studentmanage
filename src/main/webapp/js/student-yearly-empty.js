$(document).ready(function(){
		$('#current_query_year').on('change', function() {
			var queryyear = $(this).val();
			var url = "/studentmanage/student/"+student_id+"/yearlyreport/"+queryyear;
			window.location.href = url; 
		});
});

function createdNewReport(){
	var queryYear = $('#current_query_year').val();
	var id = student_id;
	url = "/studentmanage/student/"+id+"/yearlyreport/new/"+queryYear;
	window.location.href = url; 
}