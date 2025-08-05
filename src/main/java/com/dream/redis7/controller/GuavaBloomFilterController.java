package com.dream.redis7.controller;

import com.dream.redis7.service.GuavaBloomFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author huzejun
 * @Date 2025-08-02 13:11
 **/
@Api(tags = "google工具Guava处理布隆过滤器")
@RestController
@Slf4j
public class GuavaBloomFilterController {

    @Resource
    private GuavaBloomFilterService guavaBloomFilterService;

    @ApiOperation("guava布隆过滤器插入100w样本数据并额外10w测试是否存在")
    @RequestMapping(value="/guavafilter",method = RequestMethod.GET)
    public void guavaBloomFilter() {
        guavaBloomFilterService.guavaBloomFilter();
    }


}
