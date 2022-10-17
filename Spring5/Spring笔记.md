# Spring 5框架

## 一、Spring概念

1、Spring是轻量级的JavaEE框架

2、Spring可以解决企业应用开发的复杂性

3、Spring有两个核心部分：IOC和AOP

​	1）IOC：控制反转，把创建对象过程交给Spring进行管理

​	2）AOP：面向切面，不修改源代码进行功能增强

4、Spring特点

1. 方便解耦，简化开发
2. AOP编程的支持
3. 方便程序的测试
4. 方便集成各种优秀框架（方便整合其他框架）
5. 方便进行事务操作
6. 降低API开发难度

**入门案例：**

1.下载地址：https://repo.spring.io/ui/native/release/org/springframework/spring

2.打开idea，新建java项目(项目例子文件:https://github.com/DFshmily/Java/tree/main/Spring5)

3.导入Spring5相关jar包

![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927165802436-495388018.png)

![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927173446680-641688407.png)



4.创建普通类，在这个类创建普通方法

```java
public class User{
    public void add(){
        System.out.println("add......");
    }
}
```

5.创建Spring配置文件，在配置文件创建对象

Spring配置文件使用xml格式，创建在src下

![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927174750971-1076787364.png)

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置User对象创建-->
    <bean id="user" class="com.spring.User"></bean>
</beans>
```

6.进行代码测试编写

User.java

```java
package com.spring;

public class User {
    public void add(){
        System.out.println("add......");
    }
}

```

TestSpring.java

```java
package com.spring.testdemo;

import com.spring.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    @Test
    public void testAdd(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //2.获取配置创建的对象
        User user = context.getBean("user",User.class);
        System.out.println(user);
        user.add();
    }

}
运行结果：
com.spring.User@506ae4d4
add......
```





## 二、IOC框架

### 1.IOC底层原理

**（1)什么是IOC**

​		1）控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理

​		2）使用IOC目的：为了耦合度降低

​		3）做入门案例就是IOC实现

**（2）原理**

​	1）xml解析、工厂模式、反射

​	![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927181524357-305090689.png)

![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927181642790-1322405480.png)

**IOC过程：**

1. 第一步xml配置文件，配置创建的对象 

```java
 <bean id="user" class="com.spring.User"></bean>
```

2. 有service类和dao类，创建工厂类

   ```java
   class UserFactory{
       public static UserDao getDao(){
           String classValue = class属性值;//xml解析
           Class clazz = Class.forName(classValue);//通过反射创建对象
           return (UserDao)clazz.newInstance();
       }
   }
   ```

   

   



###   2.IOC接口（BeanFactory）

**（1）IOC思想基于IOC容器完成，IOC容器底层就是对象工厂**

**（2）Spring提供IOC容器实现两种方式：（两个接口）**

​	1）BeanFactory：IOC容器基本实现方式，是Spring内部的使用接口，不提供开发人员进行使用

​	*加载配置文件的时候不会创建对象，在获取对象（使用）时才创建*

​	2）ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用

​	*加载配置文件时候就会把在配置文件的对象进行创建*

**（3）ApplicationContext接口实现类**

![image](https://img2022.cnblogs.com/blog/2682250/202209/2682250-20220927205354222-2021812082.png)



### 3.IOC操作Bean管理（基于xml方式）

**（1）什么是Bean管理**

​	1）Spring创建对象

​	2）Spring注入属性

**（2）Bean管理操作有两种方式**

​	1)基于xml配置文件方式实现

​	2）基于注解方式实现

**（3）基于xml方式创建对象**

```java
 <!--配置User对象创建-->
    <bean id="user" class="com.spring.User"></bean>
```

​	1)在spring配置文件中，使用bean标签，标签里面添加对应属性，就可以实现对象创建

​	2)在bean标签有很多属性，介绍常用的属性

```java
id属性：唯一标识
class属性：类全路径（包类路径）
```

​	3）创建对象的时候，默认也是执行无参构造方法完成对象的创建

**（4）基于xml方式注入属性**

​	1）DI:依赖注入，就是注入属性

​	:star:**第一种注入方式：使用set方法注入**

```java
package com.spring;

public class Book {
    private String bname;

    public void setBname(String bname) {
        this.bname = bname;
    }
}

```

xml:

```java
  <!--set方法注入属性-->
    <bean id="book" class="com.spring.Book">
        <!--使用property完成属性注入
        name：类里面属性名称
        value：向属性注入的值-->
    <property name="bname" value="shmily"></property>
    </bean>
```

Test：

```java
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
运行结果：
    Book{bname='shmily'}
```



​	:star:**第二种注入方式：使用有参构造进行注入：**

​	1）创建类，定义属性，创建属性对应有参构造方法

```java
    private String oname;
    private String address;
    
    //有参构造
    public Orders(String oname, String address) {
        this.oname = oname;
        this.address = address;
    }
```

​	2）在Spring配置文件中进行配置

```java
  <!--有参数构造注入属性-->
    <bean id="orders" class="com.spring.Orders">
        <constructor-arg name="oname" value="PC"></constructor-arg>
        <constructor-arg name="address" value="china"></constructor-arg>
        还有个索引注入 <constructor-arg index="0" value="china"></constructor-arg>，"0"是下标
    </bean>
```

​	Test:

```java
public class TestSpring {
    @Test
    public void OrdersTest(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //2.获取配置创建的对象
        Orders orders = context.getBean("orders",Orders.class);
        System.out.println(orders);
        orders.toString();
    }
}

运行结果：
    Orders{oname='PC', address='china'}
```



:star:**第三种：p名称空间注入**

​	1）使用p名称空间注入，可以简化基于xml配置方式

​		①添加p名称空间在配置文件中：

```java
    xmlns:p="http://www.springframework.org/schema/p"
```

​		②进行属性注入，在bean标签里面进行操作

```java
    <bean id="book" class="com.spring.Book" p:bname="书"></bean>
```



### 4.IOC操作Bean管理(xml其他类型属性)

​	**（1）字面量**

​		1）null值

```java
<!--null值-->
     <property name="address">
    <null/>
    </property>
```

​		2）属性值包含特殊符号

```java
<!--属性值包含特殊符号
    1.把<>进行转义
    2.把带特殊符号内容写到CDATA
    -->
     <property name="address">
    	<value><![CDATA[<<南京>>]]</value>
        </property>
```

**（2）注入属性--外部bean**

​	1）创建两个类service类和dao类

​	2）在service调用dao里面的方法

```java
public class UserService {
    public void add(){
        System.out.println("service add......");
    }

    //原始方式：创建UserDao对象
    /*UserDao userDao = new UserDaoImpl();
            userDao.update();*/

    //创建UserDao类型属性，生成set方法
    private UserDao userDao;

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}

```



​	3）在spring配置文件进行配置

```java
<!--    1.service和dao对象创建-->
    <bean id="userService" class="com.spring.service.UserService">
    <!--注入userDao对象
        name属性：类里面属性名称
        ref属性：创建userDao对象bean标签id值
        -->
    <property name="userDao" ref="userDaoImpl"></property>
    </bean>
    <bean id="userDaoImpl" class="com.spring.dao.UserDaoImpl"></bean>
```

```java
 @Test
    public void testAdd(){
        //1.加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        //2.获取配置创建的对象
        UserService userService = context.getBean("userService",UserService.class);
        userService.add();
    }
```



**（3）注入属性--内部bean和级联赋值**

​	1)一对多关系：部门和员工

​	一个部门有多个员工，一个员工属于一个部门

​	部门是一，员工是多

​	2）在实体类之间表示表示一对多关系，员工表示所属部门，使用对象类型属性进行表示

```java
//部门类
public class Dept {
    private String dname;

    public void setDname(String dname) {
        this.dname = dname;
    }
}

```



```java
//员工类
public class Emp {
    private String ename;
    private String gender;

    //员工属于某一个部门，使用对象形式表示
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
```

​	3)在spring配置文件进行配置

```java
    <!--内部bean-->
    <bean id="emp" class="com.spring.bean.Emp">
        <!--设置两个普通属性-->
        <property name="ename" value="shmily"></property>
        <property name="gender" value="男"></property>
        <!--设置对象类型属性-->
        <property name="dept">
            <bean id="dept" class="com.spring.bean.Dept">
                <property name="dname" value="部门"></property>
            </bean>
        </property>
    </bean>
```

4)注入属性--级联赋值

1. 第一种：

```java
<!--级联赋值-->
    <bean id="emp" class="com.spring.bean.Emp">
    <!--设置两个普通属性-->
    <property name="ename" value="shmily"></property>
    <property name="gender" value="男"></property>
        
        <!--级联赋值-->
        <property name="dept" ref="dept"></property>
    </bean>
    <bean id="dept" class="com.spring.bean.Dept">
        <property name="dname" value="部门"></property>
    </bean>
```



2. 第二种：

```java
  <!--级联赋值-->
    <bean id="emp" class="com.spring.bean.Emp">
    <!--设置两个普通属性-->
    <property name="ename" value="shmily"></property>
    <property name="gender" value="男"></property>
        
        <!--级联赋值,用dept.dname时要生成get方法-->
        <property name="dept" ref="dept"></property>
        <property name="dept.dname" value="部门"></property>
    </bean>
    <bean id="dept" class="com.spring.bean.Dept">
        <property name="dname" value="部门"></property>
    </bean>
```



### 5.IOC操作Bean管理（xml注入集合属性）

**（1）注入数组类型属性**

**（2）注入List集合类型属性**

**（3）注入Map集合类型属性**

​		1）创建类，定义数组、list、map、set类型属性，生成对应set方法

```java
package com.spring;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stu {
    //1.数组类型属性
    private String[] courses;

    //2.List集合类型属性
    private List<String >list;

    //3.Map集合类型属性
    private Map<String,String> maps;

    //4.set集合类型属性
    private Set<String> sets;
    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
}

```

​	2)在spring配置文件配置

```java
    <!--集合类型属性注入-->
    <bean id="stu" class="com.spring.Stu">
        <!--数组类型属性注入-->
        <property name="courses">
            <array>
                <value>java课程</value>
                <value>MySQL</value>
            </array>
        </property>
<!--        或者:list属性注入-->
        <property name="list">
            <list>
                <value>D</value>
                <value>F</value>
            </list>
        </property>
<!--        map类型属性注入-->
        <property name="maps">
            <map>
                <entry key="JAVA" value="java"></entry>
                <entry key="C" value="c"></entry>
            </map>
        </property>
<!--        set类型属性注入-->
        <property name="sets">
            <set>
                <value>MySQL</value>
                <value>Redis</value>
            </set>
        </property>
    </bean>
```

Test：

```java
package testdemo;

import com.spring.Stu;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StuTest {
    @Test
    public void testCollection(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        Stu stu = context.getBean("stu",Stu.class);
        stu.test();
    }
}
运行结果：
[java课程, MySQL]
[D, F]
{JAVA=java, C=c}
```

**（4）在集合里面这种对象类型值**

```java
       <!--创建多个course对象-->
        <bean id="course1" class="com.spring.Course">
            <property name="cname" value="名字1"></property>
        </bean>
    <bean id="course2" class="com.spring.Course">
        <property name="cname" value="名字2"></property>
    </bean>
        <!--        注入list集合类型，值是对象-->
        <property name="courseList">
            <list>
                <ref bean="course1"></ref>
                <ref bean="course2"></ref>
            </list>
        </property>
    </bean>
```



**（5）把集合注入部分提取出来**

​		1)在spring配置文件中引用名称空间util

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd">

</beans>
```

​		2)使用util标签完成list集合注入

```java
<!--    1.提取list集合类型属性注入-->
    <util:list id="bookList">
        <value>如何阅读一本书</value>
        <value>java</value>
        <value>C</value>
    </util:list>
<!--    2.提取list集合类型属性注入使用-->
    <bean id="book" class="com.spring.Book">
        <property name="list" ref="bookList"></property>
    </bean>
```



### 6.IOC操作Bean管理（FactoryBean）

**（1）Spring有两种类型bean：一种普通bean，另一种工厂bean（FactoryBean）**

**（2）普通bean：在配置文件中定义bean类型就是返回类型**

**（3）工厂bean：在配置文件定义bean类型可以和返回类型不一样**

​		1）第一步 创建类，让这个类作为工厂bean，实习接口FactoryBean

​		2）第二步 实现接口里面的方法，在实现的方法中定义返回的bean类型

MyBean.java

```java
package com.factorybean;

import com.spring.Course;
import org.springframework.beans.factory.FactoryBean;

public class MyBean implements FactoryBean<Course> {
    //定义返回bean
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setCname("dzq");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

```

Test:

```java
package testdemo;

import com.spring.Course;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class factorybeanTest {
    @Test
    public void testCollection1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
        Course course = context.getBean("myBean", Course.class);
        System.out.println(course);
    }
}
```

配置文件：

```java
<bean id="myBean" class="com.factorybean.MyBean">

    </bean>
```



### 7.IOC操作Bean管理（bean作用域）

**（1）在Spring里面，设置创建bean实例时单实例还是多实例**

**（2）在Spring里面，默认情况下bean是单实例对象**

```java
    @Test
    public void testCollection2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        Book book1 = context.getBean("book",Book.class);
        Book book2 = context.getBean("book",Book.class);
        System.out.println(book1);
        System.out.println(book2);
    }

运行结果：
com.spring.Book@6cb107fd
com.spring.Book@6cb107fd
    
 因为book1和book2的地址相同，所以他们是单实例对象
```

**(3)如何设置单实例还是多实例**

​	1）在spring配置文件bean标签里面有属性（scope）用于设置单实例还是多实例

​	2）scope属性值

第一个值 默认值：singleton，表示单实例对象

第二个值 prototype，表示多实例对象

```java
 <bean id="book" class="com.spring.Book" scope="prototype">
        <property name="list" ref="bookList"></property>
    </bean>
运行结果：
com.spring.Book@710636b0
com.spring.Book@3de8f619
   
 它们地址不同，现在是多实例对象  
 
```

​	3）singleton和prototype区别

​		1.singleton单实例，prototype多实例

​		2.设置scope值是singleton时候，加载spring配置文件的时候就会创建单实例对象

​			设置scope值是prototype时候，不是在加载spring配置文件时候创建对象，在调用getBean方法时候创建多实例对象

​		了解：request：每一次HTTP请求都会创建一个新的bean实例，该bean仅在当前HTTP request内有效，在请求完成后，bean会失效并被垃圾回收器回收。

​					session：每一次HTTP请求都会创建一个新的bean，该bean仅在当前HTTP session内有效。同一个session会话共享一个实例，不同的会话使用不同的实例。

### 8.IOC操作Bean管理（bena生命周期）

**（1）生命周期**

​	1）从对象创建到对象销毁的过程

**（2）bean生命周期**

​	1）通过构造器创建bean实例（无参数构造）

​	2）为bean的属性设置值和对其他bean引用（调用set方法）

​	3）调用bean的初始化的方法（需要进行配置初始化的方法）

​	4）bean可以使用了（对象获取到了）

​	5）当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

**（3）演示bean的生命周期**

例子：

```java
package com.bean;

public class Orders {


    private String oname;

    @Override
    public String toString() {
        return "Orders{" +
                "oname='" + oname + '\'' +
                '}';
    }

    //1.无参构造
    public Orders() {
        System.out.println("第一步 执行无参数构造创建bean实例");
    }

    public void setOname(String oname) {
        this.oname = oname;
        System.out.println("第二步 调用set方法设置属性值");
    }

    //创建执行的初始化的方法
    private void initMethod(){
        System.out.println("第三步 执行初始化的方法");
    }

    //创建执行的销毁的方法
    public void destroyMethod(){
        System.out.println("第五步 执行销毁的方法");
    }
}


```

配置文件：

```java
 <bean id="orders" class="com.bean.Oreders" init-method="initMethod" destroy-method="destroyMethod">
        <property name="oname" value="手机"></property>
    </bean>
```

Test：

```java
public class OrdersTest {
    @Test
    public void orderTset(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println("第四步 获取创建bean实例对象");
        System.out.println(orders);


        //手动让bean实例销毁
        context.close();
    }

    
运行结果：
第一步 执行无参数构造创建bean实例
第二步 调用set方法设置属性值
第三步 执行初始化的方法
第四步 获取创建bean实例对象
Orders{oname='手机'}
第五步 执行销毁的方法
```



**（4）bean的后置处理器，bean的生命周期有七步**

​	1）通过构造器创建bean实例（无参数构造）

​	2）为bean的属性设置值和对其他bean引用（调用set方法）

​	3）把bean实例传递给bean后置处理器的方法postProcessBeforeInitialization

​	4）调用bean的初始化的方法（需要进行配置初始化的方法）

​	5）把bean实例传递给bean后置处理器的方法postProcessAfterInitialization

​	6）bean可以使用了（对象获取到了）

​	7）当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

**（5）演示添加后置处理器的效果**

​	1）创建类、实现接口BeanPostProcessor，创建后置处理器

MyBeanPost.java

```java
package com.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPost implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第三步 在初始化之前执行的方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第五步 在初始化之后执行的方法");
        return bean;
    }
}


```

Orders.java

```java
package com.bean;

public class Orders {


    private String oname;

    @Override
    public String toString() {
        return "Orders{" +
                "oname='" + oname + '\'' +
                '}';
    }

    //1.无参构造
    public Orders() {
        System.out.println("第一步 执行无参数构造创建bean实例");
    }

    public void setOname(String oname) {
        this.oname = oname;
        System.out.println("第二步 调用set方法设置属性值");
    }

    //创建执行的初始化的方法
    private void initMethod(){
        System.out.println("第四步 执行初始化的方法");
    }

    //创建执行的销毁的方法
    public void destroyMethod(){
        System.out.println("第七步 执行销毁的方法");
    }
}

```

配置文件：

```java
    <bean id="orders" class="com.bean.Orders" init-method="initMethod" destroy-method="destroyMethod">
        <property name="oname" value="手机"></property>
    </bean>

<!--    配置后置处理器-->
    <bean id="myBeanPost" class="com.bean.MyBeanPost"></bean>
```

Test:

```java
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


运行结果
第一步 执行无参数构造创建bean实例
第二步 调用set方法设置属性值
第三步 在初始化之前执行的方法
第四步 执行初始化的方法
第五步 在初始化之后执行的方法
第六步 获取创建bean实例对象
Orders{oname='手机'}
第七步 执行销毁的方法
```



### 9.IOC操作Bean管理（xml自动装配）

**（1）什么是自动装配**

​	1）根据指定装配规则（属性名称或者属性类型），Spring自动匹配属性值进行注入

**（2）演示自动装配过程**

​	1）根据属性名称自动注入

配置：

```java
<!--手动装配-->
<!--    <bean id="emp" class="com.autowire.Emp">-->
<!--        <property name="dept" ref="dept"></property>-->
<!--    </bean>-->
<!--    <bean id="dept" class="com.autowire.Dept"></bean>-->

<!--    实现自动装配
        bean标签属性autowire，配置自动装配
        autowire属性常用两个值:
            byName根据属性名称注入，注入值bean的id值和类属性名称一样
            byType根据属性类型注入
-->
    <bean id="emp" class="com.autowire.Emp" autowire="byName">
    </bean>
    <bean id="dept" class="com.autowire.Dept"></bean>

    
```

Emp.java

```java
package com.autowire;
//员工
public class Emp {

    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept +
                '}';
    }
    public void test(){
        System.out.println(dept);
    }
}

```

Dept.java

```java
package com.autowire;
//部门
public class Dept {
    @Override
    public String toString() {
        return "Dept{}";
    }
}

```

Test:

```java
public class TestSpring {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp);
    }
}


运行结果：
 Emp{dept=Dept{}}
```

​	2）根据属性类型自动注入

```java
 <bean id="emp" class="com.autowire.Emp" autowire="byType">
    </bean>
    <bean id="dept" class="com.autowire.Dept"></bean>

```



### 10.IOC操作Bean管理（外部属性文件）

**(1)直接配置数据库信息**

​	1）配置Druid（德鲁伊）连接池

​	2）引入Druid连接池jar包（下载地址：[[Central Repository: com/alibaba/druid (maven.org)](https://repo1.maven.org/maven2/com/alibaba/druid/)](https://druid.apache.org/downloads.html)）

​	配置文件：

```java
<!--    直接配置连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/book"></property>
        <property name="username" value="root"></property>
        <property name="password" value=""></property>
    </bean>
```



**（2）引入外部属性文件配置数据库连接池**

​	1）创建外部属性文件，properties格式文件，写数据库信息

```java
prop.driverClass=com.mysql.jdbc.Driver
prop.url=jdbc:mysql://localhost:3306/book
prop.username=root
prop.password=""
```

​	2）把外部properties属性文件引入到spring配置文件中

​		:black_circle:引入context命名空间

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 			  http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

```

​		:black_circle:在spring配置文件使用标签引入外部属性文件

```java
<!--    引入外部属性文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
        <!--  配置连接池-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverClass}"></property>
            <property name="url" value="${prop.url}"></property>
            <property name="username" value="${prop.username}"></property>
            <property name="password" value="${prop.password}"></property>
        </bean>
```



### 11.IOC操作Bean管理（基于注解方式）

**（1）什么是注解**

​	1）注解是代码特殊标记，格式：@注解名称（属性名称=属性值，属性名称=属性值...）

​	2）使用注解，注解可以作用在类、方法、属性上面

​	3）使用注解目的：简化xml配置

**（2）Spring针对Bean管理创建对象提供注解**

​	1）@Component

​	2）@Service

​	3）@Controller

​	4）@Repository

​	*上面四种注解功能是一样的，都可以用来创建bean实例*

**（3）基于注解方式实现对象创建**

​	1）引入依赖：spring-aop-5.3.9.jar

​	2）开启组件扫描

```java
<!--    开启组件扫描
          如果扫描多个包,多个包用逗号隔开或者扫描包上层目录
-->
    <context:component-scan base-package="com"></context:component-scan>
```

​	3)创建类，在类上面添加对象注解

```java
package com.service;

import org.springframework.stereotype.Component;

/*
在注解里面value属性值可以省略不写
默认值是类名称,首字母小写
UserService-->userService
@Component可以替代为@Service,@Controller,@Repository,但在此一般习惯于用@Component
 */
@Component(value = "userService")//<bean id="userService" class=""/>
public class UserService {

    public void add(){
        System.out.println("service add...");
    }
}


```



**（4）开启组件扫描细节配置**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/p http://www.springframework.org/schema/p/spring-p.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

<!--    开启组件扫描
          如果扫描多个包,多个包用逗号隔开或者扫描包上层目录
-->
    <context:component-scan base-package="com"></context:component-scan>

    
<!--    实例1:use-default-filters="false" 表示现在不使用默认filter,自己配置filter
             context:include-filter 表示扫描哪些内容
-->
    <context:component-scan base-package="com" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--    实例2:
            下面配置扫描包所有内容
            context:exclude-filter  设置哪些内容不进行扫描
-->
    <context:component-scan base-package="com">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
</beans>
```

**（5）基于注解方式实现属性注入**

​	1）@AutoWired ：根据属性类型进行自动装配

​		①把service和dao对象创建，在service和dao类添加创建对象

​		②在service注入dao对象，在service类添加dao类型dao类型属性，在属性上面使用注解

UserService.java

```java
package com.service;

import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //定义dao类型属性,不需要添加set方法,添加注入属性注解
    @Autowired //根据类型进行注入
    private UserDao userDao;

    public void add(){
        System.out.println("service add...");
        userDao.add();
    }
}

```

UserDao.java

```java
package com.dao;

public interface UserDao {
    public void add();
}

```

UserDaoImpl.java

```java
package com.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public void add(){
        System.out.println("dao add...");
    }
}

```

Test:

```java
    @Test
    public void userTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        UserService userService=context.getBean("userService", UserService.class);
        userService.add();
    }
}
运行结果；
service add...
dao add...
```

​	2）@Qualifier：根据属性名称进行注入

​	@Qualifier注解的使用，和@AutoWired一起使用

UserService.java

```java
@Service
public class UserService {

    //定义dao类型属性,不需要添加set方法,添加注入属性注解
    @Autowired //根据类型进行注入
    @Qualifier(value = "userDaoImpl1") //根据名称进行注入
    private UserDao userDao;

    public void add(){
        System.out.println("service add...");
        userDao.add();
    }
}

```

UserDaoImpl.java

```java
@Repository(value = "userDaoImpl1")
public class UserDaoImpl implements UserDao{
    @Override
    public void add(){
        System.out.println("dao add...");
    }
}

```



​	3）@Resource：可以根据类型注入，也可以根据名称注入

```java
  @Service
public class UserService {
	//@Resource()  //根据类型进行注入
    @Resource(name = "userDaoImpl1") //根据名称进行注入
    private UserDao userDao;

    public void add(){
        System.out.println("service add...");
        userDao.add();
    }
}

```

​	4）@Value：注入普通类型属性

```java
  @Value(value = "DF")
    private String name;
```



**（6）完全注解开发**

​	1）创建配置类，替代xml配置文件

SpringConfig.java

```java
package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //作为配置类,替代xml配置文件
@ComponentScan(basePackages = {"com"})
public class SpringConfig {

}

```

​	2）编写测试类

```java
package com.testdemo;

import com.config.SpringConfig;
import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpring {
    @Test
    public void userTest(){
        //加载配置类
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService=context.getBean("userService", UserService.class);
        userService.add();
    }
}


```





## 三、AOP

### 1.概念

**（1）什么是AOP**

​	1）面向切面编程（方面），利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

​	2）通俗描述：不通用修改源代码方式，在主干功能里添加新功能

​	3）使用登录例子说明AOP

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221001115412766-963322768.png)



### 2.AOP（底层原理）

**（1）AOP底层使用动态代理**

​	1）有两种情况动态代理

​		①有接口情况，使用JDK动态代理

​		:black_circle:创建接口实现类代理对象，增强类的方法

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221001121025490-392177822.png)

​		②没有接口情况，使用CGLTB动态代理

​		:black_circle:创建子类的代理对象，增强类的方法

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221001121715949-3740712.png)

### 3.AOP（JDK动态代理）

**（1）使用JDK动态代理，使用proxy类里面的方法创建代理对象**

```java
java.lang.reflect 
Class Proxy
java.lang.Object 
java.lang.reflect.Proxy 

```

​	1）调用newProxyInstance方法

```java

 static Object    newProxyInstance(ClassLoader loader, 类<?>[] interfaces, InvocationHandler h)  
    			  返回指定的接口，将方法调用指定的调用处理程序的代理类的一个实例。 
```

​	方法中的三个参数：

| ClassLoader loader      | 类加载器                                                     |
| ----------------------- | ------------------------------------------------------------ |
| **类<?>[] interfaces**  | **增强方法所在的类，这个类实现的接口，支持多个接口**         |
| **InvocationHandler h** | **实现这个接口InvocationHandler，创建代理对象，写增强的方法** |

**（2）编写JDK动态代码**

​	1）创建接口，定义方法

UserDao.java

```java
package com.spring;

public interface UserDao {
   public int add(int a,int b);
   public String update(String id);
}

```

​	2）创建接口实现类，实现方法

UserDaoImpl.java

```java
package com.spring;

public class UserDaoImpl implements UserDao{
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public String update(String id) {
        return id;
    }
}

```

​	3）使用Proxy类创建接口代理对象

JDKProxy.java

```java
package com.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        //第一种:创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });
        //第二种:自定义名称UserDaoProxy创建代理对象代码
        UserDaoImpl userDao = new UserDaoImpl();
     UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
     int result = dao.add(1,2);
        System.out.println("result:"+result);
   }
}
//第二种:自定义名称UserDaoProxy创建代理对象代码
class  UserDaoProxy implements InvocationHandler{

    /*
    1.把创建的是谁的代理对象,把谁传递过来
    有参构造传递
     */
    private Object obj;
    public UserDaoProxy(Object obj){
        this.obj = obj;
    }

    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //方法之前
        System.out.println("在方法之前执行..."+method.getName()+":传递的参数:"+ Arrays.toString(args));

        //被增强的方法执行
        Object res = method.invoke(obj, args);

        //方法之后
        System.out.println("在方法之后执行..."+obj);
        return res;
    }
}


运行结果：
在方法之前执行...add:传递的参数:[1, 2]
add方法执行了...
在方法之后执行...com.spring.UserDaoImpl@7ca48474
result:3
```



### 4.AOP（术语）

**（1）连接点**

​	类里面哪些方法可以被增强，这些方法称为连接点

**（2）切入点**

​	实际被真正增强的方法，称为切入点

**（3）通知（增强）**

​	1）实际增强的逻辑部分称为通知（增强）

​	2）通知有多种类型

​		①前置通知

​		②后置通知

​		③环绕通知

​		④异常通知

​		⑤最终通知 finally

**（4）切面**

​	是动作，把通知应用到切入点的过程

### 5.AOP（准备）

**（1）Spring框架一般基于AspectJ实现AOP操作**

​	1）什么是AspectJ

AspectJ不是Spring组成部分，独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作

**（2）基于AspectJ实现AOP操作**

​	1）基于xml配置文件

​	2）基于注解方式实现（使用）

**（3）在项目工程里面引入AOP相关依赖**

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221001140726359-891616200.png)

**（4）切入点表达式**

​	1）切入点表达式的作用：知道对哪个类里面的哪个方法进行增强

​	2）语法结构：

​		execution([权限修饰符] [返回类型] [类全路径] [方法名称] [参数列表])

举例1：对com.spring.UserDao类里面的add进行增强

​	execution(* com.spring.UserDao.add(...))



举例2：对com.spring.UserDao类里面的所有方法进行增强

​		execution(\* com.spring.UserDao.\*(...))



举例3：对com.spring包里面所有类，类里面的所有方法进行增强

​		execution(\* com.spring.\*.\*(...))



### 6.AOP操作（AspectJ注解）

**（1）创建类，在类里面定义方法**

User.java

```java
package com.aop;

public class User {
    public void add(){
        System.out.println("add...");
    }
}

```



**（2）创建增强类（编写增强逻辑）**

​	1）在增强类中创建方法，让不同方法代表不同通知类型

UserProxy.java

```java
package com.aop;
//增强的类
public class UserProxy {

    //前置通知
    public void before(){
        System.out.println("before...");
    }
}

```

**（3）进行通知的配置**

​	1）在spring配置文件中，开启注解扫描

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd ">
    
<!--    开启注解扫描-->
    <context:component-scan base-package="com.aop"></context:component-scan>
</beans>
```



​	2）使用注解创建User和UserProxy对象

User.java

```java
//被增强的类
@Component
public class User {
    public void add(){
        System.out.println("add...");
    }
}

```

UserProxy.java

```java
//增强的类
@Component
public class UserProxy {

    //前置通知
    public void before(){
        System.out.println("before...");
    }
}

```



​	3）在增强类上面添加注解@Aspect

UserProxy.java

```java
//增强的类
@Component
@Aspect //生成代理对象
public class UserProxy {

    //前置通知
    public void before(){
        System.out.println("before...");
    }
}

```



​	4）在spring配置文件中开启生成代理对象

xml:

```java
<!--    开启aspect生成代理对象-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

```

**（4）配置不同类型的通知**

​	1）在增强类的里面，在作为通知方法上面添加通知类型注解，使用切入点表达式配置

UserProxy.java

```java
package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//增强的类
@Component
@Aspect //生成代理对象
public class UserProxy {

    //前置通知
    //@Before注解表示作为前置通知
    @Before(value = "execution(* com.aop.User.add(..))")
    public void before(){
        System.out.println("before...");
    }

    //最终通知
    @After(value = "execution(* com.aop.User.add(..))")
    public void after(){
        System.out.println("after...");
    }


    //后置通知（返回通知）
    @AfterReturning(value = "execution(* com.aop.User.add(..))")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }


    //异常通知
    @AfterThrowing (value = "execution(* com.aop.User.add(..))")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }

    //环绕通知
    @Around(value = "execution(* com.aop.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("环绕之前...");

        //被增强的方法执行
        proceedingJoinPoint.proceed();

        System.out.println("环绕之后...");
    }
}



```

Test：

```java
package com.testdemo;

import com.aop.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {
    @Test
    public void testAop(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }
}

运行结果：
    
环绕之前...
before...
add...
afterReturning...
after...
环绕之后...
```



**（5）相同的切入点抽取**

```java
    //相同切入点抽取
    @Pointcut(value = "execution(* com.aop.User.add(..))")
    public void pointcut(){
    }

    //前置通知
    //@Before注解表示作为前置通知
    @Before(value = "pointcut")
    public void before(){
        System.out.println("before...");
    }

```



**（6）有多个增强类多个同一方法进行增强，设置增强类优先级**

​	1）在增强类上面添加注解@Order（数字类型值），数字类型值越小优先级越高

```java
@Component
@Aspect
@Order(1)
public class PersonProxy {

    @Before(value = "execution(* com.aop.User.add(..))")
    public void afterReturning(){
        System.out.println("Person Before...");
    }
}

```



**（7）完全使用注解开发**

​	1）创建配置类，不需要创建xml配置文件

```java
@Configuration
@ComponentScan(basePackages ="com.aop" )
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigAop {

}

```



### 7.AOP操作（AspectJ配置文件）

**（1）创建两个类，增强类和被增强类，创建方法**

Book.java

```java
package com.aopxml;
//被增强类
public class Book {
    public void buy(){
        System.out.println("buy...");
    }
}

```

BookProxy.java

```java
package com.aopxml;
//增强类
public class BookProxy {
    public void before(){
        System.out.println("before...");
    }
}

```



**（2）在spring配置文件中创建两个类对象**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

<!--    创建对象-->
    <bean id="book" class="com.aopxml.Book"></bean>
    <bean id="bookProxy" class="com.aopxml.BookProxy"></bean>
</beans>
```



**（3）在spring配置文件中配置切入点**

```java
<!--    配置aop增强-->
    <aopa:config>
<!--        切入点-->
        <aopa:pointcut id="point" expression="execution(* com.aopxml.Book.buy(..))"/>
<!--        配置切面-->
        <aopa:aspect ref="bookProxy">
<!--            配置增强作用在具体的方法上-->
            <aopa:before method="before" pointcut-ref="point"/>
        </aopa:aspect>
    </aopa:config>
```



Test:

```java
    @Test
    public void testAop1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        Book book = context.getBean("book", Book.class);
        book.buy();
    }

运行结果：
    
before...
buy...
```





## 四、JdbcTemplate

### 1.什么是JdbcTemplate

​	Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库操作

### 2.准备工作

**（1）引入相关jar包**

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221002173712424-1643864169.png)



**(2)在spring配置文件中配置数据库连接池**

```java
<!--    数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <property name="url" value="jdbc:mysql://localhost:3306/book"></property>
        <property name="username" value="root"></property>
        <property name="password" value=""></property>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
    </bean>
```



**（3）配置JdbcTemplate对象，注入DataSource**

```java
<!--    JdbcTemplate对象-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
<!--        注入dataSource-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```



**（4）创建sercvice类，创建dao类，在dao注入jdbcTemplate对象**

​	:black_circle:配置文件

```java
<!--    开启组件扫描-->
    <context:component-scan base-package="com"></context:component-scan>

```

​	:black_circle:service

```java
package com.service;

import com.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    //注入dao
    @Autowired
    private BookDao bookDao;
}

```

:black_circle:Dao

```java
package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao{

    //注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

}

```



### 3.JdbcTemplate操作数据库（添加）

**（1）对应数据库表创建实体类**

User.java

```java
package com.entity;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

```

**（2）编写service和dao**

​	1）在dao进行数据库添加操作

​	2）调用JdbcTemplate对象里面update方法实现添加操作

```java
update(String sql,Object...args)
```

有两个参数：

:black_circle:第一个参数：sql语句

:black_circle:第二个参数：可变参数，设置sql语句值

BookDaoImpl.java

```java
package com.dao;

import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BookDaoImpl implements BookDao{

    //注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加的方法
    @Override
    public void add(Book book) {
        //1.创建sql语句
        String sql = "insert into t_user values(?,?,?)";
        //2.调用方法实现
       int update = jdbcTemplate.update(sql,book.getId(),book.getUsername(),book.getPassword(),book.getEmail());
        System.out.println(update);
    }
}

```

BookService.java

```java
package com.service;

import com.dao.BookDao;
import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BookService {

    //注入dao
    @Autowired
    private BookDao bookDao;

    //添加的方法
    public void addBook(Book book){
        bookDao.add(book);
    }

}

```

BookDao.java

```java
package com.dao;


import com.entity.Book;

public interface BookDao {
    //添加的方法
    void add(Book book);
}

```

Book.java

```java
package com.entity;

public class Book {
    private String id;
    private String username;
    private String password;
    private String email;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

```

**（3）测试类**

```java
package com.test;

import com.entity.Book;
import com.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TsetBook {

    @Test
    public void testJdbcTemplate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService",BookService.class);
        Book book = new Book();
        book.setId("7");
        book.setUsername("c");
        book.setPassword("c");
        book.setEmail("123@qq.com");
        bookService.addBook(book);
    }
}

运行结果：
10月 02, 2022 7:44:51 下午 com.alibaba.druid.pool.DruidDataSource info
信息: {dataSource-1} inited
1

```

### 4.JdbcTemplate操作数据库（修改和删除）

BookService.java

```java
package com.service;

import com.dao.BookDao;
import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BookService {

    //注入dao
    @Autowired
    private BookDao bookDao;

    //添加的方法
    public void addBook(Book book){
        bookDao.add(book);
    }


    //修改的方法
    public void updateBook(Book book){
        bookDao.update(book);
    }

    //删除的方法
    public void deleteBook(String id){
        bookDao.delete(id);
    }
}

```

BookDao.java

```java
package com.dao;


import com.entity.Book;

public interface BookDao {
    //添加的方法
    void add(Book book);

    void update(Book book);

    void delete(String id);


}

```

BookDaoImpl.java

```java
package com.dao;

import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BookDaoImpl implements BookDao{

    //注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加的方法
    @Override
    public void add(Book book) {
        //1.创建sql语句
        String sql = "insert into t_user values(?,?,?,?)";
        //2.调用方法实现
       int update = jdbcTemplate.update(sql,book.getId(),book.getUsername(),book.getPassword(),book.getEmail());
        System.out.println(update);
    }

    //修改
    @Override
    public void update(Book book) {
        String sql = "update t_user set username=?,password=?,email=?,where id=?";
        Object[] args = {book.getUsername(),book.getPassword(), book.getEmail(),book.getId()};
        int update = jdbcTemplate.update(sql,args);
        System.out.println(update);
    }

    //删除
    @Override
    public void delete(String id) {
        String sql = "delete from t_user where id=?";
        int update = jdbcTemplate.update(sql,id);
        System.out.println(update);
    }
}

```

Test:

```java
package com.test;

import com.entity.Book;
import com.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TsetBook {

    @Test
    public void testJdbcTemplate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService",BookService.class);

        //添加
//        Book book = new Book();
//        book.setId("7");
//        book.setUsername("c");
//        book.setPassword("c");
//        book.setEmail("123@qq.com");
//        bookService.addBook(book);

        //修改
//        Book book = new Book();
//        book.setId("7");
//        book.setUsername("python");
//        book.setPassword("p");
//        book.setEmail("1234@qq.com");
//        bookService.updateBook(book);
//

        //删除
        bookService.deleteBook("7");
    }
}
运行结果：
信息: {dataSource-1} inited
1
```

### 5.JdbcTempalte操作数据库（查询返回某个值）

**（1）查询表里面有多少条记录，返回是某个值**

**（2）使用JdbcTemplate实现查询返回某个值代码**

```java
queryForObject(String sql,Class<T> requiredType)
```

有两个参数：

:black_circle:第一个参数：sql语句

:black_circle:第二个参数：返回类型Class

BookDaoImpl.java

```java
package com.dao;

import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BookDaoImpl implements BookDao{

    //注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加的方法
    @Override
    public void add(Book book) {
        //1.创建sql语句
        String sql = "insert into t_user values(?,?,?,?)";
        //2.调用方法实现
       int update = jdbcTemplate.update(sql,book.getId(),book.getUsername(),book.getPassword(),book.getEmail());
        System.out.println(update);
    }

    //修改
    @Override
    public void update(Book book) {
        String sql = "update t_user set username=?,password=?,email=? where id=?";
        Object[] args = {book.getUsername(),book.getPassword(), book.getEmail(),book.getId()};
        int update = jdbcTemplate.update(sql,args);
        System.out.println(update);
    }

    //删除
    @Override
    public void delete(String id) {
        String sql = "delete from t_user where id=?";
        int update = jdbcTemplate.update(sql,id);
        System.out.println(update);
    }

    //查询表记录数
    @Override
    public int selectCount() {

        String sql = "select count(*) from t_user";
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }
}

```

BookService.java

```java
package com.service;

import com.dao.BookDao;
import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BookService {

    //注入dao
    @Autowired
    private BookDao bookDao;

    //添加的方法
    public void addBook(Book book){
        bookDao.add(book);
    }


    //修改的方法
    public void updateBook(Book book){
        bookDao.update(book);
    }

    //删除的方法
    public void deleteBook(String id){
        bookDao.delete(id);
    }

    //查询表记录数
    public int findCount() {
      return bookDao.selectCount();
    }
}

```

BookDao.java

```java
package com.dao;


import com.entity.Book;

public interface BookDao {
    //添加的方法
    void add(Book book);

    //修改的方法
    void update(Book book);

    //删除的方法
    void delete(String id);

    //查询表记录数
    int selectCount();

}

```

Test:

```java
package com.test;

import com.entity.Book;
import com.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TsetBook {

    @Test
    public void testJdbcTemplate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService",BookService.class);

        //添加
//        Book book = new Book();
//        book.setId("7");
//        book.setUsername("c");
//        book.setPassword("c");
//        book.setEmail("123@qq.com");
//        bookService.addBook(book);

        //修改
//        Book book = new Book();
//        book.setId("7");
//        book.setUsername("python");
//        book.setPassword("p");
//        book.setEmail("1234@qq.com");
//        bookService.updateBook(book);
//

        //删除
        //bookService.deleteBook("7");

        //查询返回某个值
        int count = bookService.findCount();
        System.out.println(count);
    }
}

运行结果：
信息: {dataSource-1} inited
5
```



### 6.JdbcTemplate操作数据库（查询返回对象）

**（1）场景：查询图书详情**

**（2）JdbcTemplate实现查询返回对象**

```java
queryForObject(String sql,RowMapper<T> rowMapper,Object... args)
```

有三个参数：

:black_circle:第一个参数：sql语句

:black_circle:第二个参数：RowMapper是接口，返回不同类型数据，使用这个接口里面实现类完成数据的封装

:black_circle:第三个参数：sql语句值

BookDaoImpl.java

```java
    //查询返回对象
    @Override
    public Book findBookInfo(String id) {
        String sql = "select * from t_user where id=?";
        //调用方法
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }
```

BookService.java

```java
//查询返回对象
    public Book findOne(String id){
        return bookDao.findBookInfo(id);
    }
```

BookDao.java

```java
 //查询返回对象
    Book findBookInfo(String id);
```

Test:

```java
     //查询返回对象
        Book book =bookService.findOne("1");
        System.out.println(book);


运行结果： 
信息: {dataSource-1} inited
Book{id='1', username='admin', password='123', email='test@book.com'}

```



### 7.JdbcTemplate操作数据库（查询返回集合）

**（1）场景：查询图书列表分页**

**（2）调用JdbcTemplate方法实现查询返回集合**

```java
query(String sql,RowMapper<T> rowMapper,Object... args)
```

有三个参数：

:black_circle:第一个参数：sql语句

:black_circle:第二个参数：RowMapper是接口，返回不同类型数据，使用这个接口里面实现类完成数据的封装

:black_circle:第三个参数：sql语句值

BookDaoImpl.java

```java
  //查询返回集合
    @Override
    public List<Book> findAllBook() {
        String sql = "select * from t_user";
        //调用方法
        List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return bookList;
    }
```



BookService.java

```java
 //查询返回集合
    public List<Book> findAll(){
        return bookDao.findAllBook();
    }
```

BookDao.java

```java
 //查询返回集合
    List<Book> findAllBook();
```

Test:

```java
   //查询返回集合
        List<Book> all = bookService.findAll();
        System.out.println(all);
    }
运行结果：
信息: {dataSource-1} inited
[Book{id='1', username='admin', password='123', email='test@book.com'}, Book{id='2', username='dd', password='123', email='123@test.com'}, Book{id='4', username='dd1', password='123', email='123@test.com'}, Book{id='5', username='hh', password='123', email='123@qq.com'}, Book{id='6', username='java', password='j', email='123@qq.com'}]

```



### 8.JdbcTemplate操作数据库（批量操作）

**（1）批量操作：操作表里面的多条数据**

**（2）JdbcTemplate实现批量添加操作**

```java
batchUpdate(String sql,List<Object[]> batchArgs)
```

有二个参数：

:black_circle:第一个参数：sql语句

:black_circle:第二个参数：List集合，添加多条记录数

BookDaoImpl.java

```java
 //批量添加
    @Override
    public void batchAddBook(List<Object[]> batchArgs) {
        String sql = "insert into t_user values(?,?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql,batchArgs);
        System.out.println(Arrays.toString(ints));
    }
```

BookService.java

```java
 //批量添加
    public void batchAdd(List<Object[]> batchArgs){
        bookDao.batchAddBook(batchArgs);
    }
```

BookDao.java

```java
//批量添加
    public void batchAddBook(List<Object[]> batchArgs);

```

Test:

```java
   //批量添加
       List<Object[]> batchArgs = new ArrayList<>();
       Object[] o1 = {"7","python","p","123@qq.com"};
       Object[] o2 = {"8","c#","cc","123@qq.com"};
       batchArgs.add(o1);
       batchArgs.add(o2);
       //调用批量添加
        bookService.batchAdd(batchArgs);
   
运行结果：
信息: {dataSource-1} inited
[1, 1]
```

**（3）JdbcTemplate实现批量修改操作**

BookDaoImpl.java

```java
 //批量修改
    @Override
    public void batchUpdateBook(List<Object[]> batchArgs) {
        String sql = "update t_user set username=?,password=?,email=? where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
```

BookService.java

```java
//批量修改
    public void batchUpdate(List<Object[]> batchArgs){
        bookDao.batchUpdateBook(batchArgs);
    }
```

BookDao.java

```java
 //批量修改
    void batchUpdateBook(List<Object[]> batchArgs);

```

Test:

```java

        //批量修改
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"ddd","d","520@qq.com","7"};
        Object[] o2 = {"fff","f","520@qq.com","8"};
        batchArgs.add(o1);
        batchArgs.add(o2);

        //调用批量修改
        bookService.batchUpdate(batchArgs);
   
运行结果：
信息: {dataSource-1} inited
[1, 1]
```

（**4）JdbcTemplate操作数据库（批量删除）**

BookDaoImpl.java

```java
//批量删除
    @Override
    public void batchDeleteBook(List<Object[]> batchArgs) {
        String sql = "delete from t_user where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
```

BookService.java

```java
 //批量修改
    public void batchDelete(List<Object[]> batchArgs){
        bookDao.batchDeleteBook(batchArgs);
    }
```

BookDao.java

```java
//批量删除
    void batchDeleteBook(List<Object[]> batchArgs);
```

Test:

```java

        //批量删除
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"2"};
        Object[] o2 = {"4"};
        batchArgs.add(o1);
        batchArgs.add(o2);

        //调用批量删除
        bookService.batchDelete(batchArgs);

运行结果:
信息: {dataSource-1} inited
[1, 1]

```



## 五、事物管理

### 1.事务概念

**（1）什么是事务**

​	1）事务是数据库操作最基本单元，逻辑上一组操作，要么都成功，如果有一个失败所有操作都失败

​	2）典型场景：银行转账



**（2）事务四个特性（ACID）**

​	1）原子性

​	2）一致性

​	3）隔离性

​	4）持久性



### 2.事务操作（搭建事务操作环境）

 ![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221002223750958-362991037.png)



**（1）创建数据库表，添加记录**

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221002225901614-51233300.png)

**（2）创建service，搭建dao，完成对象创建和注入关系**

​	1）service注入dao，在dao注入JdbcTemplate，在JdbcTemplate注入DataSource

​	UserService.java

```java
package com.service;

import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //注入aop
    @Autowired
    private UserDao userDao;
}

```

UserDaoImpl.java

```java
package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

}

```



**（3）在dao创建两个方法：多钱和少钱的方法，在service创建（转账的方法）**

UserService.java

```java
package com.service;

import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //注入aop
    @Autowired
    private UserDao userDao;

    //转账的方法
    public void accountMoney(){
        //dd少100
        userDao.reduceMoney();

        //df多100
        userDao.addMoney();
    }
}

```

UserDao.java

```java
package com.dao;

public interface UserDao {
    //多钱
    public void addMoney();
    //少钱
    public void reduceMoney();
}

```

UserDaoImpl.java

```java
package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //多钱
    @Override
    public void addMoney() {
        String sql = "update count set money=money+? where username=?";
        JdbcTemplate.update(sql,100,"df");
    }

    //少钱
    @Override
    public void reduceMoney() {
        String sql = "update count set money=money-? where username=?";
        JdbcTemplate.update(sql,100,"dd");
    }
}

```

Test:

```java
package com.test;

import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBook {
    @Test
    public void TsetUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService = context.getBean("userservice",UserService.class);
        userService.accountMoney();
    }
}

```

（4）上面代码，如果正常执行没有问题的，但是如果代码执行过程中出现异常，有问题

​	1）使用事务进行解决

​	2）事务操作过程

UserService.java

```java
package com.service;

import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //注入aop
    @Autowired
    private UserDao userDao;

    //转账的方法
    public void accountMoney() {
        try {
            //第一步 开启事务

            //第二步 进行业务操作


            //模拟异常
            int i = 1/0;
            
            //dd少100
            userDao.reduceMoney();

            //df多100
            userDao.addMoney();

            //第三步 没有发生异常，提交事务
        } catch (Exception e) {
            //第四步 出现异常，事务回滚

        }
    }

}

```



### 3.事务操作（spring事务管理介绍）

**（1）事务添加到JavaEE三层结构里面Service层（业务逻辑层）**

**（2）在Spring进行事务管理操作**

​	1）有两种方式：编程式事务管理和声明式事务管理（使用）

**（3）声明式事务管理**

​	1）基于注解方式（使用）

​	2）基于xml配置文件方式

**（4）在Spring进行声明式事务管理，底层使用AOP**



**（5）Spring事务管理API**

​	1）提供一个接口，代表事务管理器，这个接口针对不太的框架提供不同的实现类

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005090647739-172407999.png)

​	

### 4.事务操作（注解声明式事务管理）

**（1）在spring配置文件配置事务管理器**

```java
<!--    创建事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--        注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```

**（2）在spring配置文件，开启事务注解**

​	1）在spring配置文件引入名称空间tx

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                   http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
                   http://www.springframework.org/schema/tx https://www.springframework.org/schema/tx/spring-tx.xsd">

```

​	2)开启事务注解

```java
<!--    开启事务注解-->
   <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
```

（3）在service类上面（或者获取service类里面方法上面）添加事务注解

​	1）@Transactional，这个注解添加到类上面，也可以添加方法上面

​	2）如果把这个注解添加类上面，这个类里面所有的方法都添加事务

​	3）如果把这个注解添加方法上面，为着这个方法添加事务

演示在类上添加注解：

```java
@Service
@Transactional
public class UserService {
```

### 5.事务操作（声明式事务管理参数配置）

**（1）**

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005093928258-1904515563.png)

**（2）propagation：事务传播行为**

当一个事务方法被另外一个事务方法调用时候，这个事务方法如何进行

| PROPAGATION_REQUIRED          | 如果不存在外层事务，就主动创建事务；否则使用外层事务         |
| ----------------------------- | ------------------------------------------------------------ |
| **PROPAGATION_SUPPORTS**      | **如果不存在外层事务，就不开启事务；否则使用外层事务**       |
| **PROPAGATION_MANDATORY**     | **如果不存在外层事务，就抛出异常；否则使用外层事务**         |
| **PROPAGATION_REQUIRES_NEW**  | **总是主动开启事务；如果存在外层事务，就将外层事务挂起**     |
| **PROPAGATION_NOT_SUPPORTED** | **总是不开启事务；如果存在外层事务，就将外层事务挂起**       |
| **PROPAGATION_NEVER**         | **总是不开启事务；如果存在外层事务，则抛出异常**             |
| **PROPAGATION_NESTED**        | **如果不存在外层事务，就主动创建事务；否则创建嵌套的子事务** |

  **PROPAGATION_REQUIRED**  ：

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005094725069-1598050706.png)



  **PROPAGATION_REQUIRES_NEW** ：

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005095123825-818443379.png)



**PROPAGATION_SUPPORTS**：

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005095415072-1616233227.png)

```java
@Service
@Transactional(propagation = Propagation.REQUIRED )
public class UserService {
```



**（3）isolation：事务隔离级别**

​	1）事务有特性成为隔离性，多事务操作之间不会产生影响，不考虑隔离性产生很多问题

​	2）有三个读问题：脏读、不可重复读、虚（幻）读

​	3）脏读：一个未提交事务读取到另一个未提交事务的数据

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005101154373-1550149756.png)

​	4）不可重复读：一个未提交事务读取到另一个提交事务修改的数据

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005101539749-711073282.png)



​	5）虚读：一个未提交事务读取到另一个提交事务添加的数据

​	6）通过设置事务隔离级别，解决读问题

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005101857218-1028325227.png)

```java
@Service
//默认设置的是REPEATABLE_READ
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserService {

```

**（4）timeout：超时时间**

​	1）事务需要在一定时间内进行提交，如果不提交进行回滚

​	2）默认值是-1，设置时间以秒为单位进行计算

```java
@Service
@Transactional(timeout = -1,propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserService {

```

**（5）readOnly：是否只读**

​	1）读：查询操作，写：添加修改删除操作

​	2）readOnly默认值false，表示可以查询，可以添加修改删除操作

​	3）设置readOnly值是true，设置成true之后，只能查询

```java
@Service
@Transactional(readOnly = true,timeout = -1,propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserService {

```



**（6）rollbackFor：回滚**

​		设置出现哪些异常进行事务回滚

**（7）noRollbackFor：不回滚**

​	设置出现哪些异常不进行事务回滚

### 6.事务操作（XML声明式事务管理）

**（1）在spring配置文件中进行配置**

​	1）配置事务管理器

```java
    <!--    1.创建事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--        注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```



​	2）配置通知

```java
<!--  2.配置通知-->
    <tx:advice id="txadvice">
<!--        配置事务参数-->
        <tx:attributes>
<!--            指定哪种规则的方法上面添加事务-->
            <tx:method name="accountMoney" propagation="REQUIRED"/>
            <tx:method name="account*"/>
        </tx:attributes>
    </tx:advice>
```



​	3）配置切入点和切面

```java
<!--    3.配置切入点和切面-->
    <aop:config>
<!--        配置切入点-->
        <aop:pointcut id="pt" expression="execution(* com.service.UserService.*(..))"/>
<!--        配置切面-->
        <aop:advisor advice-ref="txadvice" pointcut-ref="pt"/>
    </aop:config>
    
```



### 7.事务操作（完全注解声明式事务管理）

（1）创建配置类，使用配置类替代xml配置文件

TxConfig.java

```java
package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration //配置类
@ComponentScan(basePackages = "com")//组件扫描
@EnableTransactionManagement //开启事务
public class TxConfig {

    //创建数据库连接池
    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/book");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    //创建JdbcTemplate对象
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        //到ioc容器中根据类型找到dataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        //注入dataSource
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
    //创建事务管理器
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}

```



