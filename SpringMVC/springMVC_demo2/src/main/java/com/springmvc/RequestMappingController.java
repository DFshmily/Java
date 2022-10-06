package com.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {

    //RequestMapping注解的value属性
    //可以访问value中的任意一个路径
    @RequestMapping(
            value = {"/testRequestMapping","/hello"},
            method = {RequestMethod.GET,RequestMethod.POST}//RequestMethod.GET不支持POST的请求,要加RequestMethod.POST
    )



    //此时请求映射所映射的请求路径为：/hello/testRequestMapping
    //@RequestMapping("/testRequestMapping")
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

    @RequestMapping(value = "/testPut",method = RequestMethod.PUT)
    public String testPut(){
        return "success";
    }
}
