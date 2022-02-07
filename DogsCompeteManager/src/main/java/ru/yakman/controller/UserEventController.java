/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.Dog;
import ru.yakman.domain.DogEvent;
import ru.yakman.domain.User;
import ru.yakman.service.IDogEventService;
import ru.yakman.service.IDogService;
import ru.yakman.service.IUserService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("user/event")
public class UserEventController {

    @Autowired
    private IDogEventService dogEventService;

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/all")
    public List<DogEvent> events() {
        return dogEventService.getFuture();
    }

    @GetMapping(value = "/my")
    public List<DogEvent> events(@AuthenticationPrincipal Object principal) {
        User current = userService.getCurrentUser(principal);
        return dogEventService.getForUser(current);
    }

    @PostMapping(value = "/signUp")
    public void signUp(@RequestParam(name = "eventId") Long eventId, @RequestParam(name = "dogId") Long dogId) {
        dogEventService.signUpDog(eventId, dogId);
    }

}
