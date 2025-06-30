package com.example.train_spotter.mvc.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.train_spotter.mvc.models.Train;
import com.example.train_spotter.mvc.models.User;
import com.example.train_spotter.mvc.services.TrainService;
import com.example.train_spotter.mvc.services.UserService;
import com.example.train_spotter.mvc.storage.StorageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TrainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/trains")
    public String displayAllTrains(@RequestParam(required = false) String engineType, Model model, HttpSession session) {
        if(session.getAttribute("loggedInUser") != null){
            User user = userService.findUser((long) session.getAttribute("loggedInUser"));
            List<Train> all = trainService.allTrains();
                if (engineType != null && !engineType.isEmpty()) {
                    all = all.stream()
                            .filter(t -> t.getEngineType().equals(engineType))
                            .collect(Collectors.toList());
                }
            model.addAttribute("loggedInUser", user);
            model.addAttribute("allUsers", userService.allUsers());
            model.addAttribute("allTrains", all);
            model.addAttribute("engineType", engineType);
            session.removeAttribute("currentTrain");
            return "trainIndex";
        }
        return "redirect:/logout";
    }

    @GetMapping("/trains/new")
    public String newTrain(@Valid @ModelAttribute("train") Train train, BindingResult result, Model model, HttpSession session) {
        if(session.getAttribute("loggedInUser") != null){
            User user = userService.findUser((long) session.getAttribute("loggedInUser"));
            model.addAttribute("loggedInUser", user);
            return "addTrain";
        }
        return "redirect:/logout";
    }

    @PostMapping("/trains/add")
    public String createTrain(@Valid @ModelAttribute("train") Train train, BindingResult result, @RequestParam("image") MultipartFile image,  HttpSession session) {
        User user = userService.findUser((long) session.getAttribute("loggedInUser"));
        Train newTrain = new Train(train.getName(), train.getWheelArrangement(), train.getEngineType(), train.getBuildDate(), train.getLocation(), train.getDateSpotted(), user);
        newTrain.setImagePath(image.getOriginalFilename());
        newTrain.setImageContentType(image.getContentType());
        newTrain.setImageSize(image.getSize());
        trainService.createTrain(newTrain);
        storageService.store(image);
        return "redirect:/trains";
    }

    @GetMapping("/trains/{train_id}")
    public String displayOneTrain(@PathVariable("train_id") long trainId, Model model, HttpSession session) {
        if(session.getAttribute("loggedInUser") != null){
            User user = userService.findUser((long) session.getAttribute("loggedInUser"));
            model.addAttribute("loggedInUser", user);
            model.addAttribute("poster", trainService.findTrain(trainId).getUser());
            model.addAttribute("train", trainService.findTrain(trainId));
            session.setAttribute("currentTrain", trainService.findTrain(trainId));
            return "oneTrain";
        }
        return "redirect:/logout";
    }

    @RequestMapping("/trains/edit/{id}")
    public String editTrain(@PathVariable("id") long trainId, @Valid @ModelAttribute("train") Train train, BindingResult result, Model model, HttpSession session) {
        User loggedInUser = userService.findUser((long) session.getAttribute("loggedInUser"));
        if(session.getAttribute("loggedInUser") != null && trainService.findTrain(trainId).getUser().getId() == loggedInUser.getId()){
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("train", trainService.findTrain(trainId));
            return "editTrain";
        }
        return "redirect:/logout";
    }

    @RequestMapping("/trains/edit")
    public String update(@Valid @ModelAttribute("train") Train train, BindingResult result, @RequestParam("image") MultipartFile image, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("train", train);
            return "editTrain";
        } else {
            Train newTrain = train;
            newTrain.setUser(trainService.findTrain(train.getId()).getUser());
            newTrain.setImagePath(image.getOriginalFilename());
            newTrain.setImageContentType(image.getContentType());
            newTrain.setImageSize(image.getSize());
            trainService.updateTrain(newTrain);
            storageService.store(image);
            return "redirect:/trains";
        }
    }

    @RequestMapping(value="/trains/delete/{id}")
    public String destroy(@PathVariable("id") Long id, HttpSession session) {
        User loggedInUser = userService.findUser((long) session.getAttribute("loggedInUser"));
        if(trainService.findTrain(id).getUser().getId() == loggedInUser.getId()){
            trainService.deleteTrain(id);
        }
        return "redirect:/trains";
    }

}
