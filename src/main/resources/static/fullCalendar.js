$(document).ready(function() {
   		$("#calendar").fullCalendar({
   			defaultView : 'month',
   			header : {
   				left : '',
   				center : 'title',
   				right : 'today prev,next'
   			},
   			events : {
   				url : '/getEvents',
   				data : function() {
   					return {
   						course : $("#select_course").val(),
   						teacher : $("#select_teacher").val(),
   						group : $("#select_group").val(),
   						classroom : $("#select_classroom").val(),
   						dateFrom : $("#dateFrom").val(),
   						dateTo : $("#dateTo").val()
   					};
   				},
   				type : 'GET'
   			},
   			eventClick : function(event) {
            			    window.location = ('/getLesson/' + event.id)
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