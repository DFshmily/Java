package com.spring.testdemo;

import com.spring.bean.Emp;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeptTest {

    @Test
    public void testAdd(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        //2.获取配置创建的对象
        Emp emp = context.getBean("emp",Emp.class);
        emp.Show();
    }
}
