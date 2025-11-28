package com.example.demojpa.controller;

import com.example.demojpa.model.User;
import com.example.demojpa.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/addUser")
    public String addUser(User user) {
        userRepo.save(user);
        return "index";
    }

    @GetMapping("/getUser")
    public ModelAndView getUser(@RequestParam int id) {

        ModelAndView mav = new ModelAndView("showUser");
        User user = userRepo.findById(id).orElse(new User());

        mav.addObject("user", user);
        return mav;
    }
    @PostMapping("/deleteUser")

    public ModelAndView deleteUser(@RequestParam int id) {

        ModelAndView mav = new ModelAndView("deleteUser");
        User user = userRepo.findById(id).orElse(new User());

        userRepo.deleteById(id);

        mav.addObject("user", user);
        return mav;
    }
    @PostMapping("/updateUser")
    public ModelAndView updateUser(User user) {

        ModelAndView mav = new ModelAndView("updateUser");

        User oldUser = userRepo.findById(user.getId()).orElse(null);

        if (oldUser != null) {
            // update fields
            oldUser.setName(user.getName());

            // save updated user
            userRepo.save(oldUser);

            mav.addObject("user", oldUser);
            mav.addObject("message", "User updated successfully!");
        } else {
            mav.addObject("message", "User not found!");
        }

        return mav;
    }

}
