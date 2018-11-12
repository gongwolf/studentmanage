//var table;

$(document).ready(
		function() {
			$('#tableConferences .form-control.form-control-sm.newActitvity').datepicker();	
			$('#tableInternationalExps .form-control.form-control-sm.newInterEndDate').datepicker();	
			$('#tableInternationalExps .form-control.form-control-sm.newInterStartDate').datepicker();	
			$('#tableVolunteerExps .form-control.form-control-sm.newVolunteerStartDate').datepicker();	
			$('#tableVolunteerExps .form-control.form-control-sm.newVolunteerEndDate').datepicker();
			$('#tableIntern .form-control.form-control-sm.newInternStartDate').datepicker();
			$('#tableIntern .form-control.form-control-sm.newInternEndDate').datepicker();
			
			
			initFormYesNo();
			initActivitiesList();
			
			$('#current_query_year').on('change', function() {
				  var queryyear = $(this).val();
				  var url = "/studentmanage/student/"+student_id+"/otheractivities/"+queryyear;
				  window.location.href = url; 
			});
			
			$.ajax({url: "/studentmanage/home/mentor-all-list-json",
				type: "GET", 
		        dataType:'json',
				success: function(result){
					buildmentorListTable(result);
		        }
			});
			
			
			$('#addMentor').click(function(){
				$('#addMentorModel').modal('toggle');				
			});

			$('input[name=graduated]').click(function() {
				var Graduated_val = $('input[name=graduated]:checked').val();
				if (Graduated_val == 1) {
					$('#Graduated_Degree_label').show();
					$('#Graduated_Degree').show();
					$('#Graduated_Field_label').show();
					$('#Graduated_Field').show();
					$('#Graduated_Semester_label').show();
					$('#grad_semester').show();
				} else {
					$('#Graduated_Degree_label').hide();
					$('#Graduated_Degree').hide();
					$('#Graduated_Field_label').hide();
					$('#Graduated_Field').hide();
					$('#Graduated_Semester_label').hide();
					$('#grad_semester').hide();

				}
			});

			$('input[name=withdrew]').click(function() {
				var withdrew_val = $('input[name=withdrew]:checked').val();
				if (withdrew_val == 1) {
					$('#withdrew_reason_textarea').show();
				} else {
					$('#withdrew_reason_textarea').hide();

				}
			});
			
			$('input[name=transfered]').click(function() {
				var Transfered_val = $('input[name=transfered]:checked').val();
				if (Transfered_val == 1) {
					$('#transfer_info').show();
				} else if(Transfered_val == 0){
					$('#transfer_info').hide();

				}
			});
			
			$('input[name=fin_amp]').click(function() {
				var fin_sup_val = $('input[name=fin_amp]:checked').val();
				if (fin_sup_val == 1) {
					$('#Fin_sup_selection_label').show();
					$('#Fin_sup_selection_div').show();
				} else {
					$('#Fin_sup_selection_label').hide();
					$('#Fin_sup_selection_div').hide();

				}
			});

//			var jsonData = JSON.parse(yearlyBeans);
//			for (i in jsonData) {
//				var val = jsonData[i];
//				for (j in val) {
//					$('#content-wrapper > div').append(
//							j + ":" + JSON.stringify(val[j]) + "<br>");
//				}
//				$('#content-wrapper > div').append("<hr>");
//			}
			
			$('#Institution').change(function(){
				var school=$(this).val();
				$('#school_level').val(schools_level_obj[school]);
			});
			
			initActionsOfJsonTables();
			parseJsonTables();
			
			
		});

function parseJsonTables(){
	parseJsonInternTables();
	parseJsonConferenceTables();
	parseJsonPublicationTables();
	parseJsonVolunteerTables();
	parseJsonTravelTables();
	parseJsonCourseTables();
	
}

function initActionsOfJsonTables(){
	initActionsOfInternTables();
	initActionsOfConferencesTables();
	initActionsOfPublicationsTables();
	initActionsOfVolunteerTables();
	initActionsOfTravelTables();
	initActionsOfCourseWorkTables();
}

function initActionsOfInternTables(){
	$('#addMoreInterns > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeinterns'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newInterCN' name='newInterCN' placeholder='Company Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInterCity' name='newInterCity' placeholder='city' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInterState' name='newInterState' placeholder='state' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInternStartDate' name='newInternStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInternEndDate' name='newInternEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInterType' name='newInterType' placeholder='job type' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newInterDuty' name='newInterDuty' placeholder='duty' type='text' /></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableIntern > tbody ").append(markup);
		$('#tableIntern #newInternStartDate').datepicker();	
		$('#tableIntern #newInternEndDate').datepicker();	
	});
	
	$('#tableIntern').on('click','#removeinterns > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initActionsOfConferencesTables(){
	$('#addMoreConferences > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeconferences'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newConfsN' name='newConfsN' placeholder='Conference Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newConfsDate' name='newConfsDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newConfsPreTitle' name='newConfsPreTitle' placeholder='Presentation Title' type='text' /></td>"+
					 "<td>" +
					 "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
							"<option value=''>-- select one --</option>"+
							"<option value='Oral'>Oral</option>"+
							"<option value='Poster'>Poster</option>"+
							"<option value='OralandPoster'>Oral and Poster</option>"+
							"<option value='Other'>other</option>"+
					 "</select>"+
					 "</td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableConferences > tbody ").append(markup);
		$('#tableConferences #newConfsDate').datepicker();	
	});
	
	$('#tableConferences').on('click','#removeconferences > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initActionsOfPublicationsTables(){
	$('#addMorePublications > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removepublications'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td><textarea class='form-control' id='newpublications' rows='2'"+
					 "placeholder='Example: Mohammadlou, Hassan Beik, and Hamid Ekhteraei Toussi. “Parametric Studies on Elastoplastic Buckling of Rectangular FGM Thin Plates.” " +
					 "Aerospace Science and Technology, vol. 69, 2017. https://www.sciencedirect.com/science/article/pii/S127096381730651X'"+
					 "></textarea></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tablePublications > tbody ").append(markup);
	});
	
	$('#tablePublications').on('click','#removepublications > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initActionsOfVolunteerTables(){
	$('#addMoreVolunteer > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeVolunteerExps'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newVolunteerOrg' name='newVolunteerOrg' placeholder='Organization Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newVolunteerStartDate' name='newVolunteerStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newVolunteerEndDate' name='newVolunteerEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><small><textarea class='form-control' id='newVolunteerDuty' name='newVolunteerDuty' row='2' " +
					 "placeholder='Example: Advances environmental knowledge and awareness at NMSU and in the community.'></textarea></small></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableVolunteerExps > tbody ").append(markup);
		$('#tableVolunteerExps #newVolunteerStartDate').datepicker();	
		$('#tableVolunteerExps #newVolunteerEndDate').datepicker();	
	});
	
	$('#tableVolunteerExps').on('click','#removeVolunteerExps > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initActionsOfTravelTables(){
	$('#addMoreTravels > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removetravels'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newTravelCity' name='newTravelCity' placeholder='City' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newTravelState' name='newTravelState' placeholder='State' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newTravelStartDate' name='newTravelStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newTravelEndDate' name='newTravelEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newTravelPurpose' name='newTravelPurpose' placeholder='Purpose' type='text' /></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableTravel > tbody ").append(markup);
		$('#tableTravel #newTravelStartDate').datepicker();	
		$('#tableTravel #newTravelEndDate').datepicker();	
	});
	
	$('#tableTravel').on('click','#removetravels > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initActionsOfCourseWorkTables(){
	$('#addMoreCourses > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removecources'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newCourseName' name='newCourseName' placeholder='Course Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newCourseSemester' name='newCourseSemester' placeholder='semester/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newCourseNotes' name='newCourseNotes' placeholder='Notes' type='text' /></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableCoursework > tbody ").append(markup);	
	});
	
	$('#tableCoursework').on('click','#removecources > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function handleJsonInternTable(){
	
	var activitiesList="[";
	var i = 0;
	$("#tableIntern > tbody > tr").each(function(index){
		
		var aName = $(this).find("#newInterCN").val();
	    var aCity = $(this).find("#newInterCity").val();
	    var aState = $(this).find("#newInterState").val();
	    var aStart = $(this).find("#newInternStartDate").val();
	    var aEnd = $(this).find("#newInternEndDate").val();
	    var aType = $(this).find("#newInterType").val();
	    var aDuty = $(this).find("#newInterDuty").val();
	    
		if(aName!=""){
			i++;
			activitiesList+="{\"jobCompany\":\""+aName+"\"," +
	        				" \"jobCity\":\""+aCity+"\"," +
	        				" \"jobState\":\""+aState+"\"," +
	        				" \"jobStartDate\":\""+aStart+"\"," +
	        				" \"jobEndDate\":\""+aEnd+"\"," +
	        				" \"jobType\":\""+aType+"\"," +
					        " \"jobDuty\":\""+aDuty+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
	
	return activitiesList;
	
}

function handleJsonConferencesTable(){
	var activitiesList="[";
	var i = 0;
	$("#tableConferences > tbody > tr").each(function(index){
		
		var conferenceName = $(this).find("#newConfsN").val();
	    var conferenceDate = $(this).find("#newConfsDate").val();
	    var conferencePresentationTitle = $(this).find("#newConfsPreTitle").val();
	    var conferencePresentationType = $(this).find("#newConfsPreType").val();
	    
//	    alert(conferencePresentationType);
	    
		if(conferenceName!=""){
			i++;
			activitiesList+="{\"conferenceName\":\""+conferenceName+"\"," +
	        				" \"conferenceDate\":\""+conferenceDate+"\"," +
	        				" \"conferencePresentationTitle\":\""+conferencePresentationTitle+"\"," +
					        " \"conferencePresentationType\":\""+conferencePresentationType+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
	
	return activitiesList;
	
}

function handleJsonPublicationTable(){
	
	var activitiesList="[";
	var i = 0;
	$("#tablePublications > tbody > tr").each(function(index){
		var publication = $(this).find("#newpublications").val();
	    
		if(publication!=""){
			i++;
			activitiesList+="{\"publication\":\""+publication+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
	
	return activitiesList;
	
}

function handleJsonVolunteerTable(){
	var activitiesList="[";
	var i = 0;
	$("#tableVolunteerExps > tbody > tr").each(function(index){
		var volunteerOrg = $(this).find("#newVolunteerOrg").val();
	    var volunteerStartDate = $(this).find("#newVolunteerStartDate").val();
	    var volunteerEndDate = $(this).find("#newVolunteerEndDate").val();
	    var volunteerDuty = $(this).find("#newVolunteerDuty").val();
//	    alert(conferencePresentationType);
	    
		if(volunteerOrg!=""){
			i++;
			activitiesList+="{\"volunteerOrg\":\""+volunteerOrg+"\"," +
	        				" \"volunteerStartDate\":\""+volunteerStartDate+"\"," +
	        				" \"volunteerEndDate\":\""+volunteerEndDate+"\"," +
					        " \"volunteerDuty\":\""+volunteerDuty+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
//	alert(activitiesList);
	return activitiesList;
}

function handleJsonTravelTable(){
	var activitiesList="[";
	var i = 0;
	$("#tableTravel > tbody > tr").each(function(index){
	    var travelCity = $(this).find("#newTravelCity").val();
	    var travelState =$(this).find("#newTravelState").val();
	    var travelStartDate = $(this).find("#newTravelStartDate").val();
	    var travelEndDate = $(this).find("#newTravelEndDate").val();
	    var travelPurpose = $(this).find("#newTravelPurpose").val();
//	    alert(conferencePresentationType);
	    
		if(travelPurpose!=""){
			i++;
			activitiesList+="{\"travelCity\":\""+travelCity+"\"," +
	        				" \"travelState\":\""+travelState+"\"," +
	        				" \"travelStartDate\":\""+travelStartDate+"\"," +
	        				" \"travelEndDate\":\""+travelEndDate+"\"," +
					        " \"travelPurpose\":\""+travelPurpose+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
//	alert(activitiesList);
	return activitiesList;
}

function handleJsonCourseTable(){
	var activitiesList="[";
	var i = 0;
	$("#tableCoursework > tbody > tr").each(function(index){
	    var CourseName = $(this).find("#newCourseName").val();
	    var CourseSemester =$(this).find("#newCourseSemester").val();
	    var CourseNotes = $(this).find("#newCourseNotes").val();
//	    alert(conferencePresentationType);
	    
		if(CourseName!=""){
			i++;
			activitiesList+="{\"CourseName\":\""+CourseName+"\"," +
	        				" \"CourseSemester\":\""+CourseSemester+"\"," +
					        " \"CourseNotes\":\""+CourseNotes+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
//	alert(activitiesList);
	return activitiesList;
	
}

function parseJsonInternTables(){
	var interns = JSON.parse(intern_list);
	for (var i in interns) {
	    var aName = interns[i].jobCompany;
	    var aCity = interns[i].jobCity;
	    var aState = interns[i].jobState;
	    var aStart = interns[i].jobStartDate;
	    var aEnd = interns[i].jobEndDate;
	    var aType = interns[i].jobType;
	    var aDuty = interns[i].jobDuty;
	    
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removeinterns'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newInterCN' name='newInterCN' placeholder='Company Name' type='text' value='"+aName+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInterCity' name='newInterCity' placeholder='city' type='text' value='"+aCity+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInterState' name='newInterState' placeholder='state' type='text' value='"+aState+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInternStartDate' name='newInternStartDate' placeholder='MM/DD/YYYY' type='text' value='"+aStart+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInternEndDate' name='newInternEndDate' placeholder='MM/DD/YYYY' type='text' value='"+aEnd+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInterType' name='newInterType' placeholder='job type' type='text' value='"+aType+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newInterDuty' name='newInterDuty' placeholder='duty' type='text' value='"+aDuty+"'/></td>"+
		 "</tr>";
	    $("#tableIntern > tbody ").append(markup);
		$('#tableIntern #newInternStartDate').datepicker();	
		$('#tableIntern #newInternEndDate').datepicker();	
	}
}

function parseJsonConferenceTables(){
	var conferences = JSON.parse(conferences_list);
	for (var i in conferences) {
	    var conferenceName = conferences[i].conferenceName;
	    var conferenceDate = conferences[i].conferenceDate;
	    var conferencePresentationTitle = conferences[i].conferencePresentationTitle;
	    var conferencePresentationType = conferences[i].conferencePresentationType;
	    var selectstr = selectOption(conferencePresentationType);
//	    alert(selectstr);
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removeconferences'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newConfsN' name='newConfsN' placeholder='Conference Name' type='text' value='"+conferenceName+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newConfsDate' name='newConfsDate' placeholder='MM/DD/YYYY' type='text' value='"+conferenceDate+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newConfsPreTitle' name='newConfsPreTitle' placeholder='Presentation Title' type='text' value='"+conferencePresentationTitle+"'/></td>"+
		 "<td>" +selectstr+"</td>"+
		 "</tr>";
	    $("#tableConferences > tbody ").append(markup);
		$('#tableConferences #newConfsDate').datepicker();	
//		$('#tableConferences #newConfsPreType option[value='+conferencePresentationType+']').attr('selected','selected');
	}
}

function parseJsonPublicationTables(){
	var publication_obj = JSON.parse(publication_list);
	for (var i in publication_obj) {
	    var publication = publication_obj[i].publication;
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removepublications'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td><textarea class='form-control' id='newpublications' rows='2'"+
		 "placeholder='Example: Mohammadlou, Hassan Beik, and Hamid Ekhteraei Toussi. “Parametric Studies on Elastoplastic Buckling of Rectangular FGM Thin Plates.” " +
		 "Aerospace Science and Technology, vol. 69, 2017. https://www.sciencedirect.com/science/article/pii/S127096381730651X'"+
		 ">"+publication+"</textarea></td>"+
		 "</tr>";
	    //console.log(markup);
	    $("#tablePublications > tbody ").append(markup);
	}
}

function parseJsonTravelTables(){
	var travel = JSON.parse(travel_list);
	for (var i in travel) {
	    var travelCity = travel[i].travelCity;
	    var travelState = travel[i].travelState;
	    var travelStartDate = travel[i].travelStartDate;
	    var travelEndDate = travel[i].travelEndDate;
	    var travelPurpose = travel[i].travelPurpose;
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removetravels'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newTravelCity' name='newTravelCity' placeholder='City' type='text' value='"+travelCity+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newTravelState' name='newTravelState' placeholder='State' type='text' value='"+travelState+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newTravelStartDate' name='newTravelStartDate' placeholder='MM/DD/YYYY' type='text' value='"+travelStartDate+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newTravelEndDate' name='newTravelEndDate' placeholder='MM/DD/YYYY' type='text' value='"+travelEndDate+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newTravelPurpose' name='newTravelPurpose' placeholder='Purpose' type='text' value='"+travelPurpose+"'/></td>"+
		 "</tr>";
	    //console.log(markup);
	     $("#tableTravel > tbody ").append(markup);
	     $('#tableTravel #newTravelStartDate').datepicker();	
	     $('#tableTravel #newTravelEndDate').datepicker();	
	}
}

function parseJsonVolunteerTables(){
	var volunteers = JSON.parse(volunteer_list);
//	alert(volunteer_list);
	for (var i in volunteers) {
	    var volunteerOrg = volunteers[i].volunteerOrg;
	    var volunteerStartDate = volunteers[i].volunteerStartDate;
	    var volunteerEndDate = volunteers[i].volunteerEndDate;
	    var volunteerDuty = volunteers[i].volunteerDuty;
//	    alert(selectstr);
		var markup = "<tr>"+
				     "<td><a href='javascript:void(0);' id='removeVolunteerExps'"+
				     "Title='Remove Entry'><span><i class='fa fa-times'"+
				     "aria-hidden='true'></i></span></a></td>"+
				     "<td></td>"+
				     "<td><input class='form-control form-control-sm' id='newVolunteerOrg' name='newVolunteerOrg' placeholder='Organization Name' type='text' value='"+volunteerOrg+"'/></td>"+
				     "<td><input class='form-control form-control-sm' id='newVolunteerStartDate' name='newVolunteerStartDate' placeholder='MM/DD/YYYY' type='text' value='"+volunteerStartDate+"'/></td>"+
				     "<td><input class='form-control form-control-sm' id='newVolunteerEndDate' name='newVolunteerEndDate' placeholder='MM/DD/YYYY' type='text'value='"+volunteerEndDate+"'/></td>"+
				     "<td><small><textarea class='form-control' id='newVolunteerDuty' name='newVolunteerDuty' row='2' " +
				     "placeholder='Example: Advances environmental knowledge and awareness at NMSU and in the community.'>"+volunteerDuty+"</textarea></small></td>"+
				     "</tr>";
//			console.log(markup);
		$("#tableVolunteerExps > tbody ").append(markup);
		$('#tableVolunteerExps #newVolunteerStartDate').datepicker();	
		$('#tableVolunteerExps #newVolunteerEndDate').datepicker();	
	}
}

function parseJsonCourseTables(){
	var courses = JSON.parse(course_list);
//	alert(volunteer_list);
	for (var i in courses) {
	    var CourseName = courses[i].CourseName;
	    var CourseSemester = courses[i].CourseSemester;
	    var CourseNotes = courses[i].CourseNotes;
//	    alert(selectstr);
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removecources'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newCourseName' name='newCourseName' placeholder='Course Name' type='text' value='"+CourseName+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newCourseSemester' name='newCourseSemester' placeholder='semester/YYYY' type='text' value='"+CourseSemester+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newCourseNotes' name='newCourseNotes' placeholder='Notes' type='text' value='"+CourseNotes+"'/></td>"+
		 "</tr>";
	    //console.log(markup);
	    $("#tableCoursework > tbody ").append(markup);	
	}
}

function buildmentorListTable(mentorList){
	    var table = $('#mentordataTable').DataTable( {
		"aaData": mentorList, 
		"aoColumns": [
			{"mData":"mentor_id",
				"mRender":function(data, type, full){
				return '<a href="../mentor/profile/'+full.mentor_id+'" id="profile-mentor_id" >'+full.mentor_id+'</a>';}
			}, 
			{"mData":"name"},
			{"mData":"intitution"},
			{"mData":"deportment"},
//			{"mData": "",
//				"mRender":function(data, type, full){
////					var str = '<a href="../mentor/mentor-student-list/'+full.mentor_id+'" id="MentorStuList btn" class="btn btn-outline-secondary">Student List</a>';
//					var str = '<a href="../home/student-all-list" id="MentorStuList btn" class="btn btn-outline-secondary">Student List</a>';
////					str+='<a href="../student/postAMPActivities/'+full.user_id+'" id="postAMPActivities-btn" class="btn btn-outline-secondary">Post Activities</a>';
//					return str;
//				 }
//			}
          ],
          "paging":true,
          "pageLength":10,
          "ordering":true,
          "pagingType": "simple",
          "order":[2,"asc"]
	    }); 
	    
	    $('#mentordataTable tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    });
	    
	    $('#addMentorModel > div > div > div.modal-footer > a').click(function () {
			var mentor_id=table.row('.selected').data()["mentor_id"];
			var mentor_name=table.row('.selected').data()["name"];
			$('#Mentor').val(mentor_name);
			$('#MentorID').val(mentor_id);
			$('#addMentorModel').modal('hide');

		});
}

function initFormYesNo(){
	if($('input[name=graduated]:checked').val()==1){
		$('#Graduated_Degree_label').show();
		$('#Graduated_Degree').show();
		$('#Graduated_Field_label').show();
		$('#Graduated_Field').show();
		$('#Graduated_Semester_label').show();
		$('#grad_semester').show();
	}else{
		$('#Graduated_Degree_label').hide();
		$('#Graduated_Degree').hide();
		$('#Graduated_Field_label').hide();
		$('#Graduated_Field').hide();
		$('#Graduated_Semester_label').hide();
		$('#grad_semester').hide();
	}
	
	
	if($('input[name=transfered]:checked').val()==1){
		$('#transfer_info').show();
	}else{
		$('#transfer_info').hide();
	}
	
	
	if($('input[name=withdrew]:checked').val()==1){
		$('#withdrew_reason_textarea').show();
	}else{
		$('#withdrew_reason_textarea').hide();
	}
	
	if($('input[name=fin_amp]:checked').val()==1){
		$('#Fin_sup_selection_label').show();
		$('#Fin_sup_selection_div').show();
	} else {
		$('#Fin_sup_selection_label').hide();
		$('#Fin_sup_selection_div').hide();
	}
}

function submitOtherActivitiesForm(){
	var activitiesList=dealCollegeActivities();
	activitiesList+=dealUniversityActivities();
	internList=handleJsonInternTable();
	confsList=handleJsonConferencesTable();
	publicationList=handleJsonPublicationTable();
	volunteerList=handleJsonVolunteerTable();
	travelList=handleJsonTravelTable();
	coursetakenjson=handleJsonCourseTable();
//	alert(publicationList);
	$('#activitiesList').val(activitiesList);
	$('#internList').val(internList);
	$('#confsList').val(confsList);
	$('#publicationList').val(publicationList);
	$('#volunteerList').val(volunteerList);
	$('#travelList').val(travelList);
	$('#coursetakenjson').val(coursetakenjson);
	$('#otheractivityForm').submit();
}

function dealCollegeActivities(){
	var college_jstr="[";
	$('#college_acts_div > div').each(function(){
		var activity_abr = $(this).find('label').attr('id');
		var activity_fullname = $(this).find('label').text();
		activity_fullname = activity_fullname.substring(0, activity_fullname.length - 1)
		var fallchecked   = $(this).find('div:nth-child(2) > input').is(':checked');
		var springchecked = $(this).find('div:nth-child(3) > input').is(':checked');
		var summerchecked = $(this).find('div:nth-child(4) > input').is(':checked');
		college_jstr+="{ \"abr\" : \""+activity_abr+"\" ";
		college_jstr+=" ,\"fullname\": \""+activity_fullname+"\"";
		college_jstr+= " ,\"fall\": \""+fallchecked+"\"";
		college_jstr+= " ,\"spring\": \""+springchecked+"\"";
		college_jstr+= " ,\"summer\": \""+summerchecked+"\"},";
		
//		alert(activity_abr+"  "+activity_fullname+"\n "+fallchecked+" "+springchecked+" "+summerchecked);
	});
//	college_jstr = college_jstr.substring(0, college_jstr.length - 1)
//	college_jstr+="]";
	return college_jstr;
}

function dealUniversityActivities(){
	var college_jstr="";
	$('#university_acts_div > div').each(function(){
		var activity_abr = $(this).find('label').attr('id');
		var activity_fullname = $(this).find('label').text();
		activity_fullname = activity_fullname.substring(0, activity_fullname.length - 1)
		var fallchecked   = $(this).find('div:nth-child(2) > input').is(':checked');
		var springchecked = $(this).find('div:nth-child(3) > input').is(':checked');
		var summerchecked = $(this).find('div:nth-child(4) > input').is(':checked');
		college_jstr+="{ \"abr\" : \""+activity_abr+"\" ";
		college_jstr+=" ,\"fullname\": \""+activity_fullname+"\"";
		college_jstr+= " ,\"fall\": \""+fallchecked+"\"";
		college_jstr+= " ,\"spring\": \""+springchecked+"\"";
		college_jstr+= " ,\"summer\": \""+summerchecked+"\"},";
		
//		alert(activity_abr+"  "+activity_fullname+"\n "+fallchecked+" "+springchecked+" "+summerchecked);
	});
	college_jstr = college_jstr.substring(0, college_jstr.length - 1)
	college_jstr+="]";
	return college_jstr;
}

function initActivitiesList(){
	var acts = JSON.parse(activities_list);
//	alert(activities_list);
	for (var i in acts) {
	    var activity_abr = acts[i].abr;
	    var activity_fullname = acts[i].fullname;
	    var fallchecked = (acts[i].fall == 'true');
	    var springchecked =(acts[i].spring == 'true');
	    var summerchecked = (acts[i].summer == 'true');
//	    if(fallchecked==true){
//		    alert(activity_abr+" "+fallchecked+" "+springchecked+" "+summerchecked);
//	    }
	    $('#'+activity_abr).nextAll().eq(0).find('input').prop("checked",fallchecked);
	    $('#'+activity_abr).nextAll().eq(1).find('input').prop("checked",springchecked);
	    $('#'+activity_abr).nextAll().eq(2).find('input').prop("checked",summerchecked);
	}
}

function selectOption(conferencePresentationType){
	switch(conferencePresentationType){
	case 'Oral': 
		return "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Oral' selected='selected'>Oral</option>"+
		"<option value='Poster'>Poster</option>"+
		"<option value='OralandPoster'>Oral and Poster</option>"+
		"<option value='Other'>Other</option>"+
	    "</select>";
	case 'Poster':
		 return "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
			"<option value=''>-- select one --</option>"+
			"<option value='Oral'>Oral</option>"+
			"<option value='Poster'  selected='selected'>Poster</option>"+
			"<option value='OralandPoster'>Oral and Poster</option>"+
			"<option value='Other'>Other</option>"+
		    "</select>";
	case 'OralandPoster': 
		return "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Oral'>Oral</option>"+
		"<option value='Poster'>Poster</option>"+
		"<option value='OralandPoster'  selected='selected'>Oral and Poster</option>"+
		"<option value='Other'>Other</option>"+
	    "</select>";
	case 'Other': 
		return "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Oral'>Oral</option>"+
		"<option value='Poster'>Poster</option>"+
		"<option value='OralandPoster'>Oral and Poster</option>"+
		"<option value='Other'  selected='selected'>Other</option>"+
	    "</select>";
	default: 
		return "<select class='form-control form-control-sm' id='newConfsPreType' name='newConfsPreType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Oral'>Oral</option>"+
		"<option value='Poster'>Poster</option>"+
		"<option value='OralandPoster'>Oral and Poster</option>"+
		"<option value='Other'>Other</option>"+
	    "</select>";
	}
	
}











