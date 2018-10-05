package com.fed.repositories;

import com.fed.database.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "calendarevents", path = "calendarevents")
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {

    List<CalendarEvent> findCalendarEventByTitle(@Param("title") String title);

    List<CalendarEvent> findCalendarEventsByStartAfter(@Param("start") Timestamp start);


}
