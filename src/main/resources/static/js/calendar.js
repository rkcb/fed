
(function () {
    "use strict"

    /**
     * creates a calendar logic and initializes the start date
     * @param Date date, by default current local date
     * @constructor
     */
    function Calendar(date = false){

        const today = date === false ? new Date() : date;
        Object.freeze(today);
        let currentDate = copy(today); // this date will change with navigation
        const showDayDialog = function(event){
            $("#exampleModalCenter").modal("show");
        };
        Object.freeze(showDayDialog);

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

        function copy(date) {
            return new Date(date.getTime());
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

        function properCalendarElements(date) {
            let prefix = daysBeforeFirst(date);
            let days = document.getElementsByClassName("day");
            let daysInMonth = getNumberOfDaysInMonth(date);
            return days.splice(prefix, prefix + daysInMonth);
        }

        /**
         * get the month days only
         * @param date
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
         * @param date
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
         * updates the month
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



            function addUploadFileNames(){
                let uploadElem = document.getElementById("upload");
                uploadElem.addEventListener("change", function (event) {
                    let elem = event.srcElement;
                    let names = [];
                    for (let i = 0; i < elem.files.length; i++) {
                        names.push(elem.files[i].name);
                    }
                    let name = names.reduce( (acc, curr) => acc + ", " + curr);

                    let nameElem = document.getElementById("filenames").innerHTML = name;

                });
            };
            addUploadFileNames();
        }

        function addCalendarEventListeners() {
            document.getElementById("month-prev").addEventListener("click",
                updateMonth(-1));
            document.getElementById("month-next").addEventListener("click",
                updateMonth(1));
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
            }
            // add current click listeners
            let monthDays = getMonthDateElements(currentDate);
            for (let i = 0; i < monthDays.length; i++) {
                monthDays[i].addEventListener("click", showDayDialog, true);
                monthDays[i].style.cursor = "pointer";
            }

        }

        function keyListeners(){
            document.getElementById("month-prev").addEventListener("")
        }

        document.getElementById("month-prev").addEventListener("click", function () {
            updateMonth(-1);
        });
        document.getElementById("month-next").addEventListener("click", function () {
            updateMonth(1);
        });

        updateMonth();

    }

    let calendar = new Calendar();
    Object.freeze(calendar);

})();

/*
var datepicker = document.getElementById("date").flatpickr({
    weekNumbers: false,
    clickOpens: true,
    // wrap: true,
    // enableTime: true, // does not work with BS 4

    // time_24hr: true,
    dateFormat: "Y-m-dTH:i",
    altInput: true,
    altFormat: "d.m.Y",
    locale: "fi",
});
*/