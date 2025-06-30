package com.example.train_spotter.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.train_spotter.mvc.models.Train;
import com.example.train_spotter.mvc.services.TrainService;

import jakarta.validation.Valid;

public class TrainApi {

    @Autowired
    TrainService trainService;

    @PostMapping("/api/trains/new")
    public Train create(@Valid @ModelAttribute("train") Train train, BindingResult result) {
        return trainService.createTrain(train);
    }

    @RequestMapping("/api/trains/{id}")
    public Train show(@PathVariable("id") Long id) {
        Train expense = trainService.findTrain(id);
        return expense;
    }

}
