package testdemo;

import com.bean.Orders;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrdersTest {
    @Test
    public void orderTset(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println("第六步 获取创建bean实例对象");
        System.out.println(orders);


        //手动让bean实例销毁
        context.close();
    }
}
