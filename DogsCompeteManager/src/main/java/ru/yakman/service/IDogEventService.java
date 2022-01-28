/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import java.util.Date;
import java.util.List;
import ru.yakman.domain.DogEvent;

/**
 *
 * @author Test
 */
public interface IDogEventService {

    public List<DogEvent> getAll();

    public List<DogEvent> findByDate(Date date);

    public DogEvent findOne(Long id);

    public void deleteOne(Long id);

    public void addNewEvent(DogEvent dogEvent);
}
