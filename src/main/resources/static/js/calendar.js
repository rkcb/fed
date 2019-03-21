
(function () {
    "use strict"

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
     * @param Date currentDate
     */
    function fetchMonthEvents(date) {

        let start = getFirstDayOfMonth(date).toISOString();
        let end = getLastDayOfMonth(date).toISOString();

        let pre = start.split("T")[0];
        let pre2 = end.split("T")[0];

        start = pre + " 00:00:00";
        end = pre2 + " 23:59:59";

        $.getJSON({
            url: "/calendarevents/search/findAllByStartBetweenOrderByStartAsc",
            data: { start: start, end: end },
            success: function (data, textStatus, jqXHR) {
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
    function Calendar(date = false){

        const today = date === false ? new Date() : date;
        Object.freeze(today);
        let currentDate = copy(today); // this date will change with navigation
        const showDayDialog = function(event){
            let dateElem = document.getElementById("date");
            let date = copy(currentDate);
            date.setDate(event.srcElement.dateindex);
            let dateValue = "" + date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear();
            dateElem.value = dateValue;
            $("#exampleModalCenter").modal("show");

            // update the datetime input value
            let dateTimeElem = document.getElementById("datetime");
            dateTimeElem.value = date.getTime(); // mill seconds
        };

        /**
         * called after an hours or minutes change
         */
        function updateDatetimeValue() {

            let elem = document.getElementById("datetime");
            let date = new Date();
            date.setTime(elem.value);

            let hours = document.getElementById("hours").value;
            let minutes = document.getElementById("minutes").value;

            date.setHours(hours);
            date.setMinutes(minutes);

            elem.value = date.getTime();
        }

        /**
         * return a Date which points to the day 1--
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
        function addEvent(date) {
            let days = getMonthDateElements(date);
            let day = days[days.length - 1];
            day.closest("td").style.borderBottom = "solid 3px red";
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

        /**
         * update day click listeners
         */
        function addClickListeners(){

            // remove old click listeners
            let allDays = document.getElementsByClassName("day");
            for (let i = 0; i < allDays.length; i++) {
                allDays[i].removeEventListener("click", showDayDialog, true);
                allDays[i].style.cursor = "";
                allDays[i].dateindex = undefined;
            }
            // add current click listeners
            let monthDays = getMonthDateElements(currentDate);
            for (let i = 0; i < monthDays.length; i++) {
                monthDays[i].addEventListener("click", showDayDialog, true);
                monthDays[i].style.cursor = "pointer";
                // this value is used to set the date correctly in the modal dialog
                monthDays[i].dateindex = i + 1;
            }

            // update datetime input value when hours or minutes change
            function addChangeListeners(){
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
            // addEvent(new Date());
            decorateToday(today, currentDate);
            document.getElementById("month").innerText = monthName(currentDate);
            document.getElementById("year").innerText = currentDate.getFullYear();
            addClickListeners();
            fetchMonthEvents(currentDate);

            /**
             * create a collection name for uploaded file(s)
             */
            function addUploadFileNames(){
                let uploadElem = document.getElementById("upload");
                uploadElem.addEventListener("change", function (event) {
                    let elem = event.srcElement;
                    let names = [];
                    for (let i = 0; i < elem.files.length; i++) {
                        names.push(elem.files[i].name);
                    }
                    let name = names.reduce( (acc, curr) => acc + ", " + curr);

                    document.getElementById("filenames").innerHTML = name;

                });
            }
            addUploadFileNames();
        }

        function createDialogButtonListeners(){

            $("#createbutton").on("click", function (event) {

                event.preventDefault();

                let data = {};

                let inputs = $("#tournamentdata *[name]");

                $(inputs).each(function(index, element){
                    let name = $(element).prop("name");
                    data[name] = $(element).val();
                });

                $.ajax({
                    type: "POST",
                    url: "/calendarevents",
                    data: JSON.stringify(data),
                    success: function(data, textStatus, jqXHR){
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