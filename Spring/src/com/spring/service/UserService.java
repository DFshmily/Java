package com.spring.service;

import com.spring.dao.UserDao;
import com.spring.dao.UserDaoImpl;

public class UserService {


    //原始方式：创建UserDao对象
    /*UserDao userDao = new UserDaoImpl();
            userDao.update();*/

    //创建UserDao类型属性，生成set方法
    private UserDao userDao;

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
    public void add() {
        System.out.println("service add......");
        userDao.update();
    }
}
