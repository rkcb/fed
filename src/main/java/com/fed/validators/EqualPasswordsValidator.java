package com.fed.validators;

import com.fed.data.UsersData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, UsersData> {

    @Override
    public boolean isValid(UsersData usersData, ConstraintValidatorContext constraintValidatorContext) {

        return false;
//        String p = usersData.getPassword();
//        String p2 = usersData.getPassword2();
//
//        return !(p == null || p2 == null || !p.equals(p2));
    }
}
