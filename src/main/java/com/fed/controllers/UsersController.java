package com.fed.controllers;

import com.fed.data.Roles;
import com.fed.data.UsersData;
import com.fed.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO:
 *  - test cors and csrf
 *  - add the csrf token by thymeleaf
 *  - test methods
 *  - finish the view
 */

@Controller
public class UsersController {

    @GetMapping("/users")
    public String get(){
        return "users";
    }


    final int passwordLenth = 1;

    @Autowired
    private JdbcUserDetailsManager manager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value = "/users/create")
    String createUsers(@Valid UsersData usersData, BindingResult binding,
                             ModelMap model) {

        boolean exists = manager.userExists(usersData.getUsername());
        boolean passwordNotNull = usersData.getPassword() != null;
        boolean passwordsMatch = usersData.getPassword().equals(usersData.getPassword2());
        boolean passwordLengthOk =
                passwordNotNull && usersData.getPassword().length() >= passwordLenth;

        model.addAttribute("usernameExists", exists);
        model.addAttribute("usersData", usersData);

        if (exists) {
            return "users";
        }

        Collection<GrantedAuthority> authorities = Roles.of(Roles.Value.PLAYER);

        User user = new User(usersData.getUsername(), encoder.encode(usersData.getPassword()), authorities);

        manager.createUser(user);

        return "users";

//        return new ResponseEntity(HttpStatus.CREATED);
    }
/*
    @DeleteMapping("/users/delete")
    String deleteUsers(@RequestBody UsersData data) {
        manager.deleteUser(data.getUsername());
        return "users";
    }

    @PatchMapping("/users/remove-from-admins")
    ResponseEntity<HttpStatus> removeFromGroup(@RequestParam String username) {

        if (manager.userExists(username)) {
            manager.removeUserFromGroup(username, "admin");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PatchMapping("/users/add-to-admins")
    ResponseEntity<HttpStatus> addToGroup(@RequestBody UsersData data) {

        if (manager.userExists(data.getUsername())) {
            manager.addUserToGroup(data.getUsername(), "admin");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PatchMapping("/users/set-account-status")
    ResponseEntity<HttpStatus> setEnabledStatus(@RequestBody UsersData data) {

        if (manager.userExists(data.getUsername())) {
            Users user = usersRepository.findByUsername(data.getUsername());
            user.setEnabled(data.isEnabled());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     * update the password of the current user
     *
     * @param data
     * @return
     */
//    @PatchMapping("/users/update/password")
//    ResponseEntity<HttpStatus> updateUserPassword(@RequestBody UsersData data) {
//
//        try {
//            manager.changePassword(data.getOldPassword(), data.getPassword());
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (AuthenticationException exception) {
//            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
//        }
//
//    }

    /**
     * get the roles for the current user
     * for anonymous user: [{"authority":"ROLE_ANONYMOUS"}]
     *
     * @return
     */
//    @GetMapping("/user/roles")
//    Collection<? extends GrantedAuthority> getUserRoles() {
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername();
//        } else {
//            String username = principal.toString();
//        }
//
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//
//    }


}
