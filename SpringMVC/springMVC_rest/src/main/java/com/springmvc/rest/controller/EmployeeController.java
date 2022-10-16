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
