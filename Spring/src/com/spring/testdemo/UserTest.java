package com.spring.testdemo;

import com.spring.Book;
import com.spring.dao.UserDao;
import com.spring.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    //创建UserDao类型属性，生成set方法
//    private UserDao userDao;
//
//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }
//    public void add(){
//        System.out.println("service add......");
//        userDao.update();
//    }
    @Test
    public void testAdd(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        //2.获取配置创建的对象
        UserService userService = context.getBean("userService",UserService.class);
        userService.add();
    }
}
