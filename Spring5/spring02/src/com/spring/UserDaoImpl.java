package com.spring;

import org.springframework.stereotype.Repository;

@Repository(value = "userDaoImpl1")
public class UserDaoImpl implements UserDao{

    @Override
    public void add(){
        System.out.println("dao add...");
    }
}
