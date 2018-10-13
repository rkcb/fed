package com.fed.database;

import com.fed.repositories.CalendarEventRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class CalendarEventTests {

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    CalendarEventRepository calendarEventRepository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        calendarEventRepository.deleteAll();
    }

    @Test
    public void calendarEventTest(){

        Calendar calendar = Calendar.getInstance();

        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        CalendarEvent event = CalendarEvent.create("title", timestamp);

        Assert.assertTrue("calendar event", event instanceof CalendarEvent);

    }


}
