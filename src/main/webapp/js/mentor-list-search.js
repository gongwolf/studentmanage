/**
 * 
 */

//moment.tz.add("America/Denver|MST MDT MWT MPT|70 60 60 60|01010101023010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010|-261r0 1nX0 11B0 1nX0 11B0 1qL0 WN0 mn0 Ord0 8x20 ix0 LCN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1fz0 1cN0 1cL0 1cN0 1cL0 s10 1Vz0 LB0 1BX0 1cN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 1cN0 1fz0 1a10 1fz0 1cN0 1cL0 1cN0 1cL0 1cN0 1cL0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 14p0 1lb0 14p0 1lb0 14p0 1nX0 11B0 1nX0 11B0 1nX0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Rd0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0 Op0 1zb0|26e5");
 
$(document).ready(function(){
	var table = $('#dataTable').DataTable( {
		"aaData": MentorList, 
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
			{"mData":"mentor_id",
				"mRender":function(data, type, full){
				return '<a href="../mentor/profile/'+full.mentor_id+'" id="profile-mentor_id" >'+full.mentor_id+'</a>';}
			}, 
			{"mData":"name"},
			{"mData":"intitution"},
			{"mData":"deportment"},
			{"mData": "",
				"mRender":function(data, type, full){
//					var str = '<a href="../mentor/mentor-student-list/'+full.mentor_id+'" id="MentorStuList btn" class="btn btn-outline-secondary">Student List</a>';
					var str = '<a href="/studentmanage/home/student-all-list" id="MentorStuList btn" class="btn btn-outline-secondary">Student List</a>';
//					str+='<a href="../student/postAMPActivities/'+full.user_id+'" id="postAMPActivities-btn" class="btn btn-outline-secondary">Post Activities</a>';
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