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
    }


}
