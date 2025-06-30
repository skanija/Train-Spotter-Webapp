package com.example.train_spotter.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.train_spotter.mvc.models.User;
import com.example.train_spotter.mvc.services.UserService;

import jakarta.validation.Valid;

public class UserApi {

    @Autowired
    UserService userService;

    @PostMapping("/api/users/new")
    public User create(@Valid @ModelAttribute("user") User user, BindingResult result) {
        return userService.createUser(user);
    }

    @RequestMapping("/api/users/{id}")
    public User show(@PathVariable("id") Long id) {
        User expense = userService.findUser(id);
        return expense;
    }

}
