package com.fed.database;

import com.fed.repositories.CalendarEventRepository;
import com.fed.repositories.TournamentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TournamentTest {

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    CalendarEventRepository calendarEventRepository;

    @Before
    public void deleteAllTournamentsAndCalendarEvents(){
        tournamentRepository.deleteAll();
        calendarEventRepository.deleteAll();
    }

    @Test
    public void tournamentTest(){

        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        Tournament tournament = Tournament.create("tournament title", timestamp);
        calendarEventRepository.save(tournament.getCalendarEvent());
        tournamentRepository.save(tournament);

        Assert.assertTrue(tournamentRepository.count() == 1);

        List<Tournament> tournaments = tournamentRepository.findAll();

        Tournament savedTournament = tournaments.get(0);

        Assert.assertTrue(savedTournament.getCalendarEvent().getId() > 0);


    }


}
