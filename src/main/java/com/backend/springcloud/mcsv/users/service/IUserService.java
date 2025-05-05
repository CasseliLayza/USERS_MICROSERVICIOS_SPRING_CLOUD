package com.backend.springcloud.mcsv.users.service;

import com.backend.springcloud.mcsv.users.entity.User;
import com.backend.springcloud.mcsv.users.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    List<User> findUsers();
    User findUser(Long id) throws ResourceNotFoundException;
    User updateUser(Long id, User user) throws ResourceNotFoundException;
    void deleteUser(Long id);
    User findByUsername(String username) throws ResourceNotFoundException;

}
