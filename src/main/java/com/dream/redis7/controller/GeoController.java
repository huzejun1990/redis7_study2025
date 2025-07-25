package com.dream.redis7.controller;

import com.dream.redis7.service.GeoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import org.springframework.data.geo.Point;

/**
 * @Author huzejun
 * @Date 2025-07-25 8:14
 **/

@Api(tags="美团地图位置附近的酒店推送GEO")
@RestController
@Slf4j
public class GeoController {

    @Resource
    private GeoService geoService;

    @ApiOperation("添加坐标geoadd")
    @RequestMapping(value = "/geoadd",method = RequestMethod.GET)
    public String getAdd() {
        return geoService.geoAdd();
    }

    @ApiOperation("获取经纬度坐标geopos")
    @RequestMapping(value = "/geopos",method = RequestMethod.GET)
    public Point position(String member) {
        return geoService.position(member);
    }

    @ApiOperation("获取经纬度生成的base32编码值geohash")
    @RequestMapping(value = "/geohash",method = RequestMethod.GET)
    public String hash(String member) {
        return geoService.hash(member);
    }

    @ApiOperation("获取两个给定位置之间的距离")
    @RequestMapping(value = "/geodist",method = RequestMethod.GET)
    public Distance distance(String member1, String member2) {
        return geoService.distance(member1, member2);
    }

    @ApiOperation("通过经纬度查找洛阳白马寺附近的")
    @RequestMapping(value = "/georadius",method = RequestMethod.GET)
    public GeoResults radiusByxy() {
        return geoService.radiusByxy();
    }

//    @ApiOperation("通过地方查找附近,白马寺为例")
    @ApiOperation("通过地方查找附近,本例写死天安门作为地址")
    @RequestMapping(value = "georadiusByMember",method = RequestMethod.GET)
    public GeoResults radiusMember() {
        return geoService.radiusByMember();
    }
}
