package com.spring.testdemo;

import com.spring.Book;
import com.spring.Orders;
import com.spring.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    @Test
    public void OrdersTest(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book",Book.class);
        System.out.println(book);
        book.toString();
    }
}
