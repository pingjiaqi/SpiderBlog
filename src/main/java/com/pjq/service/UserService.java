package com.pjq.service;

import com.pjq.dao.UserDao;
import com.pjq.pojo.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("userService")
@Scope("prototype")

public class UserService {


    @Resource
    UserDao userMapper;

    public boolean login(String userName, String passWord) {
        User user = userMapper.selectByName(userName);
        if (user != null) {
            if (user.getUsername().equals(userName) && user.getPassword().equals((passWord))) {
                return true;
            }
        }
        return false;
    }


    public boolean register(String userName, String passWord) {
        User user = userMapper.selectByName(userName);
        if (user == null) {
            userMapper.insertIntoUser(userName, passWord);
            return true;
        } else {
            return false;
        }
    }

    public User userMessage(String userName) {
        User user = userMapper.selectByName(userName);
        return user;
    }







}
