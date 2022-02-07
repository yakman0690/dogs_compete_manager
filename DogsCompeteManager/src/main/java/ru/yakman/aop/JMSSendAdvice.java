/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yakman.jms.JMSMessageSender;

/**
 *
 * @author Test
 * 
 * test class for aop advice 
 */

@Component
@Aspect
public class JMSSendAdvice {
    
    @Autowired
    private JMSMessageSender sender;
    
    @Pointcut("@annotation(ru.yakman.aop.SendJMSMessage)")
    public void sendJMSMessageAnntoated(){
        
    }
    
    @AfterReturning("sendJMSMessageAnntoated()")
    public void advice() {
        sender.send();
    }
    
}
