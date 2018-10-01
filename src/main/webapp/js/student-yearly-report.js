$(document).ready(
		function() {
			$('#Graduated_Degree_label').hide();
			$('#Graduated_Degree').hide();
			$('#Graduated_Field_label').hide();
			$('#Graduated_Field').hide();

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

			$('input[name=Transfered]').click(function() {
				var Transfered_val = $('input[name=Transfered]:checked').val();
				if (Transfered_val == "Transfered_Y") {
					$('#transfer_info').show();
				} else {
					$('#transfer_info').hide();

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