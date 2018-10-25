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
			
		});


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

function submitYearlyForm(){
	var activitiesList=dealCollegeActivities();
	$('#activitiesList').val(activitiesList);
	$('#yearlyForm').submit();
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
		
		alert(activity_abr+"  "+activity_fullname+"\n "+fallchecked+" "+springchecked+" "+summerchecked);
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














