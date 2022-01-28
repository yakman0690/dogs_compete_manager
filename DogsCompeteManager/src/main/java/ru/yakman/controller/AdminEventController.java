/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.DogEvent;
import ru.yakman.service.IDogEventService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/admin/event")
public class AdminEventController {

    @Autowired
    private IDogEventService dogEventService;

    @GetMapping(value = "/all")
    public List<DogEvent> events() {
        return dogEventService.getAll();
    }

    @PostMapping(value = "/add")
    public void addEvent(@RequestBody DogEvent newEvent) {
        dogEventService.addNewEvent(newEvent);
    }

    @GetMapping(value = "/delete")
    public void addEvent(@RequestParam(name = "id") Long id) {
        dogEventService.deleteOne(id);
    }
}
