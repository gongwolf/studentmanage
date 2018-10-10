/**
 * 
 */

jQuery(document).ready(function($) {
	$('#birthDate').datepicker();	
	$('#tableActitvities .form-control.form-control-sm.newActitvity').datepicker();	
	
	$('#div_disa_type').hide();
	$('#Disability').change(function() {
	    var val = $(this).val();
	    if(val==2){
	    	$('#div_disa_type').show();
	    }else{
	    	$('#div_disa_type').hide();
	    }
	  });
	
	
	$('#addMoreActivities > span > i').click(function(){
//		alert("click add activity button");
		var markup = "<tr>"+
					 "<td><a href='javascript:void(0);' id='removeActivity'"+
					 "Title='Remove Entry'><span><i class='fa fa-times'"+
					 "aria-hidden='true'></i></span></a></td>"+
					 "<td></td>"+
					 "<td></td>"+
					 "<td>"+
					 "<input class='form-control form-control-sm newActitvity'"+
					 "name='newActitvity' placeholder='MM/DD/YYYY' type='text' />"+
					 "</td>"+
					 "<td></td>"
					 +"</tr>";
		$("#tableActitvities > tbody ").append(markup);
		$('#tableActitvities .form-control.form-control-sm.newActitvity').datepicker();	
	});
	
	$('#tableActitvities').on('click','#removeActivity > span > i',function(){
//		alert("click remove activity button");
		$(this).parent().parent().parent().parent().remove();
	});
});

function submitProfileForm()
{
	alert('submit');
}



