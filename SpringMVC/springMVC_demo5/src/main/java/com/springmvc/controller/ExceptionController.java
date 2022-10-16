package com.springmvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
