/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yakman.service.IUserService;
import ru.yakman.service.impl.CustomOAuth2UserService;
import ru.yakman.service.impl.UserDetailsServiceImpl;

/**
 *
 * @author Test
 */
@WebMvcTest(AdminUsersController.class)
public class AdminUsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private UserDetailsServiceImpl uds;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    public void shouldThrowExceptionIfUnauthenticated() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(new LinkedList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void shouldPassIfAuthenticated() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(new LinkedList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
