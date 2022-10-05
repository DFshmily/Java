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
