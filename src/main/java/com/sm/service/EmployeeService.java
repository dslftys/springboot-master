package com.sm.service;

import com.sm.pojo.Employee;

import java.util.List;

/**
 * Created by Victor on 2017/6/18.
 */
public interface EmployeeService {
    public List<Employee> getAll();
    public void saveEmp(Employee employee);
    public boolean checkUser(String empName);
    public Employee getEmp(Integer id);
    public void updateEmp(Employee employee);
    public void deleteEmp(Integer id);
    public void deleteBatch(List<Integer> ids);
}
