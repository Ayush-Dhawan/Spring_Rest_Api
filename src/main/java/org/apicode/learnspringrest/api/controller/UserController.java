package org.apicode.learnspringrest.api.controller;

import org.apicode.learnspringrest.api.model.User;
import org.apicode.learnspringrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam int id){
        Optional<User> user = userService.getUserById(id);

        if(user.isPresent()) return new ResponseEntity<>(user.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getUsers() {
        Optional<List<User>> users = userService.getAllUsers();
        if(users.isPresent()) return new ResponseEntity<>(users.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody  User user){
        try {
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateuser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Optional<User> optional;
        try {
           optional = userService.updateUser(user);
           return new ResponseEntity<>(optional.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
