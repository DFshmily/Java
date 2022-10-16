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
