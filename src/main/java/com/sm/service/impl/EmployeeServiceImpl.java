package com.sm.service.impl;

import com.sm.mapper.EmployeeMapper;
import com.sm.pojo.Employee;
import com.sm.pojo.EmployeeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor on 2017/6/18.
 */
@Service
public class EmployeeServiceImpl implements com.sm.service.EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    @Override
    public List<Employee> getAll() {
        // TODO Auto-generated method stub
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 员工保存
     * @param employee
     */
    @Override
    public void saveEmp(Employee employee) {
        // TODO Auto-generated method stub
        employeeMapper.insertSelective(employee);
    }

    /**
     * 检验用户名是否可用
     *
     * @param empName
     * @return  true：代表当前姓名可用   fasle：不可用
     */
    @Override
    public boolean checkUser(String empName) {
        // TODO Auto-generated method stub
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }
    /**
     * 按照员工id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee getEmp(Integer id) {
        // TODO Auto-generated method stub
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }
    /**
     * 员工更新
     * @param employee
     */
    @Override
    public void updateEmp(Employee employee) {
        // TODO Auto-generated method stub
        employeeMapper.updateByPrimaryKeySelective(employee);
    }
    /**
     * 员工删除
     * @param id
     */
    @Override
    public void deleteEmp(Integer id) {
        // TODO Auto-generated method stub
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        // TODO Auto-generated method stub
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
