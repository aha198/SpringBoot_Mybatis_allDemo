package com.example.allDemo.dao;

import com.example.allDemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    List<User> selectUser();
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}

