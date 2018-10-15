package com.fed.database;

import com.fed.repositories.ClubRepository;
import com.fed.repositories.PlayerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubTest {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Before
    public void deleteAllClubs(){
        clubRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    public void testClub(){

       Club club = Club.create("name", "", "", "", "");

       Club savedClub = clubRepository.save(club);

       Assert.assertTrue(clubRepository.count() == 1);

       Player player = Player.create("username1", "1", "player1@test.com", "password1");

       Player player2 = Player.create("username2", "2", "player2@test.com", "password2");

       playerRepository.save(player);

       playerRepository.save(player2);

       Assert.assertTrue(clubRepository.count() == 1);

       Assert.assertTrue(playerRepository.count() == 2);

       // test club <-> player relation



    }

}
