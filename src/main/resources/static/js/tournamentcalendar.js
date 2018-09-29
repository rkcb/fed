
$(document).ready(function() {
    ////////////////////// tournament editor settings ///////////////////////////////
    // create datepicker for tournament begin
    var beginDatepicker = $("#start").flatpickr({
        weekNumbers: true,
        clickOpens: true,
        time_24hr: true,
        dateFormat: "Y-m-dTH:i",
        altInput: true,
        altFormat: "d.m.Y, H:i",
        enableTime: true,
        locale: "fi",
    });

    $('#closebutton').click(function () {
        $('#tournamenteditor').css('display', 'none');
    });

    // set ESC down event close the tournament editor iff it is open
    // set Enter to choose the current date value
    window.addEventListener('keydown', function (event) {

        if (event.key === 'Enter'){
            let beginDatepickerVisible = $('div.flatpickr-calendar').css('display') === 'block';

            if (beginDatepickerVisible){
                beginDatepicker.close();
            }

        }
        if (event.key === 'Escape') {
            let visible = $('#tournamenteditor').css('display') === 'block';

            if (visible) {
                $('#tournamenteditor').css('display', 'none');
            }
        }
    }, true);
    /////////////////////////////////////////////////////

    // send tournament data as json
    $("form").submit(function (event) {
        // collects only named fields and checkbox only if checked
        event.preventDefault();
        let formData = $("form#tournamentdata").serializeArray();
        let data = new Object();

        for (let i = 0; i < formData.length; i++ ){
            data[formData[i]['name']] = formData[i]['value'];
        }
        $.ajax({
            type: "POST",
            url: "/calendarevents",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        });
    });

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'listDay,listWeek,month'
        },

        // create a new event
        dayClick: function(date, jsEvent, view) {
            $('#updatebutton').css('display', 'none');
            $('#deletebutton').css('display', 'none');
            $('#createbutton').css('display', 'inline-block');

            $('#tournamenteditor').css('display', 'block');

            // initialize the datetime value of the datepicker
            // beginDatepicker.setDate(date.format() + 'T11:00');

        },

        // modify, copy or delete an event
        eventClick: function(calEvent, jsEvent, view) {

            $('#updatebutton').css('display', 'inline-block');
            $('#deletebutton').css('display', 'inline-block');
            // you can copy an event with this
            $('#createbutton').css('display', 'inline-block');
            $('#tournamenteditor').css('display', 'block');
            beginDatepicker.setDate(calEvent.start.format());
        },


            // customize the button names,
        // otherwise they'd all just say "list"
        views: {
            listDay: { buttonText: 'list day' },
            listWeek: { buttonText: 'list week' }
        },

        defaultView: 'month',
        defaultDate: '2018-09-06',
        navLinks: true, // can click day/week names to navigate views
        select: function(start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
        },
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events: [
            {
                id:123,
                title: 'My Header',
                start: '2018-09-07T10:00:00',
                allDay: false,
                end: '2018-09-07T11:00:00',
            },
        ]
    });

});