(function () {

    "use strict";

    function Misc() {

        this.getLastInt = function (href) {
            let re = /.*\/(\d+)$/;
            return href.match(re)[1];
        };
    }

    /////////////////////////////// --- REST begins --- /////////////////////////////////////////

    /**
     * REST contains all backend functionality
     * @constructor
     */
    function REST() {

        this.calendarEventsURL = "/calendarevents";
        this.tournamentsURL = "/tournaments";
        let restSelf = this;

        /**
         * @param data as an object
         * @param success callback
         * @param error callback
         * data1@param url
         * @param contentType
         */
        this.post = function (data, success, error, url, contentType = "application/json") {
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(data),
                success: success,
                dataType: "json",
                contentType: contentType,
                error: error,
            });
        };

        this.getPostObj = (url, data, success, error) => {
            return {
                type: "POST",
                url: url,
                data: data,
                success: success,
                error: error,
                dataType: "json",
                contentType: "application/json",
            };
        };

        this.get = function (url, success, error) {
            $.ajax({
                type: "GET",
                url: url,
                success: success,
                error: error
            });
        };

        this.getGetObj = (url, success, error) => {
            return {
                type: "GET",
                url: url,
                success: success,
                error: error
            };
        };

        // curl -i -X PUT -H "Content-Type:text/uri-list"
        // -d "http://localhost:8080/tournaments/1"
        // http://localhost:8080/calendarevents/8/tournament

        this.addRelation = function (ownerUrl, payload, success, error) {
            $.ajax({
                type: "PUT",
                url: ownerUrl,
                data: payload,
                success: success,
                contentType: "text/uri-list",
                dataType: "text",
                error: error,
            });
        };

        this.getRelationObj = (url, payload, success, error) => {
            return {
                type: "PUT",
                url: url,
                data: payload,
                success: success,
                error: error,
                contentType: "text/uri-list",
                dataType: "text",
            };
        };

        this.update = (url, data, success, error) => {
            let ajaxObj = this.getPostObj(url, JSON.stringify(data), success, error);
            ajaxObj.type = "PATCH";
            $.ajax(ajaxObj);
        };

        /**
         * @param {String} eventId
         */
        this.delete = function (entityurl, success) {
            $.ajax({
                type: "DELETE",
                url: entityurl, // url to entity to be removed
                success: success,
                error: () => {
                    alert(" delete failed :( ");
                }
            });
        };

        this.getDeleteObj = (url, success, error) => {
            return {
                type: "DELETE",
                url: url,
                success: success,
                error: error,
            };
        };

        /**
         * run given requests sequentially by chaining the Ajax objects
         * @param {Array} ajaxObjects
         * @constructor
         */
        this.run = (ajaxObjects, sharedDataObject, i = 0) => {

            // sharedDataObject is available for all ajax objects

            let selfRun = restSelf.run;

            if (ajaxObjects.length > 0) {
                i++;
                let obj = ajaxObjects[0];
                let objSuccess = obj.success;
                let objError = obj.error;
                obj.success = (data) => {
                    if (objSuccess) {
                        objSuccess(data);
                    }
                    selfRun(ajaxObjects.slice(1), sharedDataObject, i);
                };
                obj.error = (data) => {
                    if (objError) {
                        objError(data);
                    }
                    alert("error");
                };
                if (!obj.url) {
                    alert("i = " + i + " undefined url: " + obj.url);
                } else {
                    $.ajax(obj);
                }
            }
        };


    }

    /////////////////////////////// --- REST ends --- /////////////////////////////////////////

    //////////////////////////////// --- DateTools begins --- /////////////////////////////////R

    /**
     * contains various date related tools
     *
     * @constructor
     */
    function DateTools() {

        this.yearAndMonthEqual = function (date1, date2) {
            let d1 = date1 instanceof Date ? date1 : new Date(date1);
            let d2 = date2 instanceof Date ? date2 : new Date(date2);
            return d1.getFullYear() === d2.getFullYear() && d1.getMonth() === d2.getMonth();
        };

        /**
         * @param {String | Date} date
         */
        this.toISOString = function (date) {
            let iso = date instanceof Date ? date.toISOString() : date;
            return typeof iso === "string" ? iso : undefined;
        };

        /**
         * @param {Date} date
         * @return {string}
         */
        this.getDateString = function (date) {
            let iso = dateTools.toISOString(date);
            return iso.split("T")[0];
        };

        /**
         * get only hours and minutes from ISO date string
         * @param {String} date
         * @return {string}
         */
        this.getTimeString = function (iso) {
            return iso.split("T")[1].substr(0, 5);
        };

        this.getDateTimeRepresentation = function (isoDateString) {

            let [date2, time] = isoDateString.split("T");
            let [year, month, date] = date2.split("-");
            time = time.substr(0, 5);

            return `${date}.${month}.${year}` + ", " + time;
        };

        /**
         * get an ISO date string for the start of the month
         * @param {Date} date
         * @return {string}
         */
        this.getMonthStartString = function (date) {
            let start = dateTools.getFirstDayOfMonth(date).toISOString();
            let pre = start.split("T")[0];
            return pre + " 00:00:00";
        };

        /**
         * get an ISO date string for the end of the month
         * @param {Date} date
         * @return {string}
         */
        this.getMonthEndString = function (date) {
            let end = dateTools.getLastDayOfMonth(date).toISOString();
            let pre = end.split("T")[0];
            return pre + " 23:59:59";
        };

        /**
         * copy given date
         * @param date
         * @return {Date}
         */
        this.copy = function (date) {
            return new Date(date.getTime());
        };

        /**
         * @param Date date
         */
        this.getFirstDayOfMonth = function (date) {
            return new Date(date.getFullYear(), date.getMonth(), 1);
        };

        /**
         * get last day of the month
         * @param {Date} date
         * @return {Date}
         */
        this.getLastDayOfMonth = function (date) {
            let end = dateTools.copy(date);
            end.setMonth(date.getMonth() + 1);
            end.setDate(0);
            return end;
        };

        /**
         * number of the previous month days
         * @param date
         * @returns {number}
         */
        this.daysBeforeFirst = function (date) {
            let date2 = dateTools.copy(date);
            date2.setDate(1);
            // number of days before the day one
            return (6 + date2.getDay()) % 7;
        };

        /**
         * the number of days in a month
         * @param date
         * @returns {number}
         */
        this.getNumberOfDaysInMonth = function (date) {
            let date2 = dateTools.copy(date);
            date2.setMonth(date2.getMonth() + 1);
            date2.setDate(0);
            return date2.getDate();
        };
    }

    //////////////////////////////// --- DateTools ends --- /////////////////////////////////


    //////////////////////////////// --- EventContainer begins --- /////////////////////////////////

    /**
     * Container for calendar events
     * @param events, calendar events as in the REST response
     * @constructor
     */
    function EventContainer(events = []) {

        const allEvents = new Map();

        if (events.length > 0) {
            events.forEach(function (event) {
                allEvents.set(getEventId(event), event);
            });
        }

        /**
         * @param event (calendar event)
         * @returns {*}
         */
        function getEventId(event) {
            let href = event._links.self.href;
            let re = /.*\/(\d+)$/;
            return href.match(re)[1];
        }

        this.getEventId = function (event) {
            let href = event._links.self.href;
            let re = /.*\/(\d+)$/;
            return href.match(re)[1];
        };

        this.getAllAsArray = function () {
            return Array.from(allEvents.values());
        };

        /**
         * @param {String} date
         */
        this.hasEventOnDate = function (date) {

            let iso1 = dateTools.toISOString(date).split("T")[0];
            this.exists(ev => {
                let iso2 = ev.start.split("T")[0];
                return iso1 === iso2;
            });
        };

        /**
         * get all events in the given date
         * @param {Date} date
         * @return {Array}
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
         * @param {String} eventId
         */
        this.get = function (eventId) {
            return allEvents.get(eventId);
        };

        this.getUrl = function (eventId) {
            let event = this.get(eventId);
            return event ? event._links.self.href : undefined;
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
         * @returns {EventContainer}
         */
        this.filter = function (predicate) {
            let events = Array.from(allEvents.values());
            return new EventContainer(events.filter(predicate));
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
         * @param eventId
         */
        this.delete = function (eventId) {
            return allEvents.delete(eventId);
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

        /**
         * apply an operation for each item of the container
         * @param operation, callback of the form function(value, key, map)
         */
        this.forEach = function (operation) {
            allEvents.forEach(operation);
        };

    }

    ////////////////////////////  --- EventContainer ends ---  /////////////////////////////////////

    ////////////////////////////  --- Modal Dialog begins ---  /////////////////////////////////////

    function EventEditor() {

        this.fill = function (eventData, eventId) {
            $('#tournamentdata *[name]').each(function () {
                let name = $(this).prop("name");
                $(this).prop("value", eventData[name]);
            });

            this.setDate(eventData.start);
            this.setTime(eventData.start);

            $("#eventid").prop("value", eventId);
        };

        this.clear = function () {
            $('#tournamentdata *[name]').each(function () {
                $(this).prop("value", "");
                $(this).prop("checked", false);
            });
            $("#eventid").prop("value", "");
        };

        this.show = function () {
            this.hideAlerts();
            $("#modalEventEditor").modal("show");
        };

        this.hide = function () {
            $("#modalEventEditor").modal("hide");
        };

        this.showCreationOk = function () {
            this.hideAlerts();
            document.getElementById("tournament-creation-ok").style.display = "block";
        };

        this.hideAlerts = function () {
            $("#tournamentdata .alert").css("display", "none");
        };

        this.showCreationFailed = function () {
            // TODO: add the notification
            this.hideAlerts();
            document.getElementById("tournament-creation-failed").style.display = "block";
        };

        this.showUpdateOk = () => {
            this.hideAlerts();
            document.getElementById("tournament-update-ok").style.display = "block";
        };

        this.showUpdateFailed = () => {
            this.hideAlerts();
            document.getElementById("tournament-update-failed").style.display = "none";
        };

        this.showDeleteOk = () => {
            this.hideAlerts();
            document.getElementById("tournament-delete-ok").style.display = "block";
        };

        this.showDeleteFailed = () => {
            this.hideAlerts();
            document.getElementById("tournament-delete-failed").style.display = "none";
        };

        this.setDate = function (isoDateString) {
            $("#date").flatpickr({
                clickOpens: true,
                time_24hr: true,
                dateFormat: "Y-m-dT00:00:00",
                altInput: true,
                altFormat: "d.m.Y",
                enableTime: false,
                locale: {
                    "firstDayOfWeek": 1 // start week on Monday
                },
                onValueUpdate: function (selectedDates, dateStr) {
                    document.getElementById("datetime").value = dateStr;
                },
                defaultDate: isoDateString,
            });
        };

        this.setTime = function (isoDateString) {
            $("#time").prop("value", dateTools.getTimeString(isoDateString));
        };

        /**
         * @param {String} id
         */
        this.setId = function (id) {
            $("#eventid").prop("value", id);
        };

        this.unsetId = function () {
            $("#eventid").prop("value", "");
        };

        this.getId = function () {
            return $("#eventid").prop("value");
        };

        this.getFormData = () => {
            let formData = {};
            let inputs = $("#tournamentdata *[name]");
            $(inputs).each(function (index, element) {
                let name = $(element).prop("name");
                formData[name] = $(element).val();
            });

            return formData;
        };
    }

    ////////////////////////////  --- Modal Dialog ends ---  /////////////////////////////////////

    ////////////////////////////  --- Decoration begins ---  /////////////////////////////////////

    function Decoration() {

        this.markEventDay = function (dayElement) {
            dayElement.style.borderBottom = "solid 3px red";
        };

        this.unmarkEventDay = function (dayElement) {
            dayElement.style.borderBottom = "";
        };

    }

    /**
     * Handles filling and clearing the selected day
     * details table
     *
     * @constructor
     */
    function EventTable() {

        let dayElem;
        let eventContainer;
        let eventDate;

        this.setEventContainer = (container) => {
            if (!eventContainer) {
                eventContainer = container;
            }
        };

        this.setCurrentDate = (date) => {
            if (date) {
                eventDate = date;
            }
        };

        this.clear = () => {
            $(".eventRow").each(function () {
                $(this).children("td[title]").html("");
                $(this).children("td[start]").html("");
                $(this).prop("eventid", "");
                $(this).css("cursor", "");
            });
        };

        this.reload = () => {
            this.clear();
            if (dayElem && eventDate) {
                this.fillDetails(dayElem, eventDate);
            } else {
                console.log("dayElem id: " + dayElem.eventid + " eventDate: " + eventDate);
            }
        };

        /**
         * fill the details for events of the day
         * @param event ES click event
         */
        this.fillDetails = (selectedDayElem, currentDay2) => {
            dayElem = selectedDayElem;
            this.setCurrentDate(currentDay2);
            let dayIndex = dayElem.dateindex;
            let date = dateTools.copy(eventDate);
            date.setDate(dayIndex);
            let events = eventContainer.getEventsByDate(date);
            // TODO: if there are events more than five this fails => add paging
            $(".eventRow").each(function (index) {
                $(this).prop("eventid", undefined);
                if (index < events.length) {
                    let event = events[index];
                    $(this).children("td[title]").html(event.title);
                    let formattedDate = dateTools.getDateTimeRepresentation(event.start);
                    $(this).children("td[start]").html(formattedDate);
                    $(this).prop("eventid", eventContainer.getEventId(event));
                    $(this).css("cursor", "pointer");
                }
            });
        };


    }

    ////////////////////////////  --- Decaration ends ---  /////////////////////////////////////


    //////////////////////////////// --- Construction begins --- /////////////////////////////////


    const dateTools = new DateTools();
    Object.freeze(dateTools);

    const rest = new REST();
    Object.freeze(rest);

    const misc = new Misc();
    Object.freeze(misc);

    const eventEditor = new EventEditor();
    Object.freeze(eventEditor);

    const eventTable = new EventTable();
    Object.freeze(eventTable);

    const decoration = new Decoration();
    Object.freeze(decoration);

    //////////////////////////////// --- Construction ends --- /////////////////////////////////


    /**
     * get the month days only
     * @param Date date
     * @returns {Element[]}
     */
    function getMonthDateElements(date) {
        let days = Array.from(document.getElementsByClassName("day"));
        let prefix = dateTools.daysBeforeFirst(date);
        let daysInMonth = dateTools.getNumberOfDaysInMonth(date);
        return days.slice(prefix, prefix + daysInMonth);
    }


    /**
     * Fetch all calendar events of the date
     * @param Date date
     * @param EventContainer eventContainer
     */
    function fetchMonthEventsAndDecorateDays(date, eventContainer) {
        /**
         * @param {Date} date
         * @returns {boolean}
         */
        function containerHasMonth(date) {
            return eventContainer.exists(event => {
                return dateTools.yearAndMonthEqual(event.start, date);
            });
        }

        function removeCurrentEventDecorations() {
            let days = getMonthDateElements(date);
            for (let day of days) {
                day.style.borderBottom = "";
            }
        }

        /**
         * decorate all month event days
         * @param Date date
         */
        function addRedEventMark(date) {
            let days = getMonthDateElements(date);

            let monthEvents = eventContainer.filter(ev => {
                return dateTools.yearAndMonthEqual(ev.start, date);
            });

            function getStartDateIndex(event) {
                let date = new Date(event.start);
                return date.getDate();
            }

            // traverse event dates and mark days
            monthEvents.forEach(function (event) {
                let day = days[getStartDateIndex(event) - 1];
                day.style.borderBottom = "solid 3px red";
            });
        }

        removeCurrentEventDecorations();

        // fetch events from the server only if needed
        // NOTE:
        // the plan is to keep the container always up-to-date

        if (!containerHasMonth(date)) {
            $.getJSON({
                url: "/calendarevents/search/findAllByStartBetweenOrderByStartAsc",
                data: {
                    start: dateTools.getMonthStartString(date),
                    end: dateTools.getMonthEndString(date)
                },
                success: function (data) {
                    let events = data._embedded.calendarevents;
                    events.forEach(function (item) {
                        eventContainer.add(item);
                    });
                    addRedEventMark(date);
                }
            });
        } else {
            addRedEventMark(date);
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

        eventTable.setEventContainer(eventContainer);



        Object.freeze(today);
        // this date reflects the clicked day
        let currentDate = dateTools.copy(today);


        /**
         * Update the selected date elem and the table
         * @param event (ES event)
         */
        const updateSelectedDayData = function (event) {
            // update the old and new selected day element

            if (selectedDayElem) {
                selectedDayElem.style.backgroundColor = "";
            }
            selectedDayElem = event.currentTarget;
            selectedDayElem.style.backgroundColor = "lightgray";

            eventTable.clear();
            eventTable.fillDetails(selectedDayElem, currentDate);
        };

        function ungraySelectedDay() {
            if (typeof selectedDayElem !== "undefined") {
                selectedDayElem.style.backgroundColor = "";
            }
        }

        /**
         * called after the time element value changes
         */
        function updateDatetimeValue() {

            let dateTimeElem = document.getElementById("datetime");

            let dateValue = document.getElementById("date").value;
            let timeValue = document.getElementById("time").value;

            if (dateValue && timeValue) {
                dateTimeElem.value = "" + dateValue + "T" + timeValue;
            }

        }

        /**
         * return a Date which points to the day 1
         * @param {Date} date
         * @returns {Date}
         */
        function getFirstCalendarDate(date) {
            let date2 = dateTools.copy(date);
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
                let timeElem = document.getElementById("time");
                timeElem.addEventListener("change", updateDatetimeValue, true);
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
            decorateToday(today, currentDate);
            document.getElementById("month").innerText = monthName(currentDate);
            document.getElementById("year").innerText = currentDate.getFullYear();
            addEventListeners();
            removeAllEventDayDecorations();
            fetchMonthEventsAndDecorateDays(currentDate, eventContainer);

            function removeAllEventDayDecorations() {
                let days = document.getElementsByClassName("day");
                for (let i = 0; i < days.length; i++) {
                    days[i].style.borderBottom = "";
                }
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

            $("#clearbutton").on("click", function () {
                eventEditor.clear();
            });

            $("#createbutton").on("click", function () {

                let formdata = {};

                let inputs = $("#tournamentdata *[name]");

                $(inputs).each(function (index, element) {
                    let name = $(element).prop("name");
                    formdata[name] = $(element).val();
                });

                formdata = eventEditor.getFormData();

                /**
                 * create tournament and set the relation if success
                 * @param calendarEventData server REST response
                 */
                function calendarEventCreated(calendarEventData) {

                    let calendarEventHref = calendarEventData._links.self.href;
                    let calendarEventTourHref = calendarEventData._links.tournament.href;

                    /**
                     * bind a calendar event and tournament
                     * @param data
                     */
                    function tournamentCreated(tournamentData) {
                        let tournamentHref = tournamentData._links.self.href;
                        let tournamentCalendarEventHref = tournamentData._links.calendarEvent.href;

                        // add calendar event to the container
                        // show notification
                        // mark the day for new event
                        // add event id to the modal dialog
                        function ok() {
                            eventEditor.showCreationOk();
                            eventContainer.add(calendarEventData);
                            let eventDate = new Date(calendarEventData.start);
                            let dayElem = getMonthDateElements(eventDate)[eventDate.getDate() - 1];
                            dayElem.style.borderBottom = "solid 3px red";
                            let helper = new EventContainer();
                            $("#eventid").prop("value", helper.getEventId(calendarEventData));
                            eventTable.clear();
                            eventTable.fillDetails(selectedDayElem, currentDate);
                        }

                        function failed() {
                            alert("bind failed");
                        }

                        // two sided:
                        rest.addRelation(tournamentCalendarEventHref, calendarEventHref, () => {
                        }, failed);
                        rest.addRelation(calendarEventTourHref, tournamentHref, ok, failed);
                    }

                    /**
                     * handle failed binding
                     */
                    function tournamentCreationFailed() {
                        alert("tournament and calendar event relation creation failed");
                    }

                    // 2. create a tournament
                    rest.post({}, tournamentCreated, tournamentCreationFailed, rest.tournamentsURL);

                    // data._links.{self, calendarEvent, tournament}.href
                    // let owner = data._links.calendarEvent.href;
                    // let mapped = data._links.tournament.href;
                    // let tourId =
                    // rest.addRelation(owner, mapped)

                }

                function calendarEventCreationFailed() {
                    alert("calendar event creation failed");
                }

                // 1. create a calendar event
                // 2. create a tournament
                // 3. add the entities to 1-1 relation
                // TODO: handle errors if any step fails

                // create a calendar event
                rest.post(formdata, calendarEventCreated, calendarEventCreationFailed, rest.calendarEventsURL);
            });

            $("#updatebutton").on("click", function () {
                const id = eventEditor.getId();
                if (id) {

                    let formData = eventEditor.getFormData();
                    let event = eventContainer.get(id);

                    if (event) {
                        rest.update(event._links.self.href, formData,
                            (newEventData) => {
                                eventContainer.delete(id);
                                eventContainer.add(newEventData);
                                eventEditor.showUpdateOk();
                                eventTable.reload();
                            },
                            eventEditor.showUpdateFailed);
                    }
                }
            });

            $("#deletebutton").on("click", function () {
                // TODO   1. remove event from eventContainer
                // TODO   2. remove decorations only if there is only one event in the day
                // TODO   3. remove CalendarEvent and Tournament bindings
                // TODO   4. remove previous entities
                //        5. remove PBN files?
                //        6. if PBN files are removed also possible master points should be removed
                //        7. if 5-6 are done create a log message who removed PBN files
                //        8. probably it is best to leave PBN and MP handling to own view

                // delete currently both calendarEvent and tournament
                // TODO: add master point removal if needed and when possible

                const id = eventEditor.getId();

                if (!id){
                    return;
                }


                const event = eventContainer.get(id);
                const eventSelfUrl = event._links.self.href;
                const eventTourUrl = event._links.tournament.href;


                rest.get(event._links.tournament.href, (tourData) => {

                    const tourSelfUrl = tourData._links.self.href;
                    const tourEventUrl = tourData._links.calendarEvent.href;

                    rest.delete(eventTourUrl, () => {
                        rest.delete(tourEventUrl, () => {
                            rest.delete(eventSelfUrl, () => {
                                rest.delete(tourSelfUrl, () => {
                                    let id = eventEditor.getId();
                                    let event = eventContainer.get(id);
                                    let count = eventContainer.getEventsByDate(event.start).length;

                                    eventEditor.showDeleteOk();

                                    eventTable.clear();
                                    if (count < 2) {
                                        eventContainer.delete(id);
                                        decoration.unmarkEventDay(selectedDayElem);
                                    } else {
                                        eventTable.fillDetails(selectedDayElem, event.start);
                                    }
                                });
                            });
                        });
                    });
                });

            });

            // open the dialog for editing
            document.getElementById("addEvent").addEventListener("click", function () {
                let date = dateTools.copy(currentDate);
                date.setDate(selectedDayElem.dateindex);
                let iso = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}T00:00:00`;

                eventEditor.hideAlerts();
                eventEditor.clear();
                eventEditor.show();

                // add a date picker
                let dateTimeElem = document.getElementById("datetime");
                dateTimeElem.value = date.toISOString();
                $("#date").flatpickr({
                    clickOpens: true,
                    time_24hr: true,
                    dateFormat: "Y-m-d",
                    altInput: true,
                    altFormat: "d.m.Y",
                    enableTime: false,
                    locale: {
                        "firstDayOfWeek": 1 // start week on Monday
                    },
                    onValueUpdate: function (selectedDates, dateStr) {
                        document.getElementById("datetime").value = dateStr;
                    },
                    defaultDate: iso,
                });
            });
        }

        /**
         * handle event table row clicks
         */
        function addEventRowClickListeners() {

            let elems = document.getElementsByClassName("eventRow");
            for (let elem of elems) {
                elem.addEventListener("click", function (event) {
                    let eventId = event.currentTarget.eventid;
                    if (eventId) { // this is updated every time user clicks a new day
                        let dayEvent = eventContainer.get(eventId);
                        eventEditor.fill(dayEvent, eventId);
                        eventEditor.show();
                    }
                });
            }

        }

        function addCheckboxListener() {
            $("#masterPointCheck").on("click", function () {
                let value = $(this).prop("value");
                $(this).prop("value", !value);
            });
        }

        document.getElementById("month-prev").addEventListener("click", function () {
            updateMonth(-1);
        });
        document.getElementById("month-next").addEventListener("click", function () {
            updateMonth(1);
        });

        addEventRowClickListeners();

        updateMonth();
        createDialogButtonListeners();
        addCheckboxListener();
    }

    let calendar = new Calendar();

    Object.freeze(calendar);

})();