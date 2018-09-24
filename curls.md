
# Add a Calendar event from command line

curl -i -X POST -H "Content-Type:application/json" -d '{"title": "My Title", "description": "My Description", "start": "2018-09-24T08:22:12"}' http://localhost:8080/calendarevents