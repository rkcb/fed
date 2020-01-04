package com.fed.database;

import com.fed.repositories.CalendarEventRepository;
import com.fed.repositories.CompetitionRegistrationRepository;
import com.fed.repositories.PlayerRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionRegistrationRepositoryTest {

    @Autowired
    CompetitionRegistrationRepository registrationRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    CalendarEventRepository calendarEventRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Before
    public void initialize(){

        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        Tournament tournament = Tournament.create("tournament title", timestamp);
        calendarEventRepository.save(tournament.getCalendarEvent());
        tournamentRepository.save(tournament);
    }

    @Test
    public void addRegistrationToRegistration(){
        Assert.assertTrue(tournamentRepository.findAll().size() == 1);

        CompetitionRegistration registration = new CompetitionRegistration();
        Tournament tournament = tournamentRepository.findAll().get(0);
        registration.setTournament(tournament);
        registrationRepository.save(registration);

        Assert.assertTrue(registrationRepository.findAll().size() == 1);

        tournament = tournamentRepository.findAll().get(0);

        // reload the saved one
        registration = registrationRepository.findAll().get(0);

        tournament.setCompetitionRegistration(registration);
        tournamentRepository.save(tournament);

        // registration set
        Assert.assertTrue(tournamentRepository.findAll().size() == 1);
        tournament = tournamentRepository.findAll().get(0);
        Assert.assertNotNull(tournament.getCompetitionRegistration());

        // add a registered player and tournament and save

        registration.setPlayer1Code("1234");
        registration.setTournament(tournament);
        registrationRepository.save(registration);

        registration = registrationRepository.findAll().get(0);
        Assert.assertTrue(registration.getPlayer1Code().equals("1234"));
    }

    @Test
    public void testUniquePlayers(){
        CompetitionRegistration registration = new CompetitionRegistration();
        Tournament tournament = tournamentRepository.findAll().get(0);
        registration.setTournament(tournament);
        registration.setPlayer1Code("1");
        registration.setPlayer2Code("1");
        registration = registrationRepository.save(registration);
        Assert.assertTrue(registrationRepository.count() == 1);


    }

}
