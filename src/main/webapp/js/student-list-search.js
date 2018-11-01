/**
 * 
 */

//moment.tz.add("America/Denver|MST MDT MWT MPT|70 60 60 60|01010101023010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010|-261r0 1nX0 11B0 1nX0 11B0 1qL0 WN0 mn0 Ord0 8x20 ix0 LCN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1fz0 1cN0 1cL0 1cN0 1cL0 s10 1Vz0 LB0 1BX0 1cN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0|26e5");
 
$(document).ready(function(){
	var table = $('#dataTable').DataTable( {
		"aaData": studentList, 
    	'columnDefs': [
         {
             'targets': 0,
             'searchable':false,
             'orderable':false,
             'className': 'dt-body-center',
             'render': function (data, type, full, meta){
            	 	return meta.row+1; 
            	 }
          }
          /**{
        	  'targets': 1,
              'searchable':false,
              'orderable':false,
              'className': 'dt-body-center',
              'render': function (data, type, full, meta){
              	return '<input class = "checkbox_input" type="checkbox" name="id[]" value="'+full.applicationID+'">';
              }
           }**/
          ], 
	
          "aoColumns": [
			{"mData":""},
			{"mData":"user_id",
				"mRender":function(data, type, full){
				return '<a href="../student/profile/'+full.user_id+'" id="user_id" >'+full.user_id+'</a>';}
			}, 
			{"mData":"first_name"},
			{"mData": "middle_name"},
			{"mData": "last_name"},
			{"mData": "birthDate"},
			{"mData": "",
				"mRender":function(data, type, full){
					if(full.isActive==1){
						return "Grudated";
					}else if(full.isActive==0){
						return "Active";
					}else if(full.isActive==2){
						return "No yearly report";
					}else{
						return "No Info";
					}
				 }
			},
			{"mData": "",
				"mRender":function(data, type, full){
					var str='<a href="/studentmanage/student/profile/'+full.user_id+'" id="studentprofile-btn" class="btn btn-outline-secondary">Profile</a>';
					str+='&nbsp;&nbsp;<a href="/studentmanage/student/yearlyreport/'+full.user_id+'" id="yearlyReport" class="btn btn-outline-secondary">Yearly Report</a>';
					str+='&nbsp;&nbsp;<a href="/studentmanage/student/postAMPActivities/'+full.user_id+'" id="postAMPActivities-btn" class="btn btn-outline-secondary">Post Activities</a>';
					return str;
				 }
			}
          ],
          "paging":true,
          "pageLength":10,
          "ordering":true,
          "order":[2,"asc"]
	}); 
	
	$('#dataTable tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
});