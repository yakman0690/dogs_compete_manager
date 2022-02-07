/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Document
public class DogEvent {

    @Id
    private Long id;

    private String name;

    private Date date;

    //@DBRef
    private List<Dog> participants = new LinkedList<>();

}
