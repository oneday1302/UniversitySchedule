$(function() {
	$("#datepicker").datepicker({
		autoclose: true,
		todayHighlight: true,
	});

	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

	$("#date_from").datepicker({
		autoclose: true,
		todayHighlight: true,
	}).datepicker("setDate", firstDay);

	$("#date_to").datepicker({
		autoclose: true,
		todayHighlight: true,
	}).datepicker("setDate", lastDay);;
});