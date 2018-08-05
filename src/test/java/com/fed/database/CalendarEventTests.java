package com.fed.database;

import com.fed.database.CalendarEvent;
import com.fed.repositories.CalendarEventRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalendarEventTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CalendarEventRepository calendarEventRepository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        calendarEventRepository.deleteAll();
    }

    @Test
    public void calendarEventTest(){

        Calendar calendar = Calendar.getInstance();

        Date now = calendar.getTime();
        Timestamp begin = new Timestamp(now.getTime());

        CalendarEvent event = CalendarEvent.create(begin, begin, "My Event",
                "My Description", "My Location");

        calendarEventRepository.save(event);

        List<CalendarEvent> events = calendarEventRepository.findCalendarEventBySubject("My Event");

        Assert.assertTrue(events.size() == 1);

    }


}
