/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.domain;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Test
 */
@Getter
@Setter
@Document
public class Dog {

    @Id
    private Long id;

    public enum Gender {
        MALE,
        FEMALE
    }

    private String name;

    private String breed;

    private Date birthday;

    private Gender gender;

    private String pedigree;

    private String tatoo;

    //@DBRef
    //private List<DogEvent> dogEvent_ids; 
    private User owner;

}
