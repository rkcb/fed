package com.fed.repositories;

import com.fed.database.CalendarEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "calendarevents", path = "calendarevents")
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {

    List<CalendarEvent> findCalendarEventByStartBetween(LocalDateTime start, LocalDateTime end);
    List<CalendarEvent> findCalendarEventByTitle(String title);

}
