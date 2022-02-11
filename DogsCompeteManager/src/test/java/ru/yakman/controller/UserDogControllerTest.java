/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yakman.service.IDogService;
import ru.yakman.service.IUserService;
import ru.yakman.service.impl.CustomOAuth2UserService;
import ru.yakman.service.impl.UserDetailsServiceImpl;

/**
 *
 * @author Test
 */
@WebMvcTest(UserDogController.class)
public class UserDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;
    @MockBean
    private IDogService dogService;

    @MockBean
    private UserDetailsServiceImpl uds;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;
    
    @BeforeEach
    public void initMethods(){        
        Mockito.when(userService.getCurrentUser(Mockito.any())).thenReturn(null);
        Mockito.when(dogService.findByUser(Mockito.any())).thenReturn(new LinkedList<>());
    }

    @Test
    public void shouldRedirectIfUnauthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/dog/all"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "user", roles = "USER")
    public void shouldPassIfAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/dog/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    public void shouldThrowExceptionIfWrongRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/dog/all"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
