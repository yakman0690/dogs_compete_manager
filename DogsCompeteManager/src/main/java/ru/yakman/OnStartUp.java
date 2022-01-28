/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.yakman.service.IUserService;

/**
 *
 * @author Test
 */
@Component
public class OnStartUp
        implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private IUserService userService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        userService.createServiceAdmin();
    }
}
