package com.spring_template.base.rest;

import com.spring_template.base.entity.User;
import com.spring_template.base.exception.UserNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private List<User> users;

    @PostConstruct
    private void loadData(){
        users = new ArrayList();

        users.add(new User(1, "User", "1"));
        users.add(new User(2, "User", "2"));
        users.add(new User(3, "User", "3"));
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return users;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        Optional<User> findUser = users.stream().filter(user -> user.getId() == id).findFirst();

        if (findUser.isPresent()) {
            return new ResponseEntity<>(findUser.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found for id: "+ id);
        }
    }
}
