/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.domain;

import java.util.Date;
import javax.persistence.Embeddable;
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
@Embeddable
public class PersonalData {

    private String firstName;
    private String lastName;

    private String imageUrl;

    private Date birthDate;

    private String email;

}
