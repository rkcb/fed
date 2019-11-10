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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        // save players

        List<Player> players = new ArrayList<>(1);

        for (int i = 0; i < 1; i++){
            Player player = Player.create("username " + i, "code " + i,
                    "player" + i + "@iki.fi", "password");
            players.add(player);
        }

//        registration.setPlayers(players);
        registrationRepository.save(registration);

        registration = registrationRepository.findAll().get(0);

//        registration.setPlayers(players);
        registrationRepository.save(registration);
//        Assert.assertTrue(registration.getPlayers().size() == 6);
    }

//    @Test
//    public void bindTournamentAndRegistration(){
//    }

}
