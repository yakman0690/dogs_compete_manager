/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import java.util.stream.Stream;
import javax.mail.Quota.Resource;
import javax.persistence.criteria.Path;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Test
 */
public interface IStorageService {

    void store(MultipartFile file);

}
