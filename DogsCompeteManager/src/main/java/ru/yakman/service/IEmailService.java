/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import javax.mail.MessagingException;

/**
 *
 * @author Test
 */
public interface IEmailService {
     public void send(String mail, String text) throws MessagingException;
}
