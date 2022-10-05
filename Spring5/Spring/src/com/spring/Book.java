package com.spring;

public class Book {
    private String bname;

    public void setBname(String bname) {
        this.bname = bname;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bname='" + bname + '\'' +
                '}';
    }
}
