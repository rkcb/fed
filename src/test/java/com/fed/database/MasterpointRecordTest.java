package com.fed.database;

import com.fed.repositories.MasterpointRecordRepository;
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
public class MasterpointRecordTest {

    @Autowired
    MasterpointRecordRepository masterpointRecordRepository;

    @Before
    public void removeAllMasterpoints(){
        masterpointRecordRepository.deleteAll();
    }

    @Test
    public void masterpointTest(){

        Calendar calendar = Calendar.getInstance();
        Timestamp earned = new Timestamp(calendar.getTimeInMillis());

        MasterpointRecord record = MasterpointRecord.create(Float.valueOf(1.2f),
                "Test", "12", "122", earned);

        masterpointRecordRepository.save(record);

        Assert.assertTrue(masterpointRecordRepository.count() == 1);
    }

}
