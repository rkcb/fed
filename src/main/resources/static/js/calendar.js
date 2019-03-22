

(function () {
    "use strict"

    //////////////////////////////// --- EventContainer begins --- /////////////////////////////////

    /**
     * Container for calendar events
     * @constructor
     */
    function EventContainer() {

        /**
         * @param Date date
         */
        function getDayOneIsoDateString(date) {
            return getFirstDayOfMonth(date).toISOString().split("T")[0];
        }

        /**
         * @param Date date
         */
        function getDateString(date) {
            return date.toISOString().split("T")[0];
        }

        const allEvents = new Map();

        function getEventId(event){

        }
        /**
         * Date date first month data, e.g. 2019-03-01
         * Object event as return by the server // see the REST
         */
        this.add = function (event) {
        }

        /**
         * delete event by the id
         * @param event
         */
        this.delete = function(event){
            // let eventId =
        }

        /**
         * Contains the date as key
         * @param date
         * @returns {boolean}
         */
        this.contains = function (date) {
            return allEvents.has(getDayOneIsoDateString(date));
        }

        /**
         * number of keys
         * @returns {number}
         */
        this.size = function () {
            return allEvents.size;
        };

    };

    ////////////////////////////  --- EventContainer ends ---  /////////////////////////////////////

    function copy(date) {
        return new Date(date.getTime());
    }

    /**
     * @param Date date
     */
    function getFirstDayOfMonth(date) {
        return new Date(date.getFullYear(), date.getMonth(), 1);
    }

    function getLastDayOfMonth(date) {
        let end = copy(date);
        end.setMonth(date.getMonth() + 1);
        end.setDate(0);
        return end;
    }

    /**
     * Fetch all calendar events of the date
     * @param Date date
     * @param EventContainer eventContainer
     */
    function fetchMonthEvents(date, eventContainer) {

        let start = getFirstDayOfMonth(date).toISOString();
        let end = getLastDayOfMonth(date).toISOString();

        let pre = start.split("T")[0];
        let pre2 = end.split("T")[0];

        start = pre + " 00:00:00";
        end = pre2 + " 23:59:59";


        // this loads all specified events without paging
        $.getJSON({
            url: "/calendarevents/search/findAllByStartBetweenOrderByStartAsc",
            data: { start: start, end: end },
            success: function (data) {
                // let events = data._embedded.calendarevents;
                // eventContainer.ad

            }
        });



    }

    /**
     * creates a calendar logic and initializes the start date and modal
     * dialog datetime input
     *
     * @param Date date, by default current local date
     * @constructor
     */
    function Calendar(date = false) {

        const today = date === false ? new Date() : date;
        const eventContainer = new EventContainer();
        let selectedDay = undefined;

        Object.freeze(today);
        let currentDate = copy(today); // this date will change with navigation

        const showDayDialog = function (event) {
            let dateElem = document.getElementById("date");
            let date = copy(currentDate);
            date.setDate(event.srcElement.dateindex);
            let dateValue = "" + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
            dateElem.value = dateValue;
            $("#modalEventEditor").modal("show");

            let dateTimeElem = document.getElementById("datetime");
            dateTimeElem.value = date.toISOString();
        };

        const setSelectedDay = function (event) {
            if (selectedDay){
                selectedDay.style.backgroundColor = "";
            }
            selectedDay = event.srcElement;
            selectedDay.style.backgroundColor = "lightgray";
        }

        function unsetSelectedDay() {
            if (selectedDay){
                selectedDay.style.backgroundColor = "";
            }
        }

        /**
         * called after an hours or minutes change
         */
        function updateDatetimeValue() {

            let elem = document.getElementById("datetime");
            let date = new Date(elem.value);

            let hours = document.getElementById("hours").value;
            let minutes = document.getElementById("minutes").value;

            date.setHours(hours);
            date.setMinutes(minutes);

            elem.value = date.toISOString();
        }

        /**
         * return a Date which points to the day 1
         * @param Date date
         * @returns Date
         */
        function getFirstCalendarDate(date) {
            let date2 = copy(date);
            date2.setDate(1);
            date2.setHours(-24 * ((date2.getDay() + 6) % 7));
            return date2;
        }


        /**
         * adds the date numbers to table cells
         */
        function setMonthDates(date) {
            let now = getFirstCalendarDate(date);
            let days = document.getElementsByClassName("day");
            for (let i = 0; i < days.length; i++) {
                days[i].innerHTML = now.getDate().toString();
                now.setHours(24); // increases current date by 24h
            }
        }

        function grayOutIrrelevantDays(date) {

            let days = document.getElementsByClassName("day");

            for (let i = 0; i < days.length; i++) {
                days[i].style.color = "lightgray";
            }

            days = getMonthDateElements(date);
            for (let i = 0; i < days.length; i++) {
                days[i].style.color = "black";
            }
        }

        /**
         * number of the previous month days
         * @param date
         * @returns {number}
         */
        function daysBeforeFirst(date) {
            let date2 = copy(date);
            date2.setDate(1);
            // number of days before the day one
            return (6 + date2.getDay()) % 7;
        }

        /**
         * the number of days in a month
         * @param date
         * @returns {number}
         */
        function getNumberOfDaysInMonth(date) {
            let date2 = copy(date);
            date2.setMonth(date2.getMonth() + 1);
            date2.setDate(0);
            return date2.getDate();
        }

        /**
         * get the month days only
         * @param Date date
         * @returns {Element[]}
         */
        function getMonthDateElements(date) {
            let days = Array.from(document.getElementsByClassName("day"));
            let prefix = daysBeforeFirst(date);
            let daysInMonth = getNumberOfDaysInMonth(date);
            return days.slice(prefix, prefix + daysInMonth);
        }

        /**
         * add an event decoration
         * @param Date date
         */
        // function addEventMark(date) {
        //     let days = getMonthDateElements(date);
        //     let day = days[days.length - 1];
        //     day.closest("td").style.borderBottom = "solid 3px red";
        // }

        /**
         * @param Date currentDate chosen date by the user
         */
        function decorateToday(today, currentDate) {
            // show today only if in correct month
            if (today.getMonth() === currentDate.getMonth() &&
                today.getFullYear() === currentDate.getFullYear()) {
                let days = getMonthDateElements(currentDate);
                let day = days[today.getDate() - 1];
                day.closest("td").style.backgroundColor = "#f8e5ab";
            } else { // remove the existing decoration
                let days = getMonthDateElements(today);
                let day = days[today.getDate() - 1];
                day.closest("td").style.backgroundColor = "";
            }
        }

        function monthName(date) {
            let names = ["Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"];
            return names[date.getMonth()];
        }

        /**
         * update day click listeners
         */
        function addClickListeners() {

            // remove old click listeners
            let allDays = document.getElementsByClassName("day");
            for (let i = 0; i < allDays.length; i++) {
                allDays[i].removeEventListener("click", setSelectedDay, true);
                allDays[i].style.cursor = "";
                allDays[i].dateindex = undefined;
            }

            // add current click listeners
            let monthDays = getMonthDateElements(currentDate);
            for (let i = 0; i < monthDays.length; i++) {
                monthDays[i].addEventListener("click", setSelectedDay, true);
                monthDays[i].style.cursor = "pointer";
                // this value is used to set the date correctly in the modal dialog
                monthDays[i].dateindex = i + 1;
            }

            // update datetime input value when hours or minutes change
            function addChangeListeners() {
                let hoursElem = document.getElementById("hours");
                let minutesElem = document.getElementById("minutes");

                hoursElem.addEventListener("change", updateDatetimeValue, true);
                minutesElem.addEventListener("change", updateDatetimeValue, true);
            }

            addChangeListeners();


        }

        /**
         * update the calendar logic to reflect the selected month
         * @param int offset
         */
        function updateMonth(offset = 0) {
            unsetSelectedDay();
            let newMonth = currentDate.getMonth() + offset;
            currentDate.setMonth(newMonth);
            setMonthDates(currentDate);
            grayOutIrrelevantDays(currentDate);
            // addEvent(new Date());
            decorateToday(today, currentDate);
            document.getElementById("month").innerText = monthName(currentDate);
            document.getElementById("year").innerText = currentDate.getFullYear();
            addClickListeners();
            fetchMonthEvents(currentDate, eventContainer);

            /**
             * create a collection name for uploaded file(s)
             */
            function addUploadFileNames() {
                let uploadElem = document.getElementById("upload");
                uploadElem.addEventListener("change", function (event) {
                    let elem = event.srcElement;
                    let names = [];
                    for (let i = 0; i < elem.files.length; i++) {
                        names.push(elem.files[i].name);
                    }
                    let name = names.reduce((acc, curr) => acc + ", " + curr);

                    document.getElementById("filenames").innerHTML = name;

                });
            }

            addUploadFileNames();
        }

        function createDialogButtonListeners() {

            $("#createbutton").on("click", function (event) {

                event.preventDefault();

                let data = {};

                let inputs = $("#tournamentdata *[name]");

                $(inputs).each(function (index, element) {
                    let name = $(element).prop("name");
                    data[name] = $(element).val();
                });

                $.ajax({
                    type: "POST",
                    url: "/calendarevents",
                    data: JSON.stringify(data),
                    success: function (data, textStatus) {
                        console.log("success: " + textStatus);
                    },
                    dataType: "json",
                    contentType: "application/json",
                });


            });

            $("#updatebutton").on("click", function (event) {
                event.preventDefault();
            });

            $("#deletebutton").on("click", function (event) {
                event.preventDefault();
            });


        }

        document.getElementById("month-prev").addEventListener("click", function () {
            updateMonth(-1);
        });
        document.getElementById("month-next").addEventListener("click", function () {
            updateMonth(1);
        });

        updateMonth();
        createDialogButtonListeners();
    }

    let calendar = new Calendar();
    Object.freeze(calendar);

    document.getElementById("minutes").step = 5;


})();