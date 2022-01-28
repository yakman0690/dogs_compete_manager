/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Test
 */

@Getter
@Setter
@Entity
public class Dog {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Gender {
        MALE,
        FEMALE
    }

    private String name;
    private String breed;
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String pedigree;
    private String tatoo;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private User owner;
    
}
