
# Remarks

When submitting a URI with query parameters adding quotes (") are necessary.
For examples curl http://getAllByStartBetween?start .. & end ... does not 
work otherwise.

# Repositories

curl localhost:8080/profile

# Add a Calendar event from command line

curl -i -X POST -H "Content-Type:application/json" -d '{"title": "timetest", 
"start": "2019-03-21T13:00:00"}' http://localhost:8080/calendarevents

## Data response format

### data._links.{self, calendarEvent, tournament}.href
{
  "title" : "",
  "description" : null,
  "location" : null,
  "organizer" : null,
  "masterpoints" : false,
  "price" : null,
  "start" : "2019-03-21T13:00:00.000+0000",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/calendarevents/3"
    },
    "calendarEvent" : {
      "href" : "http://localhost:8080/calendarevents/3"
    },
    "tournament" : {
      "href" : "http://localhost:8080/calendarevents/3/tournament"
    }
  }
}


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

- curl -i -X PUT -d "http://localhost:8080/clubs/1" -H "Content-Type:text/uri-list" 
http://localhost:8080/players/1/club 
- see the ALPS description for the JPA REST repository profile 
  (curl http://localhost:8080/profile/<repository URL>)
  
  
  
# Add a calendar event and a tournament and set them to 1-1 relationship

1. Create a calendar event and a tournament. The order does not matter.
2. Set these to the 1-1 relationship by the following (update the ids as needed)

curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/tournaments/1" 
http://localhost:8080/calendarevents/1/tournament

# Remove relation from the owning side

curl -i -X DELETE http://localhost:8080/calendarevents/1/tournament


