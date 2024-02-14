package com.mycompany.inventory.services;

import com.mycompany.inventory.dao.IUserDao;
import com.mycompany.inventory.model.User;
import com.mycompany.inventory.response.UserResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IUserServiceImpl implements IUserService {
    private IUserDao iUserDao;


    public IUserServiceImpl(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }

    @Override
    public ResponseEntity<UserResponseRest> save(User user) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseRest> searchById(Long id) {
        UserResponseRest userResponseRest = new UserResponseRest();
        List<User> userList = new ArrayList<>();
        
        return new ResponseEntity<UserResponseRest>(userResponseRest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponseRest> update(User user, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseRest> deleteById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseRest> search() {
        return null;
    }
}
