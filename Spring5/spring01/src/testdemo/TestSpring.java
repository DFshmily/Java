package testdemo;

import com.autowire.Emp;
import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp);
    }

    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        UserService userService=context.getBean("userService", UserService.class);
        userService.add();
    }

    @Test
    public void userTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        UserService userService=context.getBean("userService", UserService.class);
        userService.add();
    }
}

