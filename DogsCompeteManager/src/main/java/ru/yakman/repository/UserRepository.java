/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yakman.domain.User;

/**
 *
 * @author Test
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLogin(String login);
    Optional<User> findByVkId(String id);
}
