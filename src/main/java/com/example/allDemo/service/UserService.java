package com.example.allDemo.service;

import com.example.allDemo.dao.UserDao;
import com.example.allDemo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Resource
    private UserDao userDao;

    public List<User> selectUser() {
        return userDao.selectUser();
    }

    public void addUser(User user) {
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
    	userDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}

