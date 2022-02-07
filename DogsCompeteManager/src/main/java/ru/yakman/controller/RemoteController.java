/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.DogEvent;
import ru.yakman.service.IDogEventService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/remote")
public class RemoteController {

    @Autowired
    private IDogEventService dogEventService;

    @GetMapping(value = "/events/future")
    public List<DogEvent> getEvents() {
        return dogEventService.getFuture();
    }

    @GetMapping(value = "/events/all")
    public List<DogEvent> getAllEvents() {
        return dogEventService.getAll();
    }
}
