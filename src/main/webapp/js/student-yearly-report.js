//var table;

$(document).ready(
		function() {
			$('#tableConferences .form-control.form-control-sm.newActitvity').datepicker();	
			$('#tableInternationalExps .form-control.form-control-sm.newInterEndDate').datepicker();	
			$('#tableInternationalExps .form-control.form-control-sm.newInterStartDate').datepicker();	
			$('#tableVolunteerExps .form-control.form-control-sm.newVolunteerStartDate').datepicker();	
			$('#tableVolunteerExps .form-control.form-control-sm.newVolunteerEndDate').datepicker();
			
			
			
			
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

			$('input[name=Graduated]').click(function() {
				var Graduated_val = $('input[name=Graduated]:checked').val();
				if (Graduated_val == "Graduated_Y") {
					$('#Graduated_Degree_label').show();
					$('#Graduated_Degree').show();
					$('#Graduated_Field_label').show();
					$('#Graduated_Field').show();
				} else {
					$('#Graduated_Degree_label').hide();
					$('#Graduated_Degree').hide();
					$('#Graduated_Field_label').hide();
					$('#Graduated_Field').hide();

				}
			});

			$('input[name=withdrewInYear]').click(function() {
				var withdrew_val = $('input[name=withdrewInYear]:checked').val();
				if (withdrew_val == "withdrew_Y") {
					$('#withdrew_reason_textarea').show();
				} else {
					$('#withdrew_reason_textarea').hide();

				}
			});
			
			$('input[name=Transfered]').click(function() {
				var Transfered_val = $('input[name=Transfered]:checked').val();
				if (Transfered_val == "Transfered_Y") {
					$('#transfer_info').show();
				} else if(Transfered_val == "Transfered_N"){
					$('#transfer_info').hide();

				}
			});
			
			$('input[name=Financial_support]').click(function() {
				var fin_sup_val = $('input[name=Financial_support]:checked').val();
				if (fin_sup_val == "Fin_sup_Y") {
					$('#Fin_sup_selection_label').show();
					$('#Fin_sup_selection_div').show();
				} else {
					$('#Fin_sup_selection_label').hide();
					$('#Fin_sup_selection_div').hide();

				}
			});

			var jsonData = JSON.parse(yearlyBeans);
			for (i in jsonData) {
				var val = jsonData[i];
				for (j in val) {
					$('#content-wrapper > div').append(
							j + ":" + JSON.stringify(val[j]) + "<br>");
				}
				$('#content-wrapper > div').append("<hr>");
			}
			
			
			
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