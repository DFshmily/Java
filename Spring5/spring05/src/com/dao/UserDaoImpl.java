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
    public  void addMoney() {
        String sql = "update count set money=money+? where username=?";
        JdbcTemplate.update(sql, 100, "df");
    }

    //少钱
    @Override
    public void reduceMoney() {
        String sql = "update count set money=money-? where username=?";
        JdbcTemplate.update(sql,100,"dd");
    }
}
