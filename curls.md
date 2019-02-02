
# Add a Calendar event from command line

curl -i -X POST -H "Content-Type:application/json" -d '{"title": "My Title", "description": "My Description", "start": "2018-09-24T08:22:12"}' http://localhost:8080/calendarevents

# Query CalendarEvent by ISO DateTime

curl localhost:8080/calendarevents/search/

# Query calendar events

curl localhost:8080/calendarevents/findAllByStartBetween?

# Create a new player 

curl -i -X POST -H "Content-Type:application/json" -d '{"username": "escobar", "email": "esco@iki.fi", "code": 
"fedcode"}' http://localhost:8080/players

# Create a club

curl -i -X POST -H "Content-Type:application/json" -d '#' http://localhost:8080/clubs

# Add a player to a club (only the owning side of a relation can set the value)

- https://docs.spring.io/spring-data/rest/docs/current/reference/html/#repository-resources.association-resource
- https://www.baeldung.com/spring-data-rest-relationships 

- curl-i -X PUT -d "http://localhost:8080/clubs/1" -H "Content-Type:text/uri-list" http://localhost:8080/players/1/club 



