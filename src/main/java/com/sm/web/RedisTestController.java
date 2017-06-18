package com.sm.web;

import com.sm.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor on 2017/6/18.
 */
@RestController
@RequestMapping("/redis")
@Api
public class RedisTestController {
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("add")
    @ResponseBody
    @ApiOperation(value = "Redis测试")
    public Object add() throws Exception {
        redisUtil.set("0","the first time to connect");
        return redisUtil.get("0");
    }
}
