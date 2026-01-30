package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User updatedUser = userService.updateUser(user);
        if(updatedUser!=null) return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<?> getById(){
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
