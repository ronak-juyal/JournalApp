package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userService.getAllUser();
        if(!allUser.isEmpty()) return new ResponseEntity<>(allUser, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User save = userService.saveAdmin(user);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
}
