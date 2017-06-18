package com.sm.service.impl;

import com.sm.mapper.DepartmentMapper;
import com.sm.pojo.Department;
import com.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor on 2017/6/18.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public List<Department> getDepts() {
        // TODO Auto-generated method stub
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }
}
