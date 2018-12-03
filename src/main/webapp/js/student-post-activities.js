/**
 * 
 */

jQuery(document).ready(function($) {
	initializeTables();
	parseTables();
});

function initializeTables(){
	initializeSubDegree();
	initializeWorkingTable();
	initializePostAcademicTable();
}


function initializeSubDegree(){
	$('#addMoreSubDegreeEvent > span > i').click(function(){ 
        var markup = "<tr>"+ 
                     "<td><a href='javascript:void(0);' id='removeSubDegreeEvent'"+ 
                     "Title='Remove Entry'><span><i class='fa fa-times'"+ 
                     "aria-hidden='true'></i></span></a></td>"+ 
                     "<td></td>"+ 
                     "<td><input class='form-control form-control-sm' id='newSubDegreeOrg' name='newSubDegreeOrg' placeholder='Organization Name' type='text' /></td>"+ 
                     "<td><input class='form-control form-control-sm' id='newSubDegreeStartDate' name='newSubDegreeStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+ 
                     "<td><input class='form-control form-control-sm' id='newSubDegreeEndDate' name='newSubDegreeEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+ 
                     "<td><input class='form-control form-control-sm' id='newSubDegreeDuty' name='newSubDegreeDuty' placeholder='duty' type='text' /></td>"+ 
                     "</tr>"; 
        $("#tableSubDegree > tbody ").append(markup); 
        $('#tableSubDegree #newSubDegreeStartDate').datepicker();     
        $('#tableSubDegree #newSubDegreeEndDate').datepicker();     
    }); 

    $('#tableSubDegree').on('click','#removeSubDegreeEvent > span > i',function(){ 
        $(this).parent().parent().parent().parent().remove(); 
    }); 
}

function initializePostAcademicTable(){
	$('#addMorePostAcademic > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removePostAcademic'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicCN' name='newAcademicCN' placeholder='Company Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicCity' name='newAcademicCity' placeholder='city' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicState' name='newAcademicState' placeholder='state' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicStartDate' name='newAcademicStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicEndDate' name='newAcademicEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td>"+selectOption("")+"</td>"+
					 "<td><input class='form-control form-control-sm' id='newAcademicDegree' name='newAcademicDegree' placeholder='duty' type='text' /></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tablePostAcadimic > tbody ").append(markup);
		$('#tablePostAcadimic #newAcademicStartDate').datepicker();	
		$('#tablePostAcadimic #newAcademicEndDate').datepicker();	
	});
	
	$('#tablePostAcadimic').on('click','#removePostAcademic > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}

function initializeWorkingTable(){
	$('#addMoreWorkingExp > span > i').click(function(){
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeWorkingExp'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingCN' name='newWorkingCN' placeholder='Company Name' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingCity' name='newWorkingCity' placeholder='city' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingState' name='newWorkingState' placeholder='state' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingStartDate' name='newWorkingStartDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingEndDate' name='newWorkingEndDate' placeholder='MM/DD/YYYY' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingType' name='newWorkingType' placeholder='job type' type='text' /></td>"+
					 "<td><input class='form-control form-control-sm' id='newWorkingDuty' name='newWorkingDuty' placeholder='duty' type='text' /></td>"+
					 "</tr>";
//		console.log(markup);
		$("#tableWorkingExp > tbody ").append(markup);
		$('#tableWorkingExp #newWorkingStartDate').datepicker();	
		$('#tableWorkingExp #newWorkingEndDate').datepicker();	
	});
	
	$('#tableWorkingExp').on('click','#removeWorkingExp > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
}


function parseTables(){
	parseSubDegree();
	parseWorkingExp();
	parsePostAcademic();
}

function parseSubDegree(){
	var sub_degrees = JSON.parse(sub_degree_list); 
    for (var i in sub_degrees) { 
        var aName = sub_degrees[i].SubDegreeCompany; 
        var aStart = sub_degrees[i].SubDegreeStartDate; 
        var aEnd = sub_degrees[i].SubDegreeEndDate; 
        var aDuty = sub_degrees[i].SubDegreeDuty; 
        var markup = "<tr>"+ 
        "<td><a href='javascript:void(0);' id='removeSubDegreeEvent'"+ 
        "Title='Remove Entry'><span><i class='fa fa-times'"+ 
        "aria-hidden='true'></i></span></a></td>"+ 
        "<td></td>"+ 
        "<td><input class='form-control form-control-sm' id='newSubDegreeOrg' name='newSubDegreeOrg' placeholder='Organization Name' type='text' value='"+aName+"'/></td>"+ 
        "<td><input class='form-control form-control-sm' id='newSubDegreeStartDate' name='newSubDegreeStartDate' placeholder='MM/DD/YYYY' type='text' value='"+aStart+"'/></td>"+
        "<td><input class='form-control form-control-sm' id='newSubDegreeEndDate' name='newSubDegreeEndDate' placeholder='MM/DD/YYYY' type='text' value='"+aEnd+"'/></td>"+ 
        "<td><input class='form-control form-control-sm' id='newSubDegreeDuty' name='newSubDegreeDuty' placeholder='duty' type='text' value='"+aDuty+"'/></td>"+ 
        "</tr>"; 
        $("#tableSubDegree > tbody ").append(markup); 
        $('#tableSubDegree #newSubDegreeStartDate').datepicker();     
        $('#tableSubDegree #newSubDegreeEndDate').datepicker();     

    } 
}

function parseWorkingExp(){
	var interns = JSON.parse(working_exp_list);
	for (var i in interns) {
	    var aName = interns[i].jobCompany;
	    var aCity = interns[i].jobCity;
	    var aState = interns[i].jobState;
	    var aStart = interns[i].jobStartDate;
	    var aEnd = interns[i].jobEndDate;
	    var aType = interns[i].jobType;
	    var aDuty = interns[i].jobDuty;
	    
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removeWorkingExp'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingCN' name='newWorkingCN' placeholder='Company Name' type='text' value='"+aName+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingCity' name='newWorkingCity' placeholder='city' type='text' value='"+aCity+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingState' name='newWorkingState' placeholder='state' type='text' value='"+aState+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingStartDate' name='newWorkingStartDate' placeholder='MM/DD/YYYY' type='text' value='"+aStart+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingEndDate' name='newWorkingEndDate' placeholder='MM/DD/YYYY' type='text' value='"+aEnd+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingType' name='newWorkingType' placeholder='job type' type='text' value='"+aType+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newWorkingDuty' name='newWorkingDuty' placeholder='duty' type='text' value='"+aDuty+"'/></td>"+
		 "</tr>";
	    $("#tableWorkingExp > tbody ").append(markup);
		$('#tableWorkingExp #newWorkingStartDate').datepicker();	
		$('#tableWorkingExp #newWorkingEndDate').datepicker();	
	}
}


function parsePostAcademic(){
	var interns = JSON.parse(post_academic_list);
	for (var i in interns) {
	    var aName = interns[i].AcademicCompany;
	    var aCity = interns[i].AcademicCity;
	    var aState = interns[i].AcademicState;
	    var aStart = interns[i].AcademicStartDate;
	    var aEnd = interns[i].AcademicEndDate;
	    var aType = interns[i].AcademicType;
	    var aDegree = interns[i].AcademicDegree;
	    
	    var markup = "<tr>"+
		 "<td><a href='javascript:void(0);' id='removePostAcademic'"+
		 "Title='Remove Entry'><span><i class='fa fa-times'"+
		 "aria-hidden='true'></i></span></a></td>"+
		 "<td></td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicCN' name='newAcademicCN' placeholder='Company Name' type='text' value='"+aName+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicCity' name='newAcademicCity' placeholder='city' type='text' value='"+aCity+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicState' name='newAcademicState' placeholder='state' type='text' value='"+aState+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicStartDate' name='newAcademicStartDate' placeholder='MM/DD/YYYY' type='text' value='"+aStart+"'/></td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicEndDate' name='newAcademicEndDate' placeholder='MM/DD/YYYY' type='text' value='"+aEnd+"'/></td>"+
		 "<td>"+selectOption(aType)+"</td>"+
		 "<td><input class='form-control form-control-sm' id='newAcademicDegree' name='newAcademicDegree' placeholder='duty' type='text' value='"+aDegree+"'/></td>"+
		 "</tr>";
	    $("#tablePostAcadimic > tbody ").append(markup);
		$('#tablePostAcadimic #newAcademicStartDate').datepicker();	
		$('#tablePostAcadimic #newAcademicEndDate').datepicker();	
	}
}



function submitPostGraduationForm(){
	var SubDegreeList=handleSubDegree();
	$('#SubDegreeList').val(SubDegreeList);
	
	var WorkingExpListStr=handleWorkingExp();
	$('#WorkingExpList').val(WorkingExpListStr);
	
	var PostAcademicListStr=handlePostAcademic();
	$('#PostAcademicList').val(PostAcademicListStr);
//	alert(WorkingExpListStr);
	$("#PostActiveForm").submit();
}


function handleSubDegree(){
	var activitiesList="["; 
    var i = 0; 
    $("#tableSubDegree > tbody > tr").each(function(index){ 
        var aName = $(this).find("#newSubDegreeOrg").val(); 
        var aStart = $(this).find("#newSubDegreeStartDate").val(); 
        var aEnd = $(this).find("#newSubDegreeEndDate").val(); 
        var aDuty = $(this).find("#newSubDegreeDuty").val(); 
        if(aName!=""){ 
            i++; 
            activitiesList+="{\"SubDegreeCompany\":\""+aName+"\"," + 
                            " \"SubDegreeStartDate\":\""+aStart+"\"," + 
                            " \"SubDegreeEndDate\":\""+aEnd+"\"," + 
                            " \"SubDegreeDuty\":\""+aDuty+"\"},"; 
        } 
    }); 
    if(i!=0){ 
        activitiesList = activitiesList.slice(0, -1)+"]"; 

    }else{ 
        activitiesList = "[]"; 

    } 
    return activitiesList; 
}

function handleWorkingExp(){
	var activitiesList="[";
	var i = 0;
	$("#tableWorkingExp > tbody > tr").each(function(index){
		
		var aName = $(this).find("#newWorkingCN").val();
	    var aCity = $(this).find("#newWorkingCity").val();
	    var aState = $(this).find("#newWorkingState").val();
	    var aStart = $(this).find("#newWorkingStartDate").val();
	    var aEnd = $(this).find("#newWorkingEndDate").val();
	    var aType = $(this).find("#newWorkingType").val();
	    var aDuty = $(this).find("#newWorkingDuty").val();
	    
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

function handlePostAcademic(){
	var activitiesList="[";
	var i = 0;
	$("#tablePostAcadimic > tbody > tr").each(function(index){
		
		var aName = $(this).find("#newAcademicCN").val();
	    var aCity = $(this).find("#newAcademicCity").val();
	    var aState = $(this).find("#newAcademicState").val();
	    var aStart = $(this).find("#newAcademicStartDate").val();
	    var aEnd = $(this).find("#newAcademicEndDate").val();
	    var aType = $(this).find("#newAcademicType").val();
	    var aDegree = $(this).find("#newAcademicDegree").val();
	    
		if(aName!=""){
			i++;
			activitiesList+="{\"AcademicCompany\":\""+aName+"\"," +
	        				" \"AcademicCity\":\""+aCity+"\"," +
	        				" \"AcademicState\":\""+aState+"\"," +
	        				" \"AcademicStartDate\":\""+aStart+"\"," +
	        				" \"AcademicEndDate\":\""+aEnd+"\"," +
	        				" \"AcademicType\":\""+aType+"\"," +
					        " \"AcademicDegree\":\""+aDegree+"\"},";
		}
	});
	if(i!=0){
		activitiesList = activitiesList.slice(0, -1)+"]";
	}else{
		activitiesList = "[]";
	}
	return activitiesList;
}


function selectOption(conferencePresentationType){
	switch(conferencePresentationType){
	case 'Associates': 
		return "<select class='form-control form-control-sm' id='newAcademicType' name='newAcademicType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Associates' selected='selected'>Associates</option>"+
		"<option value='Bachelors'>Bachelors</option>"+
		"<option value='Masters'>Masters</option>"+
		"<option value='PhD'>Ph.D.</option>"+
	    "</select>";
	case 'Bachelors':
		return "<select class='form-control form-control-sm' id='newAcademicType' name='newAcademicType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Associates'>Associates</option>"+
		"<option value='Bachelors' selected='selected'>Bachelors</option>"+
		"<option value='Masters'>Masters</option>"+
		"<option value='PhD'>Ph.D.</option>"+
	    "</select>";
	case 'Masters': 
		return "<select class='form-control form-control-sm' id='newAcademicType' name='newAcademicType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Associates'>Associates</option>"+
		"<option value='Bachelors' >Bachelors</option>"+
		"<option value='Masters' selected='selected'>Masters</option>"+
		"<option value='PhD'>Ph.D.</option>"+
	    "</select>";
	case 'PhD': 
		return "<select class='form-control form-control-sm' id='newAcademicType' name='newAcademicType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Associates'>Associates</option>"+
		"<option value='Bachelors' >Bachelors</option>"+
		"<option value='Masters'>Masters</option>"+
		"<option value='PhD' selected='selected'>Ph.D.</option>"+
	    "</select>";
	default: 
		return "<select class='form-control form-control-sm' id='newAcademicType' name='newAcademicType'>"+
		"<option value=''>-- select one --</option>"+
		"<option value='Associates'>Associates</option>"+
		"<option value='Bachelors' >Bachelors</option>"+
		"<option value='Masters'>Masters</option>"+
		"<option value='PhD'>Ph.D.</option>"+
	    "</select>";
	}
	
}





