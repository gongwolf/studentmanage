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
		
	});
});