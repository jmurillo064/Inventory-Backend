package com.mycompany.inventory.services;

import com.mycompany.inventory.model.User;
import com.mycompany.inventory.response.UserResponseRest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public ResponseEntity<UserResponseRest> save(User user);
    public ResponseEntity<UserResponseRest> searchById(Long id);
    public ResponseEntity<UserResponseRest> update(User user, Long id);
    public ResponseEntity<UserResponseRest> deleteById(Long id);
    public ResponseEntity<UserResponseRest> search();
}
