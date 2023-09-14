$(document).ready(function() {
		$("#calendar").fullCalendar({
			defaultView : 'month',
			header : {
				left : '',
				center : 'title',
				right : 'today prev,next'
			},
			events : {
				url : '/student/getEvents',
				data : function() {
					return {
						courseId : $("#select_course").val(),
						teacherId : $("#select_teacher").val(),
						classroomId : $("#select_classroom").val(),
						dateFrom : $("#dateFrom").val(),
						dateTo : $("#dateTo").val()
					};
				},
				type : 'GET'
			},
			eventClick : function(event) {
				window.location = ('/student/lessonInfo/' + event.id)
			}
		});

		$("#select_course").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#select_teacher").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#select_classroom").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#dateFrom").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#dateTo").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});
});