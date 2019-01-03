/**
 * 
 */

jQuery(document).ready(function($) {
	$('#birthDate').datepicker();	
	$('#tableActitvities .form-control.form-control-sm.newActitvity').datepicker();	
	
	$('#div_disa_type').hide();
	
	if($('#disability').val()=="YES"){
		$('#div_disa_type').show();
	}
		
	$('#disability').change(function() {
	    var val = $(this).val();
	    if(val=="YES"){
	    	$('#div_disa_type').show();
	    }else{
	    	$('#div_disa_type').hide();
	    }
	  });
	
	if($('#hs_testing').val()==""){
		$('#test_scores').hide();
	}else{
		$('#test_scores').show();
	}
	
	$('#hs_testing').change(function() {
	    var val = $(this).val();
	    if(val!=""){
	    	$('#test_scores').show();
	    }else{
	    	$('#test_scores').hide();
	    }
	  });
	

	
	
	$('#addMoreActivities > span > i').click(function(){
//		alert("click add activity button");
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeActivity'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm'"+
					 "id='activityName' name='activityName'"+
					 "placeholder='Activity Name' type='text' /></td>"+
					 "<td>"+
					 "<input class='form-control form-control-sm newActitvity'"+
					 "id='activityDate' name='activityDate' placeholder='MM/DD/YYYY' type='text' />"+
					 "</td>"+
					 "<td><input class='form-control form-control-sm'"+
					 "id='activityType' name='activityType'"+
					 "placeholder='Activity Name' type='text' /></td>"+
					 +"</tr>";
		$("#tableActitvities > tbody ").append(markup);
		$('#tableActitvities .form-control.form-control-sm.newActitvity').datepicker();	
	});
	
	$('#tableActitvities').on('click','#removeActivity > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
	
	parseActivities(student_activities);
	
//	$('#uploadRecommendModal').modal({
//	    backdrop: 'static',
//	    keyboard: false,
//	    show:false
//	});
	
	$('#uploadCmdBtn').click(function(e) {
		e.preventDefault();
		var fileInput = $('#recommendationFile');
	    var file = fileInput.prop('files')[0];
	    
	    if(typeof file != 'undefined'){
	    	var $modal = $('#uploadRecommendModal');
		    var $bar = $modal.find('.progress-bar');
		    
		    $modal.modal('show');
		    var formData = new FormData();
		    
	    	formData.append("file",file);
	    	console.log(file);
		    
	    	var ajaxReq = $.ajax({
		        url : '/studentmanage/student/profile/recommendationFileUpload/'+student_id,
		        type : 'POST',
		        data : formData,
		        cache : false,
		        contentType : false,
		        processData : false,
		        xhr: function(){
		          //Get XmlHttpRequest object
		           var xhr = $.ajaxSettings.xhr() ;
		          //Set onprogress event handler 
		           xhr.upload.onprogress = function(event){
		            	var perc = Math.round((event.loaded / event.total) * 100);
		            	$('#progressBar').text(perc + '%');
		            	$('#progressBar').css('width',perc + '%');
		            	if(perc==100){
		            		$('#progressModalHeader > h1 ').text("Process on Server ....");
		            	}
		           };
		           return xhr ;
		      	},
		      	beforeSend: function( xhr ) {
		      		//Reset alert message and progress bar
//		      		$('#alertMsg').text('');
		      		$('#progressBar').text('');
		      		$('#progressBar').css('width','0%');
		      		}
		      });
	    	
	    	ajaxReq.done(function(result) {
//		    	alert(result);
	      		$('#progress').html(result);
	      		$('#recommendationFile').val('');
	      		$('#downloadRecommendationlink').text(file.name);
	      		setTimeout(function(){$('#uploadRecommendModal').modal('hide');},1000);
		      });
		    
	    	
	    }

	});	    
	
	
	$('#uploadTrscBtn').click(function(e) {
		e.preventDefault();
		var fileInput = $('#TranscriptFile');
	    var file = fileInput.prop('files')[0];
	    
	    if(typeof file != 'undefined'){
	    	var $modal = $('#uploadRecommendModal');
		    var $bar = $modal.find('.progress-bar');
		    
		    $modal.modal('show');
		    var formData = new FormData();
		    
	    	formData.append("file",file);
	    	console.log(file);
		    
	    	var ajaxReq = $.ajax({
		        url : '/studentmanage/student/profile/transcriptFileUpload/'+student_id,
		        type : 'POST',
		        data : formData,
		        cache : false,
		        contentType : false,
		        processData : false,
		        xhr: function(){
		          //Get XmlHttpRequest object
		           var xhr = $.ajaxSettings.xhr() ;
		          //Set onprogress event handler 
		           xhr.upload.onprogress = function(event){
		            	var perc = Math.round((event.loaded / event.total) * 100);
		            	$('#progressBar').text(perc + '%');
		            	$('#progressBar').css('width',perc + '%');
		            	if(perc==100){
		            		$('#progressModalHeader > h1 ').text("Process on Server ....");
		            	}
		           };
		           return xhr ;
		      	},
		      	beforeSend: function( xhr ) {
		      		//Reset alert message and progress bar
//		      		$('#alertMsg').text('');
		      		$('#progressBar').text('');
		      		$('#progressBar').css('width','0%');
		      		}
		      });
	    	
	    	ajaxReq.done(function(result) {
//		    	alert(result);
	      		$('#progress').html(result);
	      		$('#TranscriptFile').val('');
	      		$('#downloadTranscriptlink').text(file.name);
	      		setTimeout(function(){$('#uploadRecommendModal').modal('hide');},1000);
		      });
		    
	    	
	    }

	});	    
});

function parseActivities(student_activities){
	var acts = JSON.parse(student_activities);
	if(acts==null){
		return;
	}
	var alist = acts.activities;
	for (var i in alist) {
	    var aName = alist[i].activityName;
	    var aDate = alist[i].activityDate;
	    var aType = alist[i].activityType;
	    
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removeActivity'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm'"+
		 "id='activityName' name='activityName'"+
		 "placeholder='Activity Name' type='text' value='"+aName+"' /></td>"+
		 "<td>"+
		 "<input class='form-control form-control-sm newActitvity'"+
		 "id='activityDate' name='activityDate' placeholder='MM/DD/YYYY' type='text' value='"+aDate+"'/>"+
		 "</td>"+
		 "<td><input class='form-control form-control-sm'"+
		 "id='activityType' name='activityType'"+
		 "placeholder='Activity Name' type='text'  value='"+aType+"' /></td>"+
		 +"</tr>";
	    $("#tableActitvities > tbody ").append(markup);
	    $('#tableActitvities .form-control.form-control-sm.newActitvity').datepicker();	
	}
	
}

function submitProfileForm()
{
	$('#profileForm').submit();
}

function submitHighSchoolForm()
{
	var activitiesList="{\"activities\":[";
	var i = 0;
	$("#tableActitvities > tbody > tr").each(function(index){
		console.log(index+" "+$(this).find("#activityName").val());
		console.log(index+" "+$(this).find("#activityDate").val());
		console.log(index+" "+$(this).find("#activityType").val());
		console.log("==================================");
//		var currentRow=$(this);
		var activityName = $(this).find("#activityName").val();
		var activityDate = $(this).find("#activityDate").val();
		var activityType = $(this).find("#activityType").val();
		if(activityName!=""){
			i++;
			activitiesList+="{\"activityName\":\""+activityName+"\",\"activityDate\":\""+activityDate+"\",\"activityType\":\""+activityType+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]}";
	}else{
		activitiesList = activitiesList+"]}";
	}
	$('#activitiesList').val(activitiesList);
	$('#HighSchoolForm').submit();
}
function escapeSpecialChars(jsonString) {
    return jsonString.replace(/\n/g, "\\n")
        .replace(/\r/g, "\\r")
        .replace(/\t/g, "\\t")
        .replace(/\f/g, "\\f");

}



