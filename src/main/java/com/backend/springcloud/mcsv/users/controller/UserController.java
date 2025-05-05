package com.backend.springcloud.mcsv.users.controller;

import com.backend.springcloud.mcsv.users.entity.User;
import com.backend.springcloud.mcsv.users.exception.ResourceNotFoundException;
import com.backend.springcloud.mcsv.users.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> findUsers() {
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successful",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

}
