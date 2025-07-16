package com.dream.redis7.controller;

import com.dream.redis7.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author huzejun
 * @Date 2025-07-16 9:15
 **/
@RestController
@Slf4j
@Api(tags = "订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation("新增订单接口")
    @RequestMapping(value = "/order/add",method = RequestMethod.POST)
    public void addOrder() {
        orderService.addOrder();
    }

    @ApiOperation("按照keyId查询订单")
    @RequestMapping(value = "/order/{keyId}",method = RequestMethod.GET)
    public String getOrderById(@PathVariable Integer keyId) {
        return orderService.getOrderById(keyId);
    }


}
