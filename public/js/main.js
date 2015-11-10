 $(function() {
 	$(".alert>.close").click(function() {
 		$(this).parent().remove();
 	})
 	/***Loading..***/
 	$(document).ajaxStart(function() {
 		$('#mask').show();
 	});

 	$(document).ajaxStop(function() {
 		$('#mask').hide();
 	});
 })
