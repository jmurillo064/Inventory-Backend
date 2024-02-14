package com.mycompany.inventory.dao;

import com.mycompany.inventory.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserDao extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
