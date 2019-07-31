package com.fed.controllers;

import org.springframework.stereotype.Controller;

/**
 * TODO:
 *  - test cors and csrf
 *  - add the csrf token by thymeleaf
 *  - test methods
 *  - finish the view
 */

@Controller
public class UsersController {
/*
    final int passwordLenth = 1;

    @Autowired
    private JdbcUserDetailsManager manager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public void users(){
    }

    @PostMapping(value = "/users/create")
    String createUsers(@ModelAttribute UsersData data,
                       @RequestBody UsersData usersData) {

        boolean exists = manager.userExists(data.getUsername());
        boolean passwordNotNull = data.getPassword() != null;
        boolean passwordsMatch = data.getPassword().equals(data.getPassword2());
        boolean passwordLengthOk = passwordNotNull && data.getPassword().length() >= passwordLenth;

        if (exists) {
//            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        if (!passwordNotNull) {
//            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        if (!passwordsMatch) {
//            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        if (!passwordLengthOk) {
//            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }



        Collection<GrantedAuthority> authorities = Roles.of(Roles.Value.PLAYER);

        User user = new User(data.getUsername(), encoder.encode(data.getPassword()), authorities);

        manager.createUser(user);

        return "users";
//        return new ResponseEntity(HttpStatus.CREATED);
    }

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
