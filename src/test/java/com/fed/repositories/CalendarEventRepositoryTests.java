package com.fed.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureMockMvc
public class CalendarEventRepositoryTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalendarEventRepository eventRepository;

    @Before
    public void deleteAllBeforeTest(){
        eventRepository.deleteAll();
    }

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {

    }
//    @Test
//    public void shouldReturnRepositoryIndex() throws Exception {
//
//        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
//                jsonPath("$._links.people").exists());
//    }
//
    @Test
    public void shouldCreateEntity() throws Exception {

//        mockMvc.perform(post("/calendarevents").content(
//                "{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")).andExpect(
//                status().isCreated()).andExpect(
//                header().string("Location", containsString("calendarevents/")));
    }


}
