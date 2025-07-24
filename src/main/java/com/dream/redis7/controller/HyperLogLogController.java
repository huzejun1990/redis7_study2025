package com.dream.redis7.controller;

import com.dream.redis7.service.HyperLogLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author huzejun
 * @Date 2025-07-24 17:40
 **/
@Api(tags = "淘宝亿级UV的Redis方案")
@RestController
@Slf4j
public class HyperLogLogController {

    @Resource
    HyperLogLogService hyperLogLogService;

    @ApiOperation("获得IP去重复后的UV统计访问量")
    @RequestMapping(value = "/uv",method = RequestMethod.GET)
    public long uv() {
        return hyperLogLogService.uv();
    }

}
