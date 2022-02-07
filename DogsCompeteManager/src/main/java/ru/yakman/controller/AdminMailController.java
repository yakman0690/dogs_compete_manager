/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.service.IEmailService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/admin/mail")
public class AdminMailController {

    @Autowired
    private IEmailService emailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMail(@RequestParam(value = "address", required = true) String address,
            @RequestBody String text) {
        try {
            emailService.send(address, text);
        } catch (MessagingException ex) {
            Logger.getLogger(AdminMailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
