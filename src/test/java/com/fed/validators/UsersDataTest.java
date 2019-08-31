package com.fed.validators;

import com.fed.data.UsersData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersDataTest {

    @Test
    public void passwordsTest(){

        UsersData usersData = new UsersData();
        usersData.setPassword("111");
        usersData.setPassword2("222");
        usersData.setUsername("eero@eero.se");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UsersData>> violations = validator.validate(usersData);
        for( ConstraintViolation<UsersData> v : violations){
//            System.out.println(">>>>>>>>>>> " + v.getMessage());
        }
        Assert.assertTrue(violations.size() > 0);

    }

}
