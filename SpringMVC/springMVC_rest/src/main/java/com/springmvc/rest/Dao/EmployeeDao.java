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
