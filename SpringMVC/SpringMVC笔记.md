# 一、SpringMVC简介

## 1、什么是MVC

MVC是一种软件架构的思想，将软件按照模型、视图、控制器来划分

**M：**Model，模型层，指工程中的JavaBean，作用是处理数据

JavaBean分为两类：

- 一类称为实体类Bean：专门存储业务数据的，如Student、User等
- 一类称为业务处理Bean：指Service或Dao对象，专门用于处理业务逻辑和数据访问

**V：**View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据

**C：**Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器

**MVC的工作流程：**

用户通过视图层发送请求到浏览器，在服务器中请求被Controller接收，Controller调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller在根据请求处理的结果找到相应的View视图，渲染数据后最终响应给浏览器

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221005113837122-1641913063.png)



笔记中案例：https://github.com/DFshmily/Java/tree/main/SpringMVC

## 2、什么是SpringMVC

SpringMVC是Spring的一个后续产品，是Spring的一个子项目

SpringMVC是Spring为表述层开发提供的一整套完备的解决方案，在表述层框架经Strust、WebWork、Strust2等诸多产品的历代更迭之后，目前业界普遍选择了SpringMVC作为Java EE项目表述层开发的**首选方案**

*注：三层架构分为表述层（或表示层）、业务逻辑层、数据访问层、表述层表示前台页面和后台servlet*

## 3、SpringMVC的特点

- **Spring家族原生产品**，与IOC容器等基础设施无缝对接
- **基于原生的Servlet**，通过了功能强大的**前端控制器DispatcherServlet**，对请求和响应进行统一处理
- 表述层各细分领域需要解决的问题**全方位覆盖**，提供**全面解决方案**
- **代码清新简洁**，大幅度提升开发效率
- 内部组件化程度高，可插拔式组件**即插即用**，想要什么功能配置相应组件即可
- **性能卓著**，尤其适合现代大型、超大型互连网项目要求



# 二、搭建环境

## 1、开发环境

IDE：IDEA：2022.1.4

构建工具：maven 3.8.6

服务器：tomcat 9

Spring版本

## 2、创建maven工程

**（1）添加web模块**

**（2）打包方式：war**

**（3）引入依赖**

```java
<?xml version="1.0" encoding="UTF-8"?>
<!--Maven作用：1.用来做项目构建 2.项目jar包管理-->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>springMVC_demo1</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>


    <dependencies>
<!--        SpringMVC-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.23</version>
        </dependency>

<!--        日志-->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.3</version>
            <scope>test</scope>
        </dependency>


<!--        ServletAPI-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
<!--            依赖范围：provided，代表服务器已经提供-->
            <scope>provided</scope>
        </dependency>

<!--        thymeleaf和spring5整合包-->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
            <version>3.1.0.M3</version>
        </dependency>

    </dependencies>
</project>
```



## 3、配置web.xml

注册SpringMVC的前端控制器DispatcherServlet

##### （1）默认配置方式

此配置作用下，SpringMVC的配置文件默认位于WEB-INF下，默认名称为\<servlet-name>-servlet.xml,例如，以下配置所对应SpringMVC的配置文件位于WEB-INF下，文件名springMVC-servlet.xml

web.xml:

```java
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

<!--    配置SpringMVC的前端控制器，对浏览器发生的请求进行统一处理-->
    <servlet>
        <servlet-name>springMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>springMVC</servlet-name>
        <!--
            设置springMVC的核心控制器所能处理的请求的请求路径
            '/'所匹配的请求可以是/login或.html或.js或.css方式的请求路径
            但是'/'不能匹配.jsp请求路径的请求
         -->
        <url-pattern></url-pattern>
    </servlet-mapping>
</web-app>
```

#####  （2）扩展配置方式

可通过init-param标签设置SpringMVC配置文件的位置和名称，通过load-on-startup标签设置SpringMVC前端控制器DispatcherServlet的初始化时间

```java
<!--    配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理-->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--        配置SpringMVC配置文件的位置和名称-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
<!--        将前端控制器DispatcherServlet的初始化时间提前到服务器启动时-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```



## 4、创建请求控制器

由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

请求控制器中每一个处理请求的方法成为控制器方法

因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Srping的IOC容器管理，此时SpringMVC才能够识别控制器的存在

```java
@Controller //控制层组件
public class HelloController {

}
```

## 5、创建springMVC的配置文件

springmvc.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

<!--    扫描组件-->
    <context:component-scan base-package="com.mvc"></context:component-scan>
<!--    配置Thymeleaf视图解析器-->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

<!--                        视图前缀-->
                        <property name="prefix" value="/WEB-INF/templates/"/>

<!--                        视图后缀-->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
<!--    处理静态资源，例如html、js、css、jpg
        若只设置该标签，则只能访问静态资源，其他请求则无法访问
        此时必须设置<mvc:annotation-driven/>解决问题
-->

<!--一般不需要后面的-->
<mvc:default-servlet-handler/>
<!--    开启mvc注解驱动-->
    <mvc:annotation-driven>
        <mvc:message-converters>
<!--            处理响应中文内容乱码-->
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="defaultCharset" value="UTF-8"/>
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/html</value>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```

## 6、测试

##### （1）实现对首页的访问

在请求控制器中创建处理请求的方法

HelloController.java

```java
package com.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DFshmily
 */
@Controller //控制层组件
public class HelloController {

   // "/"--->/WEB-INF/templates/index.html

    //@RequestMapping注解：处理请求和控制器方法之间的映射关系
    //@RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
    //localhost：8080/springMVC/
    @RequestMapping("/") 
    public String index(){
        //设置视图名称
        return "index";
    }
}

```

index.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
</body>
</html>
```



##### （2）通过超连接跳转到指定页面

在主页index.html中设置超链接

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/target}">访问目标页面target.html</a>
</body>
</html>
```



HelloController.java

```java
package com.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DFshmily
 */
@Controller //控制层组件
public class HelloController {

   // "/"--->/WEB-INF/templates/index.html

    //@RequestMapping注解：处理请求和控制器方法之间的映射关系
    //@RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
    //localhost：8080/springMVC/
    @RequestMapping("/")
    public String index(){
        //设置视图名称
        return "index";
    }


    @RequestMapping("/target")
    public String toTarget(){
        return "target";
    }

}

```

target.html

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
Hello DFshmily!
</body>
</html>
```



## 7、总结

浏览器发送请求，若请求地址符号前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理，

前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法，

处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymelea对视图进行渲染，最终转发到视图对应页面



# 三、@RequestMapping注解

## 1、@RequestMapping注解的功能

从注解名称上我们可以看到，@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联起来，建立映射关系

SpringMVC接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求



## 2、@RequestMapping注解的位置

:black_circle:@RequestMapping标识一个类：设置映射请求的请求路径的初始信息

:black_circle:@RequestMapping标识一个方法：设置映射请求请求路径的具体信息

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class RequestMappingController {
    //此时请求映射所映射的请求路径为：/hello/testRequestMapping
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping(){
        return "success";
    }
}

```

index.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/hello/testRequestMapping}">测试RequestMapping注解的位置</a>

</body>
</html>
```

success.html

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>RequestMapping注解位置</h2>
</body>
</html>
```



## 3、@RequestMapping注解的value属性

:black_circle:@RequestMapping注解的value属性通过请求的请求地址匹配请求映射

:black_circle:@RequestMapping注解的value属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址对应的请求

:black_circle:@RequestMapping注解的value属必须设置，至少通过请求地址匹配请求映射

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestMappingController {

    //RequestMapping注解的value属性
    //可以访问value中的任意一个路径
    @RequestMapping(
            value = {"/testRequestMapping","/hello"}
    )
        public String testRequestMapping(){
        return "success";
    }
}


```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/hello}">测试RequestMapping注解的位置-->/hello</a><br>
<a th:href="@{/testRequestMapping}">测试RequestMapping注解的位置-->/testRequestMapping</a><br>
<!--不能访问-->
<a th:href="@{/hello/testRequestMapping}">测试RequestMapping注解的位置-->/hello/testRequestMapping</a>


</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>RequestMapping注解位置</h2>
</body>
</html>
```





## 4、@RequestMapping注解的method属性

:black_circle:@RequestMapping注解的method属性通过请求的请求方式（get或post）匹配请求映射

:black_circle:@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式的请求

若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，

则浏览器报错405 Request method 'POST' not supported

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {

    //RequestMapping注解的value属性
    //可以访问value中的任意一个路径
    @RequestMapping(
            value = {"/testRequestMapping","/hello"},
            method = {RequestMethod.GET,RequestMethod.POST}//RequestMethod.GET不支持POST的请求,要加RequestMethod.POST
    )
   public String testRequestMapping(){
        return "success";
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/hello}">测试RequestMapping注解的位置-->/hello</a><br>
<a th:href="@{/testRequestMapping}">测试RequestMapping注解的位置-->/testRequestMapping</a><br>
<!--不能访问-->
<a th:href="@{/hello/testRequestMapping}">测试RequestMapping注解的位置-->/hello/testRequestMapping</a>

<form th:action="@{/hello}" method="post">
     <input type="submit" value="测试RequestMapping注解的method属性--->POST">
</form>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>RequestMapping注解位置</h2>
</body>
</html>
```



注：

（1）对于处理指定请求的控制器方法，SpringMVC中提供了@RequestMapping的派生注解

| 处理get请求的映射        | @GetMapping        |
| ------------------------ | ------------------ |
| **处理post请求的映射**   | **@PostMapping**   |
| **处理put请求的映射**    | **@PutMapping**    |
| **处理delete请求的映射** | **@DeleteMapping** |

（2）常用的请求方式有get、post、put、delete

但是目前浏览器只支持get和post，若在from表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理

若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter

**@GetMapping**和**@PostMapping**的例子：

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
  public String testRequestMapping(){
        return "success";
    }


    @GetMapping("/testGetMapping")
    public String testGetMapping(){
        return "success";
    }

    @PostMapping("/testPostMapping")
    public String testPostMapping(){
        return "success";
    }
}
```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
    <a th:href="@{/testGetMapping}">测试GetMapping注解</a><br>

<form th:action="@{/testPostMapping}" method="post">
    <input type="submit" value="测试PostMapping注解">
</form>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>RequestMapping注解位置</h2>
</body>
</html>
```



**@PutMapping**的例子：

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
    @RequestMapping(value = "/testPut",method = RequestMethod.PUT)
    public String testPut(){
        return "success";
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<!--form表单中的method方法默认按get方法，不能用put-->
<form th:action="@{/testPut}" method="put">
    <input type="submit" value="测试from表单是否能够发送put或delete请求方式">
</form>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>RequestMapping注解位置</h2>
</body>
</html>
```



测试结果：

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221006154002135-1774601250.png)

## 5、@RequestMapping注解的params属性（了解）

:black_circle:@RequestMapping注解的params属性通过请求的请求参数匹配请求映射

:black_circle:@RequestMapping注解的params属性是一个字符串类型的数组，可以通过四中表达式设置请求参数和请求映射的匹配关系

:star:"params"：要求请求映射所匹配的请求必须携带params请求参数

:star:"!params"：要求请求映射所匹配的请求必须不能携带params请求参数

:star:”params=value“：要求请求映射所匹配的请求必须携带params请求参数且params=value

:star:”params!=value“：要求请求映射所匹配的请求必须携带params请求参数但是params!=value

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
        @RequestMapping(
            value = {"/testParamsAndHeaders"},
            params = {"username"}

    )
public String testParamsAndHeaders(){
        return "success";
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
    <a th:href="@{testParamsAndHeaders(username='admin',password=123)}">RequestMapping注解的Params属性--->testParamsAndHeaders</a>
</body>
</html>
```





## 6、@RequestMapping注解的headers属性（了解）

:black_circle:@RequestMapping注解的headers属性通过的请求头信息匹配请求映射

:black_circle:@RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系

:star:"header"：要求请求映射所匹配的请求必须携带header请求参数

:star:"!header"：要求请求映射所匹配的请求必须不能携带header请求参数

:star:”header=value“：要求请求映射所匹配的请求必须携带header请求参数且header=value

:star:”header!=value“：要求请求映射所匹配的请求必须携带header请求参数但是header!=value

若当前请求满足@RequestMapping注解的headers和method属性，但是不满足headers属性，此时页面显示404错误，即资源未找到





## 7、SpringMVC支持ant风格的路径

？：表示任意的单个字符

*：表示任意的0个或多个字符

**：表示任意的一层或多层目前

```java
注意：在使用**时，只能使用/**/xxx的方式
```

以？为例：

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
    
    @RequestMapping("/a?a/testAnt")
    public String testAnt(){
        return "success";
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
    <!--都可以成功a?a,中的？可以为任意单个字符-->
<a th:href="@{/a1a/testAnt}">测试@RequestMapping可以匹配的Ant路径-->使用？</a><br>
<a th:href="@{/a2a/testAnt}">测试@RequestMapping可以匹配的Ant路径-->使用？</a><br>
<a th:href="@{/a3a/testAnt}">测试@RequestMapping可以匹配的Ant路径-->使用？</a>

</body>
</html>
```



## 8、SpringMVC支持路径中的占位符（重点）

原始方式：/deleteUser?id=1

rest方式：/deleteUser/1

SpringMVC路径中的占位符常用与rest风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参

RequestMappingController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
    @RequestMapping("/testPath/{id}/{username}")
    public String testPath(@PathVariable("id")Integer id,@PathVariable("username") String username){
        System.out.println("id:"+id+",username"+username);
        return "success";
    }

}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
    <a th:href="@{/testPath/1/admin}">测试路径中的占位符————>testPath</a>
</body>
</html>


```





# 四、SpringMVC获取请求参数

## 1、通过servletAPI获取

将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象

ParamController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ParamController {

    @RequestMapping("/testServletAPI")
    //形参位置的request
    public String testServletAPI(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:"+username+"pawssword:"+password);
        return "success";
    }
}

```

TestController.java

```java
package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/parma")
     public String param(){
        return "test_parma";
    }
}

```

test_parma.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>测试请求参数</title>
</head>
<body>
<h1>测试请求参数</h1>
<a th:href="@{/testServletAPI(username='admin',password=123456)}">测试使用ServletAPI获取请求参数</a>
</body>
</html>
```



## 2、通过控制器方法的形参获取请求参数

在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给相应的形参

ParamController.java

```java
   @PostMapping("/testParam")
    //@RequestParam中的required为false，不是必须的,不传参数的话就是defaultValue中设置的默认值
    public String testParma(@RequestParam(value = "user_name",required = false,defaultValue = "默认值") String username, String password, String[] hobby){
        //若请求参数中出现多个同名的请求参数，可以再控制器方法的形参位置设置字符串类型或字符串数组接收此数据
        //若使用字符串类型的形参，最终结果为请求参数的每一个值之间使用逗号进行拼接
        System.out.println("username:"+username+",password:"+password+",hobby:"+ Arrays.toString(hobby));
        return "success";
    }
```

test_parma.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>测试请求参数</title>
</head>
<body>
<h1>测试请求参数</h1>
<a th:href="@{/testServletAPI(username='admin',password=123456)}">测试使用ServletAPI获取请求参数</a><br>
<a th:href="@{/testParam(username='admin',password=123456)}">测试通过控制器方法的形参获取请求参数</a>

<form th:action="@{/testParam}" method="post">
    姓名：<input type="text" name="user_name"><br>
    密码：<input type="password" name="password"><br>
    爱好：<input type="checkbox" name="hobby" value="a">a
    <input type="checkbox" name="hobby" value="b">b
    <input type="checkbox" name="hobby" value="c">c<br>
    <input type="submit" value="测试通过控制器方法的形参获取请求参数">
</form>

</body>
</html>
```

*注：*

*若请求所传输的请求参数中有多个同名的请求参数，此时可以在控制器方法的形参中设置字符串数组或者字符串类型的形参接收此请求参数*

*若使用字符串数组类型的形参，此参数的数组中包含了每一个数据*

*若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果*



## 3、@RequestParam

@RequestParam是将请求参数和控制器方法的形参创建映射关系

@RequestParam注解一共有三个属性：

value：指定为形参赋值的请求参数的参数名

required：设置是否必须传输此请求参数，默认值为true

若设置为true时，则当前请求必须传输value所指定的请求参数，若没有传输该请求参数，且没有设置defaultValue属性，则页面报错400：Required String parameter ‘xxx’ is not present；

若设置为false，则当前请求不是必须传输value所指定的请求参数，若没有传输，则注解所标识的形参的值为null



## 4、@RequestHeader

@RequestHeader是将请求头信息和控制器方法的形参创建映射关系

@RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@Requestparam

```java
 @PostMapping("/testParam")
  public String testParma(
       @RequestHeader("Host") String host){
       System.out.println("host:"+host);
  }

运行结果：
    host:localhost：8080
```



```java
 @PostMapping("/testParam")
  public String testParma(
       @RequestHeader(value = "DF",required = true,defaultValue = "shmily") String host){
       System.out.println("host:"+host);
  }

运行结果：
    host:shmily
```



## 5、@CookieValue

@CookieValue是将Cookie数据和控制器方法的形参创建映射关系

@CookieValue注解一共三个属性：value、required、defaultValue，用法同@RequestParam

```java
 @PostMapping("/testParam")
  public String testParma(
       //@CookieValue( "Idea-1cb5354") String JSESSIONID
       @CookieValue(value = "Cookie",required = true,defaultValue = "cookie") String cookie)
            {
  System.out.println("Cookie:"+cookie);
  }

运行结果：
//JSESSIONID:2c3874e6-94af-4d67-a05b-7d86ffd68398
 Cookie:cookie
```



## 6、通过POJO获取请求参数

可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

test_parma.html

```html
<form th:action="@{/testPOJO}" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    性别：<input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女<br>
    年龄：<input type="text" name="age"><br>
    邮箱：<input type="text" name="email"><br>
    <input type="submit" value="使用实体类来接收请求参数">
</form>
```

User.java

```java
package com.springmvc.bean;

public class User {

    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String email;

    //反射默认无参构造，在生成有参构造的同时要加上无参构造
    public User() {
    }

    public User(Integer id, String username, String password, String sex, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

```

ParamController.java

```java
  @RequestMapping("/testPOJO")
    public String testBean(User user){
        System.out.println(user);
        return "success";
    }

运行结果：
    User{id=null, username='11', password='11', sex='??��', email='test@qq.com'}
```



## 7、解决获取请求参数乱码的问题

解决获取请求参数的乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是必须在web.xml中进行注册

①在tomcat的config文件中的service.xml中加入URIEncoding="UTF-8"

```java
 <Connector port="8080" URIEncoding="UTF-8" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```

②

```java
<!--    配置springMVC的编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
       <url-pattern>/*</url-pattern>
    </filter-mapping>
```

*注：SpringMVC中处理编码的过滤器一定要求配置到其他过滤器之前，否则无效*

③tomcat：在VM options中加入-Dfile.encoding=UTF-8

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221011205612092-298222461.png)

# 五、域对象共享数据

## 1、使用servletAPI向request域对象共享数据

ScopeController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ScopeController {


    //使用servletAPI向request域对象共享数据
    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope","hello,servletAPI");
        return "success";
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/testRequestByServletAPI}">通过servletAPI向request域对象共享数据</a>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>success</title>
</head>
<body>
<h1>success</h1>
<p th:text="${testRequestScope}"></p>
</body>
</html>
```



## 2、使用ModelAndView向request域对象共享数据

```java
 @RequestMapping("/testModelAndView")
 public ModelAndView testModelAndView(){
       ModelAndView modelAndView = new ModelAndView();
       //处理模型数据，即向请求域request共享数据
       modelAndView.addObject("testRequestScope","hello,ModelAndView");
       //设置视图名称
       modelAndView.setViewName("success");
       return modelAndView;
}
```



## 3、使用Model向request域对象共享数据

```java
    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","hello,Model");
        return "success";
    }
```



## 4、使用map向request域对象共享数据

```java
  @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        map.put("testRequestScope","hello,Map");
        return "success";
    }
```



## 5、使用ModelMap向request域对象共享数据

```java
  @RequestMapping("/testModelMap")
    public String testMpdelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","hello.ModelMap");
        return "success";
    }

```



## 6、Model、ModelMap、Map的关系

```java
  @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","hello,Model");
        System.out.println(model.getClass().getName());
        return "success";
    }


    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        map.put("testRequestScope","hello,Map");
        System.out.println(map.getClass().getName());
        return "success";
    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","hello.ModelMap");
        System.out.println(modelMap.getClass().getName());
        return "success";
    }

运行结果：
org.springframework.validation.support.BindingAwareModelMap
org.springframework.validation.support.BindingAwareModelMap
org.springframework.validation.support.BindingAwareModelMap
```

Model、ModelMap、Map类型的参数其实本质上都是BindingAwareModelMap类型的

```
public interface Model()
public class ModelMap extends LinkedHashMap<String,Object>{}
public class EctendModelMap extends ModelMap inplements Model{}
public class BindingAwareModelMap extends ExtendedModelMap{}
```





## 7、向session域对象共享数据

```java
 @RequestMapping("/testSession")
    public String testSession(HttpSession session){
       session.setAttribute("testSessionScope","hello,session");
        return "success";
    }
```



## 8、向application域对象共享数据

```java
  @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope","hello,application");
        return "success";
    }
```



所用文件：

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/testRequestByServletAPI}">通过servletAPI向request域对象共享数据</a><br>
<a th:href="@{/testModelAndView}">通过ModelAndView向request域对象共享数据</a><br>
<a th:href="@{/testModel}">通过Model向request域对象共享数据</a><br>
<a th:href="@{/testMap}">通过Map集合向request域对象共享数据</a><br>
<a th:href="@{/testModelMap}">通过ModelMap集合向request域对象共享数据</a><br>
<a th:href="@{/testSession}">通过servletAPI向session域对象共享数据</a><br>
<a th:href="@{/testApplication}">通过servletAPI向application域对象共享数据</a><br>
</body>
</html>
```

ScopeController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ScopeController {


    //使用servletAPI向request域对象共享数据
    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope","hello,servletAPI");
        return "success";
    }


    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView modelAndView = new ModelAndView();
        //处理模型数据，即向请求域request共享数据
        modelAndView.addObject("testRequestScope","hello,ModelAndView");
        //设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","hello,Model");
        System.out.println(model.getClass().getName());
        return "success";
    }


    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        map.put("testRequestScope","hello,Map");
        System.out.println(map.getClass().getName());
        return "success";
    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","hello.ModelMap");
        System.out.println(modelMap.getClass().getName());
        return "success";
    }

    @RequestMapping("/testSession")
    public String testSession(HttpSession session){
       session.setAttribute("testSessionScope","hello,session");
        return "success";
    }

    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope","hello,application");
        return "success";
    }
}

```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>success</title>
</head>
<body>
<h1>success</h1>

<p th:text="${testRequestScope}"></p>
<p th:text="${testSessionScope}"></p>
<p th:text="${testApplicationScope}"></p>
</body>
</html>
```



# 六、SpringMVC的视图

SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

SpringMVC视图的种类很多，默认有转发视图InternalResourceView和重定向视图RedirectView

当工程引入jstl的依赖，转发视图会自动转换为jstlView

若使用的视图技术为ThymeleafView，在SpringMVC的配置文件中配置了ThymeleafView的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

## 1、ThymeleafView

当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

```java
  @RequestMapping("/testThymeleafView")
    public String testThymeleafView(){
        return "success";
    }
}
```



![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016213408280-2053667765.png)

## 2、转发视图

SpringMVC中默认的转发视图是InternalResourceView

SpringMVC中创建转发视图的情况：

当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转

例如"forward:/"，"forward:/testThymeleafView"

```java
   @RequestMapping("/testForward")
    public String testForward(){
        return "forward:/testThymeleafView";
    }
```

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016213413418-1406329506.png)

## 3、重定向视图

SpringMVC中默认的重定向视图是RedirectView

当控制器方法中设置的视图名称以"edirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转

例如"redirect:/"，"redirect:/testThymeleafView"

```java
  @RequestMapping("/testRedirect")
    public String testRedirect(){
        return "redirect:/testThymeleafView";
    }
```

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016213417499-1085666222.png)

*注：*

*重定向视图在解析时，会先将redirect:前缀去掉，然后会判断剩余部分是否以/开头，若是则会自动拼接上下文路径*

## 4、视图控制器view-controller

当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

```java
    <!--    配置静态资源的处理,不开启MVC的注解驱动会请求都失效
    path：设置处理的请求地址
    view-name:设置请求地址所对应的视图名称
    -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

<!--    开启MVC的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
```

*注：*

*当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：*

```java
<!--    开启MVC的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
```





所有文件：

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/testRequestByServletAPI}">通过servletAPI向request域对象共享数据</a><br>
<a th:href="@{/testModelAndView}">通过ModelAndView向request域对象共享数据</a><br>
<a th:href="@{/testModel}">通过Model向request域对象共享数据</a><br>
<a th:href="@{/testMap}">通过Map集合向request域对象共享数据</a><br>
<a th:href="@{/testModelMap}">通过ModelMap集合向request域对象共享数据</a><br>
<a th:href="@{/testSession}">通过servletAPI向session域对象共享数据</a><br>
<a th:href="@{/testApplication}">通过servletAPI向application域对象共享数据</a><br>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>success</title>
</head>
<body>
<h1>success</h1>

<p th:text="${testRequestScope}"></p>
<p th:text="${testSessionScope}"></p>
<p th:text="${testApplicationScope}"></p>
</body>
</html>
```

test_view.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a th:href="@{/testThymeleafView}">测试ThymeleafView</a><br>
<a th:href="@{/testForward}">测试InternalResourceView</a><br>
<a th:href="@{/testRedirect}">测试RedirectView</a><br>
</body>
</html>
```

TestController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//实现页面跳转
@Controller
public class TestController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/test_view")
    public String testView(){
        return "test_view";
    }
}

```

ScopeController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ScopeController {


    //使用servletAPI向request域对象共享数据
    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope","hello,servletAPI");
        return "success";
    }


    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView modelAndView = new ModelAndView();
        //处理模型数据，即向请求域request共享数据
        modelAndView.addObject("testRequestScope","hello,ModelAndView");
        //设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","hello,Model");
        System.out.println(model.getClass().getName());
        return "success";
    }


    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        map.put("testRequestScope","hello,Map");
        System.out.println(map.getClass().getName());
        return "success";
    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","hello.ModelMap");
        System.out.println(modelMap.getClass().getName());
        return "success";
    }

    @RequestMapping("/testSession")
    public String testSession(HttpSession session){
       session.setAttribute("testSessionScope","hello,session");
        return "success";
    }

    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope","hello,application");
        return "success";
    }
}

```

ViewController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/testThymeleafView")
    public String testThymeleafView(){
        return "success";
    }

    @RequestMapping("/testForward")
    public String testForward(){
        return "forward:/testThymeleafView";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect(){
        return "redirect:/testThymeleafView";
    }
}

```

springMVC.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--    扫描组件-->
    <context:component-scan base-package="com"></context:component-scan>

    <!--    配置Thymeleaf视图解析器-->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!--                        视图前缀-->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!--                        视图后缀-->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <!--    配置静态资源的处理,不开启MVC的注解驱动会请求都失效
    path：设置处理的请求地址
    view-name:设置请求地址所对应的视图名称
    -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

<!--    开启MVC的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
</beans>
```

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--    配置springMVC的编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--    配置Servlet前端控制器-->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!--        配置SpringMVC配置文件的位置和名称-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springMVC.xml</param-value>
        </init-param>

        <!--        将前端控制器DispatcherServlet的初始化时间提前到服务器启动-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```



# 七、RESTful

## 1、RESTful简介

REST：Representational State Transfer，表现层资源状态转移

##### （1）资源

资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成，每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词，一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址，对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。

##### （2）资源的表述

资源的表述是一段对于资源在某个特定时刻的状态的描述，可以在客户端—服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定，请求—响应方向的表述通常使用不同的格式。

##### （3）状态转移

状态转移说的是：在客户端和服务器端之间转移（transfer）代表资源状态的表述，通过转移和操作资源的表述，来间接实现操作资源的目的。

## 2、RESTful的实现

具体说，就是HTTP协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE，

它们分别对应四种基本操作：GET用来获取资源，POST用来新建资源，PUT用来更新资源，DELETE用来删除资源

REST风格提倡URL地址使用统一的风格设计，从前到各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为URL地址的一部分，以保证整体风格的一致性。

| 操作     | 传统方式        | REST风格                |
| -------- | --------------- | ----------------------- |
| 查询操作 | getUserById?=1  | user/1-->get请求方式    |
| 保存操作 | saveUser        | user-->post请求方式     |
| 更新操作 | updateUser      | user/1-->put请求方式    |
| 删除操作 | deleteUser?Id=1 | user/1-->delete请求方式 |





## 3、HiddenHttpMethodFilter

由于浏览器只支持发送get和post方式的请求，那么该如何发送put和delete请求呢？

SpringMVC提供了**HiddenHttpMethodFilter**帮助我们**将POST请求转换为DELETE或PUT请求**

**HiddenHttpMethodFilter**处理put和delete请求的条件：

​	①当前请求的请求方式必须为post

​	②当前请求必须传输请求参数_method





所用文件：

test_rest.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a th:href="@{/user}">查询所有用户信息</a><br>
<a th:href="@{/user/1}">根据id查询用户信息</a><br>
<form th:action="@{/user}" th:method="post">
    用户名：<input type="text" name="username" ><br>
    密码：<input type="text" name="password" ><br>
    <input type="submit" value="添加用户">
</form>

<form th:action="@{/user}" th:method="post">
    <input type="hidden" name="_method" value="PUT">
    用户名：<input type="text" name="username" ><br>
    密码：<input type="password" name="password" ><br>
    <input type="submit" value="修改用户">
</form>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>success</title>
</head>
<body>
<h1>success</h1>

<p th:text="${testRequestScope}"></p>
<p th:text="${testSessionScope}"></p>
<p th:text="${testApplicationScope}"></p>
</body>
</html>
```

TestController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//实现页面跳转
@Controller
public class TestController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/test_view")
    public String testView(){
        return "test_view";
    }

    @RequestMapping("/test_rest")
    public String testRest(){
        return "test_rest";
    }
}

```

UserController.java

```java
package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    /**
     * 使用RESTFul模拟用户资源的增删改查
     * /users   GET 查询所有用户信息
     * /user/1 GET 根据用户id查询用户信息
     * /user   POST 添加用户信息
     * /user PUT 修改用户信息
     * /user/1 DELETE 删除用户信息
     */


    @RequestMapping(value="/user",method=RequestMethod.GET)
    public String getAllUser(){
        System.out.println("查询所有用户信息");
        return "success";
    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据用户id查询用户信息");
        return "success";
    }

    @RequestMapping(value="/user",method=RequestMethod.POST)
    public String insertUser(String username,String password){
        System.out.println("添加用户信息:"+username+" "+password);
        return "success";
    }

    @RequestMapping(value="/user",method=RequestMethod.PUT)
    public String updateUser(String username,String password){
        System.out.println("修改用户信息:"+username+" "+password);
        return "success";
    }
}

```

springMVC.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--    扫描组件-->
    <context:component-scan base-package="com"></context:component-scan>

    <!--    配置Thymeleaf视图解析器-->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!--                        视图前缀-->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!--                        视图后缀-->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <!--    配置静态资源的处理,不开启MVC的注解驱动会请求都失效
    path：设置处理的请求地址
    view-name:设置请求地址所对应的视图名称
    -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
    <mvc:view-controller path="/" view-name="test_view"></mvc:view-controller>
    <mvc:view-controller path="/" view-name="test_rest"></mvc:view-controller>

<!--    开启MVC的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
</beans>
```

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <!--    配置springMVC的编码过滤器,编码的必须放最前面-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--    配置HiddenHttpMethodFilter过滤器-->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--    配置Servlet前端控制器-->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!--        配置SpringMVC配置文件的位置和名称-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springMVC.xml</param-value>
        </init-param>

        <!--        将前端控制器DispatcherServlet的初始化时间提前到服务器启动-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```





# 八、RESTful案例

## 1、准备工作

和传统CRUD一样，实现对员工信息的增删改查

- 搭建环境
- 准备实现类

Employee.java

```java
package com.springmvc.rest.bean;

public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer sex;

    
     public Employee() {
   }

    public Employee(Integer id, String lastName, String email, Integer gender) {
      super();
      this.id = id;
      this.lastName = lastName;
      this.email = email;
      this.gender = gender;
   }

  
    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Integer getGender() {
      return gender;
   }

   public void setGender(Integer gender) {
      this.gender = gender;
   }
}

```

- Dao层


EmployeeDao.java

```java
package com.springmvc.rest.Dao;

import com.springmvc.rest.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {

    //模拟数据库中的数据
    public static Map<Integer, Employee> employees= null;

    //员工有所属的部门
    static {
        employees = new HashMap<Integer, Employee>();

        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com",1));
        employees.put(1002, new Employee(1002, "E-BB", "bb@163.com",0));
        employees.put(1003, new Employee(1003, "E-CC", "CC@163.com",1));
        employees.put(1004, new Employee(1004, "E-DD", "DD@163.com",1));
        employees.put(1005, new Employee(1005, "E-EE", "EE@163.com",1));
    }

    //主键自增
    private static Integer initId = 1006;

    //增加与修改员工
    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
    }
    //查询全部员工信息
    public Collection<Employee> getAll(){
        return employees.values();
    }
    //通过id查询员工
    public Employee get(Integer id){
        return employees.get(id);
    }
    //通过id删除员工
    public void delete(Integer id){
        employees.remove(id);
    }
}

```





## 2、功能清单

| 功能               | URL地址     | 请求方式 |
| ------------------ | ----------- | -------- |
| 访问首页           | /           | GET      |
| 查询全部数据       | /employee   | GET      |
| 删除               | /employee/2 | DELETE   |
| 跳转到添加数据页面 | /toAdd      | DET      |
| 执行保存           | /employee   | POST     |
| 跳转到更新数据页面 | /employee/2 | GET      |
| 执行更新           | /employww   | PUT      |





## 3、具体功能：访问首页

##### （1）配置view-controller

springMVC.xml

```xml
<!--    配置视图控制器-->
    <mvc:view-controller path="/"   view-name="index"></mvc:view-controller>
```



##### （2）创建页面

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{employee}">查看员工信息</a>
</body>
</html>
```



## 4、具体功能：查询所有员工数据

##### （1）控制器方法

```java

    //查询所有员工返回列表页面
    @RequestMapping(value = "/employee",method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList",employeeList);
        return "employee_list";
    }
```



##### （2）创建employee_list.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Employee Info</title>
</head>
<body>
<table id="datatable" border="1" cellpadding="0" style="text-align: center">
    <tr>
        <th colspan="5">Employee Info</th>
    </tr>
    <tr>
        <th>id</th>
        <th>lastName</th>
        <th>email</th>
        <th>gender</th>
        <th>options(<a th:href="@{/toAdd}">add</a>)</th>
    </tr>
    <tr th:each="employee: ${employeeList}">
        <td th:text="${employee.id}"></td>
        <td th:text="${employee.lastName}"></td>
        <td th:text="${employee.email}"></td>
        <td th:text="${employee.gender}"></td>
        <td>
            <!-- 两种方式都可以<a th:href="@{/employee/}+${employee.id}">delete</a>-->
            <a @click="deleteEmployee" th:href="@{'/employee/'+${employee.id}}">delete</a>
            <a th:href="@{'/employee/'+${employee.id}}">update</a>
        </td>
    </tr>
</table>
```



## 5、具体功能：删除

##### （1）创建处理delete请求方式的表单

```html
<!-- 作用：通过超链接控制表单的提交，将post请求转换为delete请求 -->
<form id="deleteForm" method="post">
    <!-- HiddenHttpMethodFilter要求：必须传输_method请求参数，并且值为最终的请求方式 -->
    <input type="hidden" name="_method" value="delete"/>
</form>
```



##### （2）删除超链接绑定点击事件

引入vue.js

```html
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
```

删除超链接

```html
 <!-- 两种方式都可以<a th:href="@{/employee/}+${employee.id}">delete</a>-->
            <a @click="deleteEmployee" th:href="@{'/employee/'+${employee.id}}">delete</a>
```

通过vue处理点击事件

```html
<script type="text/javascript">
    var vue = new Vue({
        el:"#datatable",
        methods:{
            deleteEmployee:function(event){
                //根据id获取表单元素
                var deleteForm = document.getElementById("deleteForm");
                //将触发点击事件的超链接的href属性赋值给表单的action属性
                deleteForm.action =event.target.href;
                //提交表单
                deleteForm.submit();
                //取消超链接的默认行为
                event.preventDefault();
    }
        }
    })
</script>
```



##### （3）控制器方法

```java
    //删除员工
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/employee";
    }
```



## 6、具体功能：跳转到添加数据页面

##### （1）配置view-controller

```xml
<mvc:view-controller path="/toAdd" view-name="employee_add"></mvc:view-controller>
```

##### （2）创建employee_add.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Add employee</title>
</head>
<body>
<form th:action="@{/employee}" method=""post>
    lastName:<input type="text" name="lastName"><br/>
    email:<input type="text" name="email"><br/>
    gender:<input type="radio" name="gender" value="1">male
    <input type="radio" name="gender" value="0">female<br/>
    <input type="submit" value="添加"><br/>
</form>
</body>
</html>
```

## 7、具体功能：执行保存

##### （1）控制器方法

```java
 //添加员工
    @RequestMapping(value="/employee",method = RequestMethod.POST)
    public String addEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }

```





## 8、具体功能：跳转到跟新数据页面

##### （1）修改超链接

```html
 <a th:href="@{'/employee/'+${employee.id}}">update</a>
```

##### （2）控制器方法

```java
   //修改员工
    @RequestMapping(value="/employee/{id}",method = RequestMethod.GET)
    public String getEmployeeById(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "employee_update";
    }

```



##### （3）创建employee_update.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>update employee</title>
</head>
<body>
<form th:action="@{/employee}" method="post">
    <input type="hidden" name="_method" value="put">
    <input type="hidden" name="id" th:value="${employee.id}">
  lastName:<input type="text" name="lastName" th:value="${employee.lastName}"/><br/>
  email:<input type="text" name="email" th:value="${employee.email}"><br/>
    <!--
       th:field="${employee.gender}"可用于单选框或复选框的回显
       若单选框的value和employee.gender的值一致，则添加checked="checked"属性
   -->
  gender:<input type="radio" name="gender" value="1" th:field="${employee.gender}">male
  <input type="radio" name="gender" value="0" th:field="${employee.gender}">female<br/>
    <input type="submit" value="update">
    
</form>
</body>
</html>
```



## 9、具体功能：执行更新

##### 控制器方法

```java
@RequestMapping(value="/employee",method = RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }
```

所有文件：

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{employee}">查看员工信息</a>
</body>
</html>
```

employee_list.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Employee Info</title>
</head>
<body>
<table id="datatable" border="1" cellpadding="0" style="text-align: center">
    <tr>
        <th colspan="5">Employee Info</th>
    </tr>
    <tr>
        <th>id</th>
        <th>lastName</th>
        <th>email</th>
        <th>gender</th>
        <th>options(<a th:href="@{/toAdd}">add</a>)</th>
    </tr>
    <tr th:each="employee: ${employeeList}">
        <td th:text="${employee.id}"></td>
        <td th:text="${employee.lastName}"></td>
        <td th:text="${employee.email}"></td>
        <td th:text="${employee.gender}"></td>
        <td>
            <!-- 两种方式都可以<a th:href="@{/employee/}+${employee.id}">delete</a>-->
            <a @click="deleteEmployee" th:href="@{'/employee/'+${employee.id}}">delete</a>
            <a th:href="@{'/employee/'+${employee.id}}">update</a>
        </td>
    </tr>
</table>

<!-- 作用：通过超链接控制表单的提交，将post请求转换为delete请求 -->
<form id="deleteForm" method="post">
    <!-- HiddenHttpMethodFilter要求：必须传输_method请求参数，并且值为最终的请求方式 -->
    <input type="hidden" name="_method" value="delete"/>
</form>


<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el:"#datatable",
        methods:{
            deleteEmployee:function(event){
                //根据id获取表单元素
                var deleteForm = document.getElementById("deleteForm");
                //将触发点击事件的超链接的href属性赋值给表单的action属性
                deleteForm.action =event.target.href;
                //提交表单
                deleteForm.submit();
                //取消超链接的默认行为
                event.preventDefault();
    }
        }
    })
</script>
</body>
</html>
```

employee_add.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Add employee</title>
</head>
<body>
<form th:action="@{/employee}" method=""post>
    lastName:<input type="text" name="lastName"><br/>
    email:<input type="text" name="email"><br/>
    gender:<input type="radio" name="gender" value="1">male
    <input type="radio" name="gender" value="0">female<br/>
    <input type="submit" value="添加"><br/>
</form>
</body>
</html>
```

employee_update.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>update employee</title>
</head>
<body>
<form th:action="@{/employee}" method="post">
    <input type="hidden" name="_method" value="put">
    <input type="hidden" name="id" th:value="${employee.id}">
  lastName:<input type="text" name="lastName" th:value="${employee.lastName}"/><br/>
  email:<input type="text" name="email" th:value="${employee.email}"><br/>
    <!--
       th:field="${employee.gender}"可用于单选框或复选框的回显
       若单选框的value和employee.gender的值一致，则添加checked="checked"属性
   -->
  gender:<input type="radio" name="gender" value="1" th:field="${employee.gender}">male
  <input type="radio" name="gender" value="0" th:field="${employee.gender}">female<br/>
    <input type="submit" value="update">

</form>
</body>
</html>
```

Employee.java

```java
package com.springmvc.rest.bean;

public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email, Integer gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}

```

EmployeeDao.java

```java
package com.springmvc.rest.Dao;

import com.springmvc.rest.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {

    //模拟数据库中的数据
    public static Map<Integer, Employee> employees= null;

    //员工有所属的部门
    static {
        employees = new HashMap<Integer, Employee>();

        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com",1));
        employees.put(1002, new Employee(1002, "E-BB", "bb@163.com",0));
        employees.put(1003, new Employee(1003, "E-CC", "CC@163.com",1));
        employees.put(1004, new Employee(1004, "E-DD", "DD@163.com",1));
        employees.put(1005, new Employee(1005, "E-EE", "EE@163.com",1));
    }

    //主键自增
    private static Integer initId = 1006;

    //增加与修改员工
    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
    }
    //查询全部员工信息
    public Collection<Employee> getAll(){
        return employees.values();
    }
    //通过id查询员工
    public Employee get(Integer id){
        return employees.get(id);
    }
    //通过id删除员工
    public void delete(Integer id){
        employees.remove(id);
    }
}

```

EmployeeController.java

```java
package com.springmvc.rest.controller;

import com.springmvc.rest.Dao.EmployeeDao;
import com.springmvc.rest.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    //查询所有员工返回列表页面
    @RequestMapping(value = "/employee",method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList",employeeList);
        return "employee_list";
    }

    //删除员工
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/employee";
    }

    //添加员工
    @RequestMapping(value="/employee",method = RequestMethod.POST)
    public String addEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }

    //修改员工
    @RequestMapping(value="/employee/{id}",method = RequestMethod.GET)
    public String getEmployeeById(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "employee_update";
    }



    @RequestMapping(value="/employee",method = RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }
}

```

springMVC.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--    扫描组件-->
    <context:component-scan base-package="com.springmvc.rest"></context:component-scan>
    <!--    配置Thymeleaf视图解析器-->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!--                        视图前缀-->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!--                        视图后缀-->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

<!--    配置视图控制器-->
    <mvc:view-controller path="/"   view-name="index"></mvc:view-controller>
    <mvc:view-controller path="/toAdd" view-name="employee_add"></mvc:view-controller>
<!--    开启mvc注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>

<!--    开发对静态资源的访问-->
    <mvc:default-servlet-handler></mvc:default-servlet-handler>
</beans>
```

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
  <!--    配置springMVC的编码过滤器-->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!--    配置HiddenHttpMethodFilter过滤器-->
  <filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
    <!--    配置Servlet前端控制器-->
  <servlet>
    <servlet-name>SpringMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <!--        配置SpringMVC配置文件的位置和名称-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springMVC.xml</param-value>
    </init-param>

    <!--        将前端控制器DispatcherServlet的初始化时间提前到服务器启动-->
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>SpringMVC</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
        
```



# 九、HttpMessageConverter

HttpMessageConverter，报文信息转换器，将请求报文转换为Java对象，或将Java对象转换为响应报文

HttpMessageConverter提供了两个注解和两个类型：

@RequestBody，@ResponseBody，RequestEntity，ResponseEntity

## 1、@RequestBody

@RequestBody可以获取请求体，需要在控制器方法设置一个形参，使用@RequestBody进行标识，当前请求的请求体就会为当前注解所标识的形参赋值

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<form th:action="@{/testRequestBody}" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit" value="测试RequestBody">
</form>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>success</h1>
</body>
</html>
```

HttpController.java

```java
package com.srpingmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HttpController {


    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody){
        System.out.println("requestBody:"+requestBody);
        return "success";
    }

}

```

运行结果：

```
requestBody:username=test1&password=1
```



## 2、RequestEntity

RequestEntity封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给该形参，可以通过getHeaders()获取请求头信息，通过getBody()获取请求体信息

index.html

```html
<form th:action="@{/testRequestEntity}" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit" value="测试RequestEntity">
</form>
```

HttpController.java

```java
    @RequestMapping("/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity){
        //当前requestEntity表示整个请求报文的信息
        System.out.println("请求头:"+requestEntity.getHeaders());
        System.out.println("请求体:"+requestEntity.getBody());
        return "success";
    }
```

运行结果：

```
请求头:[host:"localhost:8080", connection:"keep-alive", content-length:"25", cache-control:"max-age=0", sec-ch-ua:""Chromium";v="106", "Google Chrome";v="106", "Not;A=Brand";v="99"", sec-ch-ua-mobile:"?0", sec-ch-ua-platform:""Windows"", upgrade-insecure-requests:"1", origin:"http://localhost:8080", user-agent:"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36", accept:"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9", sec-fetch-site:"same-origin", sec-fetch-mode:"navigate", sec-fetch-user:"?1", sec-fetch-dest:"document", referer:"http://localhost:8080/springMVC_demo4_war_exploded/", accept-encoding:"gzip, deflate, br", accept-language:"zh-CN,zh;q=0.9", cookie:"Idea-1cb5354=2c3874e6-94af-4d67-a05b-7d86ffd68398; firsturl=http%3A//localhost%3A63342/wz/wz/www.cssmoban.com/preview/indexc1be.html; Hm_lvt_a5ba743d0ea57bb0c5a7ad25181e4f7b=1657703256; Idea-1cb5ad4=9aeb17f6-6038-49d8-9e1b-b446ffa64042", Content-Type:"application/x-www-form-urlencoded;charset=UTF-8"]
请求体:username=test1&password=1
```



## 3、@ResponseBody

@ResponseBody用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器

```html
<!--response方法-->
<a th:href="@{/testResponse}">通过servletAPI的Resopnse对象响应浏览器数据</a><br>
<!--@ResponseBody方法-->
<a th:href="@{/testResponseBody}">通过@ResponseBody响应浏览器数据</a>
```

```java
	// Response
	@RequestMapping("/testResponse")
    public void testResponse(HttpServletResponse response) throws IOException {
        response.getWriter().print("hello response");
    }

	//@ResponseBody
    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody(){
        return "success";
    }
```



## 4、SpringMVC处理json

@ResponseBody处理json的步骤：

**（1）导入jackson的依赖**

```
   <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.13.4</version>
        </dependency>
```

**(2）在SpringMVC的核心配置文件中开启mvc的注解驱动，此时在HandlerAdaptor中会自动装配一个消息转换器：**MappingJackson2HttpMessageConverter，可以将响应到浏览器的Java对象转换为Json格式的字符串

```xml
    <!--    开启MVC的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
```

**（3）在处理器方法上使用@ResponseBody注解进行标识**

**（4）将Java对象直接作为控制器方法的返回值返回，就会自动转换为Json格式的字符串**

```java
 @RequestMapping("/testResponseUser")
 @ResponseBody
    public User testResponseUser(){
        return new User(1001,"admin","123456",18,"男");
    }
```

浏览器的页面展示的结果：

```
{"id":1001,"username":"admin","password":"123456","age":18,"sex":"男"}
```





## 5、SpringMVC处理ajax

**（1）请求超链接：**

```html
<div id="app">
    <a @click="testAxios" th:href="@{/testAxios}" >SpringMVC处理ajax</a><br>
```



**（2）通过vue和axios处理点击事件：**

```html
<script type="text/javascript" th:src="@{static/js/vue.js}"></script>
<script type="text/javascript" th:src="@{static/js/axios.min.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el: '#app',
        methods:{
            testAxios:function (event) {
                axios({
                    method: "post",
                    url: event.target.href,
                    param: {
                        username: "admin",
                        password: "123"
                    }
                }).then(function (response) {
                    alert(response.data);
                });
                event.preventDefault();
            }
        }
    });
 </script>
```





## 6、@RestController注解

@RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，并且为其中的每个方法添加了@ResponseBody注解



## 7、ResponEntity

ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文



# 十、文件上传和下载

## 1、文件下载

使用ResponseEntity实现下载文件的功能

FileUpAndDownController.java

```java

    @RequestMapping("/testFileDownload")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        System.out.println(realPath);
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字,filename下载默认名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

}

```

file.html

```html
<a th:href="@{/testFileDownload}">测试文件上传</a>
```



## 2、文件上传

文件上传要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"

SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

上传步骤：

**（1）添加依赖：**

pom.xml

```xml
   <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.4</version>
        </dependency>
```

**（2）在SpringMVC的配置文件中添加配置：**

```xml
<!--    配置文件上传解析器，将上传的文件解析成MultipartFile-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    </bean>
```

**（3）页面：**

```html
<form th:action="@{/testFileUpload}" method="post" enctype="multipart/form-data">
    头像：<input type="file" name="photo"><br>
    <input type="submit" value="上传">
</form>
```



**（4）控制器方法：**

```java
   @RequestMapping("/testFileUpload")
    public String testFileUpload(MultipartFile photo,HttpSession session) throws IOException {
        //获取上传文件的文件名
       String FileName = photo.getOriginalFilename();
       //获取上传文件的后缀名
        String suffixName = FileName.substring(FileName.lastIndexOf("."));
        //将UUID作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //将uuid和后缀名拼接后的结果作为最终的文件名
        FileName = uuid + suffixName;
       //通过ServletContext获取服务器photo目录的路径
       ServletContext servletContext = session.getServletContext();
       String photoPath = servletContext.getRealPath("photo");
       File file = new File(photoPath);
       //判断文件夹是否存在
         if (!file.exists()) {
             //若不存在，则创建文件夹
              file.mkdirs();
         }
         String finalPath = photoPath + File.separator+FileName;
         //将文件写入到服务器中
       photo.transferTo(new File(finalPath));
        return "success";
    }
```



# 十一、拦截器

## 1、拦截器的配置

SpringMVC中的拦截器用于拦截控制器方法的执行

SpringMVC中的拦截器需要实现HandlerInterceptor

SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置：

```xml
<!--    配置拦截器-->
<mvc:interceptors>
<!--     第一种：   <bean class="com.springmvc.interceptor.FirstInterceptor"></bean>-->
<!--     第二种：   <ref bean="firstInterceptor"></ref>-->
<!--     第三种：-->
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <mvc:exclude-mapping path="/index.html"/>
<!--     这两种都可   <bean class="com.springmvc.interceptor.FirstInterceptor"></bean>-->
        <ref bean="firstInterceptor"></ref>
    </mvc:interceptor>
</mvc:interceptors>
```

## 2、拦截器的三个抽象方法

SpringMVC中的拦截器有三个抽象方法：

preHandle：控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法

postHandle：控制器方法执行之后执行postHandle()

afterComplation：处理完视图和模型数据，渲染视图完毕之后执行afterComplation()



## 3、多个拦截器的执行顺序

**（1）若每个拦截器的preHandle()都返回true**

此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件的配置顺序有关：

preHandle()会按照配置的顺序执行，而postHandle()和afterComplation()会按照配置的反序执行

**（2）若某个拦截器的preHandle()返回了false**

preHandle()返回false和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false的拦截器之前的拦截器的afterComplation()会执行



所用文件：

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/testInterceptor}">测试拦截器</a>
</body>
</html>
```

success.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>success</h1>
</body>
</html>
```

TestController.java

```java
package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/**/testInterceptor")
    public String testInterceptor() {
        return "success";
    }
}

```

FirstInterceptor.java

```java
package com.springmvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FirstInterceptor implements HandlerInterceptor {
    // 在目标方法执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FirstInterceptor--> preHandle");
        return true;
    }

    // 在目标方法执行之后，视图对象返回之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("FirstInterceptor--> postHandle");
    }

    // 在流程都执行完毕后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("FirstInterceptor--> afterCompletion");
    }
}

```

SecondInterceptor.java

```java
package com.springmvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecondInterceptor implements HandlerInterceptor {
    // 在目标方法执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("SecondInterceptor--> preHandle");
        return true;
    }

    // 在目标方法执行之后，视图对象返回之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("SecondInterceptor--> postHandle");
    }

    // 在流程都执行完毕后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("SecondInterceptor--> afterCompletion");
    }
}


```

配置：

```xml
<!--    配置拦截器-->
    <mvc:interceptors>
        <ref bean="firstInterceptor"></ref>
        <ref bean="secondInterceptor"></ref>
    </mvc:interceptors>
```

运行结果：

```
FirstInterceptor--> preHandle
SecondInterceptor--> preHandle
SecondInterceptor--> postHandle
FirstInterceptor--> postHandle
SecondInterceptor--> afterCompletion
FirstInterceptor--> afterCompletion
```



# 十二、异常处理器

## 1、基于配置的异常处理

SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver

HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver

SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver，使用方式：

```xml
<!--    配置异常处理-->
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <property name="exceptionMappings">
            <props>
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
<!--        设置将异常信息共享在请求域中的键-->
        <property name="exceptionAttribute" value="ex"></property>
    </bean>
```



## 2、基于注解的异常处理

```java
//@ControllerAdvice将当前类标识为异常处理的组件
@ControllerAdvice
public class ExceptionController {

    //@ExceptionHandler用于设置所标识方法处理的异常类型
    @ExceptionHandler({ArithmeticException.class})
        //ex表示当前请求处理中出现的异常对象
        public String testExceptionHandler(Exception ex, Model model) {
           //将异常对象添加到Model中，使得下一个页面可以获取到异常信息
            model.addAttribute("ex",ex);
            System.out.println("出异常了：" + ex);
            return "error";
        }
}
```



# 十三、注解配置SpringMVC

使用配置类和注解代替web.xml和SpringMVC配置文件的功能

## 1、创建初始化类，代替web.xml

在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器。
Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文。

WebInit.java

```java
package com.springmvc.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 指定spring的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringConfig.class};
    }

    /**
     * 指定SpringMVC的配置类
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * 指定DispatcherServlet的映射规则，即url-pattern
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 注册过滤器
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter};
    }
}


```



## 2、创建SpringConfig配置类，代替spring的配置文件

SpringConfig.java

```java
package com.springmvc.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    //ssm整合之后，spring的配置信息写在此类中
}

```





## 3、创建WebConfig配置类，代替SpringMVC的配置文件

```java
package com.springmvc.config;

import com.springmvc.interceptor.TestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.List;
import java.util.Properties;

/**
 * 代替springMVC的配置文件
 * 1、扫描组件 2、视图解析器 3、视图控制器（view-controller） 4、开启静态资源访问（default-servlet-handler）
 * 5、mvc注解驱动 6、文件上传解析器 7、异常处理 8、拦截器
 */

//将当前类标识为一个配置类
@Configuration
//1、扫描组件
@ComponentScan()
//5、开启mvc注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    //2、视图解析器
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    //8、配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TestInterceptor testInterceptor = new TestInterceptor();
        registry.addInterceptor(testInterceptor).addPathPatterns("/**");
    }

    //3、配置视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
    }

    //6、文件上传解析器
    @Bean
    public MultipartResolver multipartResolver() {
        //return new StandardServletMultipartResolver();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    //7、异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("java.lang.ArithmeticException", "error");
        exceptionResolver.setExceptionMappings(properties);
        exceptionResolver.setExceptionAttribute("exception");
        resolvers.add(exceptionResolver);

    }

    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并未解析器注入模板引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}

```



## 4、测试功能

TestController.java

```java
package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}

```

TestInterceptor.java

```java
package com.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle-->preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle-->postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion-->afterCompletion");
    }
}

```

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/}">hello</a>
</body>
</html>
```

hello.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>hello</h1>
</body>
</html>
```





# 十四、SpringMVC执行流程

## 1、SpringMVC常用组件

- DispatcherServlet：**前端控制器**，不需要工程师开发，由框架提供

作用：统一处理请求和响应，整个流程控制的中心，由它调用其它组件处理用户的请求



- HandlerMapping：**处理器映射器**，不需要工程师开发，由框架提供

作用：根据请求的url、method等信息查找Handler，即控制器方法



- Handler：**处理器**，需要工程师开发

作用：在DispatcherServlet的控制下Handler对具体的用户请求进行处理



- HandlerAdapter：**处理器适配器**，不需要工程师开发，由框架提供

作用：通过HandlerAdapter对处理器（控制器方法）进行执行



- ViewResolver：**视图解析器**，不需要工程师开发，由框架提供

作用：进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView、RedirectView



- View：**视图**

作用：将模型数据通过页面展示给用户



## 2、DispatcherServlet初始化过程

DispatcherServlet 本质上是一个 Servlet，所以天然的遵循 Servlet 的生命周期。所以宏观上是 Servlet 生命周期来进行调度。

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016211206940-363772838.png)



##### （1）初始化WebApplicationContext

所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected WebApplicationContext initWebApplicationContext() {
    WebApplicationContext rootContext =
        WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    WebApplicationContext wac = null;

    if (this.webApplicationContext != null) {
        // A context instance was injected at construction time -> use it
        wac = this.webApplicationContext;
        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (!cwac.isActive()) {
                // The context has not yet been refreshed -> provide services such as
                // setting the parent context, setting the application context id, etc
                if (cwac.getParent() == null) {
                    // The context instance was injected without an explicit parent -> set
                    // the root application context (if any; may be null) as the parent
                    cwac.setParent(rootContext);
                }
                configureAndRefreshWebApplicationContext(cwac);
            }
        }
    }
    if (wac == null) {
        // No context instance was injected at construction time -> see if one
        // has been registered in the servlet context. If one exists, it is assumed
        // that the parent context (if any) has already been set and that the
        // user has performed any initialization such as setting the context id
        wac = findWebApplicationContext();
    }
    if (wac == null) {
        // No context instance is defined for this servlet -> create a local one
        // 创建WebApplicationContext
        wac = createWebApplicationContext(rootContext);
    }

    if (!this.refreshEventReceived) {
        // Either the context is not a ConfigurableApplicationContext with refresh
        // support or the context injected at construction time had already been
        // refreshed -> trigger initial onRefresh manually here.
        synchronized (this.onRefreshMonitor) {
            // 刷新WebApplicationContext
            onRefresh(wac);
        }
    }

    if (this.publishContext) {
        // Publish the context as a servlet context attribute.
        // 将IOC容器在应用域共享
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);
    }

    return wac;
}
```

##### （2）创建WebApplicationContext

所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected WebApplicationContext createWebApplicationContext(@Nullable ApplicationContext parent) {
    Class<?> contextClass = getContextClass();
    if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
        throw new ApplicationContextException(
            "Fatal initialization error in servlet with name '" + getServletName() +
            "': custom WebApplicationContext class [" + contextClass.getName() +
            "] is not of type ConfigurableWebApplicationContext");
    }
    // 通过反射创建 IOC 容器对象
    ConfigurableWebApplicationContext wac =
        (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);

    wac.setEnvironment(getEnvironment());
    // 设置父容器
    wac.setParent(parent);
    String configLocation = getContextConfigLocation();
    if (configLocation != null) {
        wac.setConfigLocation(configLocation);
    }
    configureAndRefreshWebApplicationContext(wac);

    return wac;
}
```

##### （3）DispatcherServlet初始化策略

FrameworkServlet创建WebApplicationContext后，刷新容器，调用onRefresh(wac)，此方法在DispatcherServlet中进行了重写，调用了initStrategies(context)方法，初始化策略，即初始化DispatcherServlet的各个组件

所在类：org.springframework.web.servlet.DispatcherServlet

```java
protected void initStrategies(ApplicationContext context) {
   initMultipartResolver(context);
   initLocaleResolver(context);
   initThemeResolver(context);
   initHandlerMappings(context);
   initHandlerAdapters(context);
   initHandlerExceptionResolvers(context);
   initRequestToViewNameTranslator(context);
   initViewResolvers(context);
   initFlashMapManager(context);
}
```





## 3、DispatcherServlet调用组件处理请求

##### （1）processRequest()

FrameworkServlet重写HttpServlet中的service()和doXxx()，这些方法中调用了processRequest(request, response)

所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    long startTime = System.currentTimeMillis();
    Throwable failureCause = null;

    LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
    LocaleContext localeContext = buildLocaleContext(request);

    RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());

    initContextHolders(request, localeContext, requestAttributes);

    try {
		// 执行服务，doService()是一个抽象方法，在DispatcherServlet中进行了重写
        doService(request, response);
    }
    catch (ServletException | IOException ex) {
        failureCause = ex;
        throw ex;
    }
    catch (Throwable ex) {
        failureCause = ex;
        throw new NestedServletException("Request processing failed", ex);
    }

    finally {
        resetContextHolders(request, previousLocaleContext, previousAttributes);
        if (requestAttributes != null) {
            requestAttributes.requestCompleted();
        }
        logResult(request, response, failureCause, asyncManager);
        publishRequestHandledEvent(request, response, startTime, failureCause);
    }
}
```

##### （2）doService()

所在类：org.springframework.web.servlet.DispatcherServlet

```java
@Override
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
    logRequest(request);

    // Keep a snapshot of the request attributes in case of an include,
    // to be able to restore the original attributes after the include.
    Map<String, Object> attributesSnapshot = null;
    if (WebUtils.isIncludeRequest(request)) {
        attributesSnapshot = new HashMap<>();
        Enumeration<?> attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = (String) attrNames.nextElement();
            if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
                attributesSnapshot.put(attrName, request.getAttribute(attrName));
            }
        }
    }

    // Make framework objects available to handlers and view objects.
    request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
    request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
    request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
    request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());

    if (this.flashMapManager != null) {
        FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
        if (inputFlashMap != null) {
            request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
        }
        request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
        request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
    }

    RequestPath requestPath = null;
    if (this.parseRequestPath && !ServletRequestPathUtils.hasParsedRequestPath(request)) {
        requestPath = ServletRequestPathUtils.parseAndCache(request);
    }

    try {
        // 处理请求和响应
        doDispatch(request, response);
    }
    finally {
        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            // Restore the original attribute snapshot, in case of an include.
            if (attributesSnapshot != null) {
                restoreAttributesAfterInclude(request, attributesSnapshot);
            }
        }
        if (requestPath != null) {
            ServletRequestPathUtils.clearParsedRequestPath(request);
        }
    }
}
```

##### （3）doDispatch()

所在类：org.springframework.web.servlet.DispatcherServlet

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        ModelAndView mv = null;
        Exception dispatchException = null;

        try {
            processedRequest = checkMultipart(request);
            multipartRequestParsed = (processedRequest != request);

            // Determine handler for the current request.
            /*
            	mappedHandler：调用链
                包含handler、interceptorList、interceptorIndex
            	handler：浏览器发送的请求所匹配的控制器方法
            	interceptorList：处理控制器方法的所有拦截器集合
            	interceptorIndex：拦截器索引，控制拦截器afterCompletion()的执行
            */
            mappedHandler = getHandler(processedRequest);
            if (mappedHandler == null) {
                noHandlerFound(processedRequest, response);
                return;
            }

            // Determine handler adapter for the current request.
           	// 通过控制器方法创建相应的处理器适配器，调用所对应的控制器方法
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            // Process last-modified header, if supported by the handler.
            String method = request.getMethod();
            boolean isGet = "GET".equals(method);
            if (isGet || "HEAD".equals(method)) {
                long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                    return;
                }
            }
			
            // 调用拦截器的preHandle()
            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                return;
            }

            // Actually invoke the handler.
            // 由处理器适配器调用具体的控制器方法，最终获得ModelAndView对象
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

            if (asyncManager.isConcurrentHandlingStarted()) {
                return;
            }

            applyDefaultViewName(processedRequest, mv);
            // 调用拦截器的postHandle()
            mappedHandler.applyPostHandle(processedRequest, response, mv);
        }
        catch (Exception ex) {
            dispatchException = ex;
        }
        catch (Throwable err) {
            // As of 4.3, we're processing Errors thrown from handler methods as well,
            // making them available for @ExceptionHandler methods and other scenarios.
            dispatchException = new NestedServletException("Handler dispatch failed", err);
        }
        // 后续处理：处理模型数据和渲染视图
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    }
    catch (Exception ex) {
        triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
    }
    catch (Throwable err) {
        triggerAfterCompletion(processedRequest, response, mappedHandler,
                               new NestedServletException("Handler processing failed", err));
    }
    finally {
        if (asyncManager.isConcurrentHandlingStarted()) {
            // Instead of postHandle and afterCompletion
            if (mappedHandler != null) {
                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
            }
        }
        else {
            // Clean up any resources used by a multipart request.
            if (multipartRequestParsed) {
                cleanupMultipart(processedRequest);
            }
        }
    }
}
```

##### （4）processDispatchResult()

```java
private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                   @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
                                   @Nullable Exception exception) throws Exception {

    boolean errorView = false;

    if (exception != null) {
        if (exception instanceof ModelAndViewDefiningException) {
            logger.debug("ModelAndViewDefiningException encountered", exception);
            mv = ((ModelAndViewDefiningException) exception).getModelAndView();
        }
        else {
            Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
            mv = processHandlerException(request, response, handler, exception);
            errorView = (mv != null);
        }
    }

    // Did the handler return a view to render?
    if (mv != null && !mv.wasCleared()) {
        // 处理模型数据和渲染视图
        render(mv, request, response);
        if (errorView) {
            WebUtils.clearErrorRequestAttributes(request);
        }
    }
    else {
        if (logger.isTraceEnabled()) {
            logger.trace("No view rendering, null ModelAndView returned.");
        }
    }

    if (WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
        // Concurrent handling started during a forward
        return;
    }

    if (mappedHandler != null) {
        // Exception (if any) is already handled..
        // 调用拦截器的afterCompletion()
        mappedHandler.triggerAfterCompletion(request, response, null);
    }
}
```

## 4、SpringMVC的执行流程

1.用户向服务器发送请求，请求被SpringMVC 前端控制器 DispatcherServlet捕获。

2.DispatcherServlet对请求URL进行解析，得到请求资源标识符（URI），判断请求URI对应的映射：

​	:star:不存在:

​		i. 再判断是否配置了mvc:default-servlet-handler

​		ii. 如果没配置，则控制台报映射查找不到，客户端展示404错误

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016223522561-705958377.png)



![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016223541273-1521917481.png)

​		iii. 如果有配置，则访问目标资源（一般为静态资源，如：JS,CSS,HTML），找不到客户端也会展示404错误

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016223627304-122284853.png)

![image](https://img2022.cnblogs.com/blog/2682250/202210/2682250-20221016223649855-1565618623.png)



:star:存在则执行下面的流程:

3) 根据该URI，调用HandlerMapping获得该Handler配置的所有相关的对象（包括Handler对象以及Handler对象对应的拦截器），最后以HandlerExecutionChain执行链对象的形式返回。

4) DispatcherServlet 根据获得的Handler，选择一个合适的HandlerAdapter。

5) 如果成功获得HandlerAdapter，此时将开始执行拦截器的preHandler(…)方法【正向】

6) 提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller)方法，处理请求。在填充Handler的入参过程中，根据你的配置，Spring将帮你做一些额外的工作：

​			①HttpMessageConveter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息

​			②数据转换：对请求消息进行数据转换。如String转换成Integer、Double等

​			③数据格式化：对请求消息进行数据格式化。 如将字符串转换成格式化数字或格式化日期等

​			④数据验证： 验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中

7) Handler执行完成后，向DispatcherServlet 返回一个ModelAndView对象。

8) 此时将开始执行拦截器的postHandle(...)方法【逆向】。

9) 根据返回的ModelAndView（此时会判断是否存在异常：如果存在异常，则执行HandlerExceptionResolver进行异常处理）选择一个适合的ViewResolver进行视图解析，根据Model和View，来渲染视图。

10) 渲染视图完毕执行拦截器的afterCompletion(…)方法【逆向】。

11) 将渲染结果返回给客户端。
