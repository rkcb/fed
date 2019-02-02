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

        Player player = Player.create("testjack","1", "testjack@testjack.com", "password");

        playerRepository.save(player);

        Assert.assertTrue(playerRepository.count() == 1);

//        Player player2 = Player.create("testjack","2", "testjack2@testjack.com", "password2");
//
//        boolean saveFailed2 = false;
//
//        // validate that duplicate username does not save to the database
//        try {
//            playerRepository.save(player2);
//        } catch (ValidationException exception){
//            saveFailed2 = true;
//        }
//        Assert.assertTrue(!saveFailed2);
//
//        boolean saveFailed3 = false;
//
//        // validate that duplicate username does not save to the database
//        Player player3 = Player.create("testjack3","1", "testjack3@testjack.com", "password3");
//
//        try {
//            playerRepository.save(player3);
//        } catch (ValidationException exception){
//            saveFailed3 = true;
//        }
//        Assert.assertTrue(!saveFailed3);
//
//        boolean saveFailed4 = false;
//
//        // missing email address
//        Player player4 = Player.create("testjack4","1", "", "password4");
//
//        try {
//            playerRepository.save(player4);
//        } catch (ValidationException exception){
//            saveFailed4 = true;
//        }


    }

}
