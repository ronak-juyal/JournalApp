package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList("USER"));
        return userRepository.save(user);
    }

    public User saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepository.save(user);
    }

    public User getUser(String userName){
        return userRepository.findByUserName(userName);
    }
    @Transactional
    public User updateUser(User user) {
        User oldUser = userRepository.findByUserName(getUserName());
        if(oldUser!=null){
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(oldUser);
        }
        return null;
    }

    public void deleteUser() {
        userRepository.deleteByUserName(getUserName());
    }

    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}
