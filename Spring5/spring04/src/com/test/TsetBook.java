package com.test;

import com.entity.Book;
import com.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

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
//        int count = bookService.findCount();
//        System.out.println(count);



        //查询返回对象
//        Book book =bookService.findOne("1");
//        System.out.println(book);

        //查询返回集合
//        List<Book> all = bookService.findAll();
//        System.out.println(all);

        //批量添加
//       List<Object[]> batchArgs = new ArrayList<>();
//       Object[] o1 = {"7","python","p","123@qq.com"};
//       Object[] o2 = {"8","c#","cc","123@qq.com"};
//       batchArgs.add(o1);
//       batchArgs.add(o2);
       //调用批量添加
//        bookService.batchAdd(batchArgs);


        //批量修改
//        List<Object[]> batchArgs = new ArrayList<>();
//        Object[] o1 = {"ddd","d","520@qq.com","7"};
//        Object[] o2 = {"fff","f","520@qq.com","8"};
//        batchArgs.add(o1);
//        batchArgs.add(o2);

        //调用批量修改
//        bookService.batchUpdate(batchArgs);

        //批量删除
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"2"};
        Object[] o2 = {"4"};
        batchArgs.add(o1);
        batchArgs.add(o2);

        //调用批量删除
        bookService.batchDelete(batchArgs);

    }

}

