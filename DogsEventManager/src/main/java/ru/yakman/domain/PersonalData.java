/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Test
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalData {

    private String firstName;
    
    private String lastName;

    private String imageUrl;

    private String email;

}
