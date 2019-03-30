(function () {
    "use strict";

    //////////////////////////////// --- DateTools begins --- /////////////////////////////////

    function DateTools() {
        this.yearAndMonthEqual = function (date1, date2) {
            let d1 = date1 instanceof Date ? date1 : new Date(date1);
            let d2 = date2 instanceof Date ? date2 : new Date(date2);
            return d1.getFullYear() === d2.getFullYear() && d1.getMonth() === d2.getMonth();
        };

        this.getDateString = function (date) {
            return date.toISOString().split("T")[0];
        };
    }

    //////////////////////////////// --- DateTools ends --- /////////////////////////////////

    const dateTools = new DateTools();
    Object.freeze(dateTools);

    //////////////////////////////// --- EventContainer begins --- /////////////////////////////////

    /**
     * Container for calendar events
     * @constructor
     */
    function EventContainer() {

        const allEvents = new Map();

        /**
         * @param event (calendar event)
         * @returns {*}
         */
        function getEventId(event) {
            let href = event._links.self.href;
            let re = /.*\/(\d+)$/;
            return href.match(re)[0];
        }

        /**
         * @param {Date} date
         */
        this.hasEventOnDate = function (date) {
            let iso1 = date.toISOString().split("T")[0];
            this.exists(ev => {
                let iso2 = ev.start.split("T")[0];
                return iso1 === iso2;
            });
        };

        /**
         * get all events in the given date
         * @param Date date
         */
        this.getEventsByDate = function (date) {
            let iso = dateTools.getDateString(date);
            let events = [];
            let keys = allEvents.keys();

            for (let key of keys) {
                let ev = allEvents.get(key);
                let start = ev.start.split("T")[0];
                if (start === iso) {
                    events.push(ev);
                }
            }

            events.sort((a, b) => a.start.localeCompare(b.start));

            return events;
        };

        /**
         * Test does the container have an event that satisfies the predicate
         * @param {function}  (function: <calendar event> => boolean )
         */
        this.exists = function (predicate) {
            let events = allEvents.values();
            for (let ev of events) {
                if (predicate(ev)) {
                    return true;
                }
            }

            return false;
        };

        /**
         * filter the events that satisfy the given predicate
         * @param predicate
         * @returns {any[]}
         */
        this.filter = function (predicate) {
            let events = Array.from(allEvents.values());
            return events.filter(predicate);
        };

        /**
         * Date date first month data, e.g. 2019-03-01
         * Object calendarEvent as return by the server // see the REST
         */
        this.add = function (event) {
            let id = getEventId(event);
            return allEvents.set(id, event);
        };

        /**
         * delete event by the id
         * @param event
         */
        this.delete = function (event) {
            let id = getEventId(event);
            return allEvents.delete(id);
        };

        /**
         * Has the container this event
         * @param Object event (REST calendarEvent)
         * @returns {boolean}
         */

        this.has = function (event) {
            return allEvents.has(getEventId(event));
        };

        /**
         * number of keys
         * @returns {number}
         */
        this.size = function () {
            return allEvents.size;
        };

    }

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

        /**
         * @param {Date} date
         * @returns {boolean}
         */
        function containerHasMonth(date) {
            return eventContainer.exists(event => {
                return dateTools.yearAndMonthEqual(event.start, date);
            });
        }

        // fetch events from the server only if needed -- note that
        // the plan is to keep the container always up-to-date

        if (!containerHasMonth(date)) {
            $.getJSON({
                url: "/calendarevents/search/findAllByStartBetweenOrderByStartAsc",
                data: {start: start, end: end},
                success: function (data) {
                    let events = data._embedded.calendarevents;
                    events.forEach(function (item) {
                        eventContainer.add(item);
                    });
                }
            });
        }
    }

    /**
     * creates a calendar logic and initializes the start date and the modal
     * dialog datetime input to be sended
     *
     * @param Date date, by default current local date
     * @constructor
     */
    function Calendar(date = false) {

        const today = date === false ? new Date() : date;
        // container (cache) for fetched calendar events
        const eventContainer = new EventContainer();

        let selectedDayElem;

        Object.freeze(today);
        // this date reflects the clicked day
        let currentDate = copy(today);


        /**
         * Update the selected date elem and the table
         * @param event (ES event)
         */
        const updateSelectedDayData = function (event) {

            /**
             * clear selected day data
             */
            function clearEventTable() {
                $(".eventRow").each(function () {
                    $(this).children("td[title]").html("");
                    $(this).children("td[start]").html("");
                });
            }

            function addDayEvents() {
                let dayIndex = event.srcElement.dateindex;
                let date = copy(currentDate);
                date.setDate(dayIndex);
                let events = eventContainer.getEventsByDate(date);
                $(".eventRow").each(function (index) {
                    if (index < events.length) {
                        $(this).children("td[title]").html(events[index].title);
                        let d = new Date(events[index].start);
                        let formattedDate = d.getDate() + "." + d.getMonth() + "." + d.getFullYear() + ", " + d.getHours() + ":" + d.getMinutes();
                        $(this).children("td[start]").html(formattedDate);
                    }
                });
            }

            // update the old and new selected day element

            if (selectedDayElem) {
                selectedDayElem.style.backgroundColor = "";
            }
            selectedDayElem = event.srcElement;
            selectedDayElem.style.backgroundColor = "lightgray";

            clearEventTable();
            addDayEvents();
        };

        function ungraySelectedDay() {
            if (typeof selectedDayElem !== "undefined") {
                selectedDayElem.style.backgroundColor = "";
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
         * @param {Date} date
         * @returns {Date}
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

        function setDefaultSelectedDay() {
            ungraySelectedDay();
            let days = getMonthDateElements(currentDate);
            days[0].style.backgroundColor = "lightgray";
            selectedDayElem = days[0];
        }


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

        function addEventListeners() {

            // remove old click listeners
            let allDays = document.getElementsByClassName("day");
            for (let i = 0; i < allDays.length; i++) {
                allDays[i].removeEventListener("click", updateSelectedDayData, true);
                allDays[i].style.cursor = "";
                allDays[i].dateindex = undefined;
            }

            // set current click listeners
            let monthDays = getMonthDateElements(currentDate);
            for (let i = 0; i < monthDays.length; i++) {
                monthDays[i].addEventListener("click", updateSelectedDayData, true);
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
            let newMonth = currentDate.getMonth() + offset;
            currentDate.setMonth(newMonth);
            setMonthDates(currentDate);
            grayOutIrrelevantDays(currentDate);
            setDefaultSelectedDay();
            // addEvent(new Date());
            decorateToday(today, currentDate);
            document.getElementById("month").innerText = monthName(currentDate);
            document.getElementById("year").innerText = currentDate.getFullYear();
            addEventListeners();
            fetchMonthEvents(currentDate, eventContainer);

            /**
             * set an event decoration (red underline)
             * @param Date date
             */
            function addRedEventMark(date) {
                let days = getMonthDateElements(date);
                let dateTools = new DateTools();
                let monthEvents = eventContainer.filter(ev => {
                    return dateTools.yearAndMonthEqual(ev.start, date);
                });

                monthEvents.forEach(function (ev) {

                })

                let day = days[date.getDate() - 1];
                day.style.borderBottom = "solid 3px red";
            }


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

            $("#createbutton").on("click", function () {

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

            $("#updatebutton").on("click", function () {
                console.log("update");
            });

            $("#deletebutton").on("click", function () {
            });

            $("#updatebutton").on("click", function () {
            });

            // open the dialog for editing
            document.getElementById("addEvent").addEventListener("click", function () {
                let dateElem = document.getElementById("date");
                let date = copy(currentDate);
                date.setDate(selectedDayElem.dateindex);
                let dateValue = "" + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
                dateElem.value = dateValue;
                $("#modalEventEditor").modal("show");

                let dateTimeElem = document.getElementById("datetime");
                dateTimeElem.value = date.toISOString();
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