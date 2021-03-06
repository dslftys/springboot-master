package com.sm.web;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.pojo.Employee;
import com.sm.pojo.Msg;
import com.sm.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 处理员工CRUD请求
 * Created by Victor on 2017/5/18.
 */
@RestController
@Api
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * 单个批量二合一
     * 批量删除1-2-3
     * 单个删除1
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "单个删除和多个删除")
    public Msg deleteEmp(@PathVariable("ids")String ids){
        //批量删除
        if(ids.contains("-")){
            List<Integer> del_ids=new ArrayList<Integer>();
            String[] str_ids=ids.split("-");
            //组合id的集合
            for(String string:str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        }else {
            Integer id=Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return  Msg.success();
    }
    @RequestMapping("/emps")
    @ResponseBody
    @ApiOperation(value ="多条内容查询")
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue = "1")Integer pn){
        //查询很多数据，不是分页查询
        //引入PageHelper插件
        //在查询之前只需要调用,传入页面以及每页的大小
        PageHelper.startPage(pn,5);
        //startPAge后面紧跟的这个查询就是分页查询
        List<Employee> emps=employeeService.getAll();
        //用PageInfo对结果进行包装,只需要将pageInfo交给页面就行
        //封装了详细的分页信息，包括我们查询出来的数据,传入连续显示的页数5
        PageInfo page = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }

    /**
     * 如果直接发送ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014, empName=null, gender=null, email=null, dId=null]
     *
     * 问题：
     * 请求体中有数据；
     * 但是Employee对象封装不上；
     * update tbl_emp  where emp_id = 1014;
     *
     * 原因：
     * Tomcat：
     * 		1、将请求体中的数据，封装一个map。
     * 		2、request.getParameter("empName")就会从这个map中取值。
     * 		3、SpringMVC封装POJO对象的时候。
     * 				会把POJO中每个属性的值，request.getParamter("email");
     * AJAX发送PUT请求引发的血案：
     * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * org.apache.catalina.connector.Request--parseParameters() (3111);
     *
     * protected String parseBodyMethods = "POST";
     * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     success = true;
     return;
     }
     *
     *
     * 解决方案；
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 1、配置上HttpPutFormContentFilter；
     * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     * 员工更新方法
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{empId}",method= RequestMethod.PUT)
    @ApiOperation(value = "保存员工数据")
    public Msg saveEmp(Employee employee,HttpServletRequest request){
        System.out.println("请求体中的值："+request.getParameter("gender"));
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success()	;
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @RequestMapping(value="/emp/{id}",method= RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id查询员工")
    public Msg getEmp(@PathVariable("id")Integer id){

        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    @ApiOperation(value = "查询用户名是否可用")
    public Msg checkuser(@RequestParam("empName")String empName){
        //先判断用户名是否是合法的表达式;
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }

        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 员工保存
     * 1、支持JSR303校验
     * 2、导入Hibernate-Validator
     *
     *
     * @return
     */
    @RequestMapping(value="/emp",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "员工保存")
    public Msg saveEmp(@Valid Employee employee, BindingResult result){

        if(result.hasErrors()){
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else{
           // System.out.print(employee);
           // System.out.print(result);

            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }

    /**
     * 查询员工数据(分页查询)
     * @return
     */
   // @RequestMapping("/emps")
    public String getEmps(@RequestParam(value="pn",defaultValue = "1")Integer pn,
                          Model model){
        //查询很多数据，不是分页查询
        //引入PageHelper插件
        //在查询之前只需要调用,传入页面以及每页的大小
        PageHelper.startPage(pn,5);
        //startPAge后面紧跟的这个查询就是分页查询
        List<Employee> emps=employeeService.getAll();
        //用PageInfo对结果进行包装,只需要将pageInfo交给页面就行
        //封装了详细的分页信息，包括我们查询出来的数据,传入连续显示的页数5
        PageInfo page = new PageInfo(emps,5);
        model.addAttribute("pageInfo",page);
        return "list";
    }

}
