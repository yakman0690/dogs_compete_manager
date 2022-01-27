/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yakman.domain.User;
import ru.yakman.repository.UserRepository;

/**
 *
 * @author Test
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired 
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByLogin(username);
        if(!opt.isPresent()){            
            throw new UsernameNotFoundException("Unknown user: "+username);
        }
        User myUser = opt.get();
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
    
}
