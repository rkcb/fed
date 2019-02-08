package com.fed.database;

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
public class PlayerTest {

    @Autowired
    PlayerRepository playerRepository;


    @Before
    public void deleteAllPlayers(){
        playerRepository.deleteAll();
    }

    @Test
    public void testPlayer(){

        Assert.assertTrue(true);

        Player player = Player.create("testjack","1", "testjack@testjack.com", "password");

        playerRepository.save(player);

        Assert.assertTrue(playerRepository.count() == 1);

//        Player player2 = Player.create("testjack","2", "testjack2@testjack.com", "password2");
        Player player2 = Player.create("testjack","2", "touko@testjack.com", "touko");

        // invalid save throws an exception since the username already exists
        try {
            playerRepository.save(player2);
        } catch(Exception e){
        }

        Assert.assertTrue(playerRepository.count() == 1);


        // validate that duplicate username does not save to the database
        Player player3 = Player.create("testjack3","1", "testjack3@testjack.com", "password3");

        try {
            playerRepository.save(player3);
        } catch (Exception exception){
        }
        Assert.assertTrue(playerRepository.count() == 1);


    }

}
