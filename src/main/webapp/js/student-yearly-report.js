$(document).ready(function(){
	var jsonData =JSON.parse(yearlyBeans);
	for(i in jsonData)
	{
		var val = jsonData[i];
		for(j in val)
		{
			$('#content-wrapper > div').append(j+":"+JSON.stringify(val[j])+"<br>");
		}
		$('#content-wrapper > div').append("<hr>");
	}
});