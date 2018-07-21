package com.fed.controllers;

import com.fed.services.CalendarEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CalendarEventController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/calendarevent")
    public CalendarEvent event(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in calendar event ====");
        return new CalendarEvent();
    }

}
