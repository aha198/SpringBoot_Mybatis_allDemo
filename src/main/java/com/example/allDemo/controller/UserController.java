package com.example.allDemo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import javax.annotation.Resource;
import java.util.List;
import com.example.allDemo.service.*;
import com.example.allDemo.entity.*;
import org.springframework.web.bind.annotation.*;
import com.example.allDemo.service.UserService;
import com.example.allDemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/list")
  public List<User> selectUser() {
    return userService.selectUser();
  }

  @PostMapping("/add")
  public List<User> addUser(@RequestBody User user) {
    userService.addUser(user);
    return userService.selectUser(); // 返回修改后的列表
  }

  @PutMapping("/update/{id}")
  public List<User> updateUser(@PathVariable int id, @RequestBody User user) {
    user.setId(String.valueOf(id));
    userService.updateUser(user);
    return userService.selectUser(); // 返回修改后的列表
  }




  @DeleteMapping("/delete/{id}")
  public List<User> deleteUser(@PathVariable int id) {
    userService.deleteUser(id);
    return userService.selectUser(); // 返回修改后的列表
  }
}





 


