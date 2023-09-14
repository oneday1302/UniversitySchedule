$(document).ready(function() {
		$("#calendar").fullCalendar({
			defaultView : 'month',
			header : {
				left : '',
				center : 'title',
				right : 'today prev,next'
			},
			events : {
				url : '/admin/getEvents',
				data : function() {
					return {
						courseId : $("#select_course").val(),
						teacherId : $("#select_teacher").val(),
						groupId : $("#select_group").val(),
						classroomId : $("#select_classroom").val(),
						dateFrom : $("#dateFrom").val(),
						dateTo : $("#dateTo").val()
					};
				},
				type : 'GET'
			},
			eventClick : function(event) {
				window.location = ('/admin/editLesson/' + event.id)
			},
			dayClick: function() {
				window.location = ('/admin/addLesson')
			}
		});

		$("#select_course").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#select_teacher").change(function() {
			$("#calendar").fullCalendar("refetchEvents");
		});

		$("#select_group").change(function() {
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