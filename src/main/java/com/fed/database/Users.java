package com.fed.database;

import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity is only for checking existence
 */

@Data
@Entity
public class Users {

    @Id
    @ReadOnlyProperty
    private String username;

    @ReadOnlyProperty
    private String password;

    private boolean enabled;


}
