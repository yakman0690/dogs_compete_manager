/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.Dog;
import ru.yakman.domain.User;
import ru.yakman.service.IDogService;
import ru.yakman.service.IUserService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("user/dog")
public class UserDogController {

    @Autowired
    private IDogService dogService;
    @Autowired
    private IUserService userService;

    org.slf4j.Logger logger = LoggerFactory.getLogger(UserDogController.class);

    @GetMapping(value = "/delete")
    public void deleteDog(@RequestParam(name = "id") Long id) {
        dogService.deleteOne(id);
    }

    @GetMapping(value = "/all")
    public List<Dog> dogs(@AuthenticationPrincipal Object principal) {
        User user = userService.getCurrentUser(principal);
        return dogService.findByUser(user);
    }

    @PostMapping(value = "/add")
    public void addDog(@RequestBody Dog dog, @AuthenticationPrincipal Object principal) {
        try {
            User user = userService.getCurrentUser(principal);
            dogService.addNewDog(dog, user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
