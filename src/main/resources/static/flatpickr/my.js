
$(document).ready(function() {

    ////////////////////// tournament editor settings ///////////////////////////////
    // create datepicker for tournament begin
    console.log("my test");
    var beginDatepicker = $("#begin").flatpickr({
        weekNumbers: true,
        clickOpens: true,
        time_24hr: true,
        dateFormat: "Y-m-dTH:i",
        altInput: true,
        altFormat: "d.m.Y, H:i",
        enableTime: true,
        // locale: "fi",
    });

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,basicWeek,basicDay'
        },
        defaultDate: '2018-03-12',
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: [
            {
                title: 'All Day Event',
                start: '2018-03-01'
            },
            {
                title: 'Long Event',
                start: '2018-03-07',
                end: '2018-03-10'
            },
        ]
    });
});


