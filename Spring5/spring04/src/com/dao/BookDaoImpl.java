package com.dao;

import com.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


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


    //查询返回对象
    @Override
    public Book findBookInfo(String id) {
        String sql = "select * from t_user where id=?";
        //调用方法
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }


    //查询返回集合
    @Override
    public List<Book> findAllBook() {
        String sql = "select * from t_user";
        //调用方法
        List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return bookList;
    }

    //批量添加
    @Override
    public void batchAddBook(List<Object[]> batchArgs) {
        String sql = "insert into t_user values(?,?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql,batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    //批量修改
    @Override
    public void batchUpdateBook(List<Object[]> batchArgs) {
        String sql = "update t_user set username=?,password=?,email=? where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    //批量删除
    @Override
    public void batchDeleteBook(List<Object[]> batchArgs) {
        String sql = "delete from t_user where id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
}
