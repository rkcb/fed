package com.fed.restcontrollers;

import com.fed.data.Roles;
import com.fed.data.UsersData;
import com.fed.database.Users;
import com.fed.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * # admin actions
 *   - user creation
 *   - role changing by groups (practically only admin change now)
 *
 * # player actions
 *   - change password with old one
 *   - username cannot be changed
 *
 */


@RestController
public class UsersRestController {

    final int passwordLenth = 1;

    @Autowired
    private JdbcUserDetailsManager manager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/users/create")
    String createUsers(@RequestBody UsersData data){

        boolean exists = manager.userExists(data.getUsername());
        boolean passwordNotNull = data.getPassword() != null;
        boolean passwordsMatch = data.getPassword().equals(data.getPassword2());
        boolean passwordLengthOk = passwordNotNull && data.getPassword().length() >= passwordLenth;

        if (exists){
            return "Username exists already";
        }

        if (!passwordNotNull){
            return "Invalid password length";
        }

        if (!passwordsMatch){
            return "Passwords do not match";
        }

        if (!passwordLengthOk){
            return "Password is too short; minimum length is " + passwordLenth;
        }

        Collection<GrantedAuthority> authorities = Roles.of(Roles.Value.PLAYER);

        User user = new User(data.getUsername(), encoder.encode(data.getPassword()), authorities);

        manager.createUser(user);

        return "User created";
    }

    @DeleteMapping("/users/delete")
    ResponseEntity<HttpStatus> deleteUsers(@RequestBody UsersData data){
        manager.deleteUser(data.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/users/remove-from-admins")
    ResponseEntity<HttpStatus> removeFromGroup(@RequestBody UsersData data){

        if (manager.userExists(data.getUsername())){
            manager.removeUserFromGroup(data.getUsername(), "admin");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PatchMapping("/users/add-to-admins")
    ResponseEntity<HttpStatus> addToGroup(@RequestBody UsersData data){

        if (manager.userExists(data.getUsername())){
            manager.addUserToGroup(data.getUsername(), "admin");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PatchMapping("/users/set-account-status")
    ResponseEntity<HttpStatus> setEnabledStatus(@RequestBody UsersData data){

        if (manager.userExists(data.getUsername())){
            Users user = usersRepository.findByUsername(data.getUsername());
            user.setEnabled(data.isEnabled());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     * update the password of the current user
     * @param data
     * @return
     */
    @PatchMapping("/users/update/password")
    ResponseEntity<HttpStatus> updateUserPassword(@RequestBody UsersData data) {

        try {
            manager.changePassword(data.getOldPassword(), data.getPassword());
            return new ResponseEntity(HttpStatus.OK);
        } catch (AuthenticationException exception){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     * get the roles for the current user
     * for anonymous user: [{"authority":"ROLE_ANONYMOUS"}]
     * @return
     */
    @GetMapping("/user/roles")
    Collection<? extends GrantedAuthority> getUserRoles(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();

    }




}
