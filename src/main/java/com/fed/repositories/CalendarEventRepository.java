package com.fed.repositories;

import com.fed.database.CalendarEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "calendarevents", path = "calendarevents")
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {

    List<CalendarEvent> findCalendarEventByBeginBetween(Timestamp begin, Timestamp end);
    List<CalendarEvent> findCalendarEventBySubject(String subject);

}
