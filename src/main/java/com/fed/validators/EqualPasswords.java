package com.fed.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { EqualPasswordsValidator.class })
@Documented
public @interface EqualPasswords {

    String message() default "Unequal passwords";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}