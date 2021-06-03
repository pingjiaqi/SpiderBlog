package com.pjq.dao;

import com.pjq.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public User selectByName(String username);

    public void insertIntoUser(@Param("username") String userName, @Param("password") String passWord);

}
