


$(document).ready(function() {

    /////////////////// tools ////////////////////////////////////////////////////
    /**
     * get date in YYYY-MM-DD
     * @returns {string}
     */
    function currentDateAsString() {

        let date = new Date();
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        let d = date.getDate();

        return String(y) + "-" + String(m) + "-" + String(d);

    }

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
        defaultDate: currentDateAsString(),
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
        events:
             function(start, end, timezone, callback) {
                 $.ajax({
                     url: '/calendarevents/search/findAllByStartBetween',
                     dataType: 'json',
                     data: { // parameters for url
                         start: '2019-01-01 00:00:00',
                         end: '2019-12-31 00:00:00'
                     },
                     success: function(doc) {
                         var events = [];
                         $(doc).find('calendarevents').each(function() {
                             events.push({
                                 title: $(this).attr('title'),
                                 start: $(this).attr('start') // will be parsed
                             });
                         });
                         callback(events);
                     },
                     error: function (jqXHR, textStatus, errorThrown) {
                         console.log("error message >>> : " + textStatus + " #################### " + errorThrown);
                     }

                 });
             }

            // let start0 = start;//"2018-12-01 00:00:00.000000"//start.format("YYYY-MM-DD 00:00:00.000000");
            // let start1 = end; //"2018-12-31 00:00:00.000000"//end.format("YYYY-MM-DD 00:00:00.000000");

        // }
        /*

*/


            // [
            // {
            // id:123,
            // title: 'My Header',
            // start: '2018-09-07T10:00:00',
            // allDay: false,
            // end: '2018-09-07T11:00:00',
            // },
            // ]

    });

});