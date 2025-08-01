package com.dream.redis7.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huzejun
 * @Date 2025-07-25 8:17
 **/
@Service
@Slf4j
public class GeoService {

    public static final String CITY = "city";

    @Autowired
    private RedisTemplate redisTemplate;

    public String geoAdd() {
        Map<String, Point> map = new HashMap<>();
        map.put("天安门",new Point(116.403963,39.915119));
        map.put("故宫",new Point(116.403414,39.924091));
        map.put("长城",new Point(116.024067,40.362639));

        redisTemplate.opsForGeo().add(CITY,map);
        return map.toString();
//        return null;
    }

    public Point position(String member) {
        //获取经纬度坐标
        List<Point> list = redisTemplate.opsForGeo().position(CITY, member);
        return list.get(0);
    }

    public String hash(String member) {
        //geohash算法生成的base32编码值
        List<String> list = redisTemplate.opsForGeo().hash(CITY, member);
        return list.get(0);
    }

    public Distance distance(String member1, String member2) {
        //获取两个给定位置之间的距离
        Distance distance = redisTemplate.opsForGeo().distance(CITY, member1, member2,
                RedisGeoCommands.DistanceUnit.KILOMETERS);
        return distance;
    }

    public GeoResults radiusByxy() {
        //通过经度，纬度查找附近的，北京王府井位置116.41817,39.914402
        Circle circle = new Circle(116.41817, 39.914402, Metrics.KILOMETERS.getMultiplier());
        //返回50条
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortDescending().limit(50);

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = redisTemplate.opsForGeo().radius(CITY, circle, args);

        return geoResults;
    }

    public GeoResults radiusByMember() {
        //通过地方查找附近,洛阳白马寺为例
        String member = "白马寺";
        // 返回10条
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(10);
        // 半径 10 公里内
        Distance distance = new Distance(10, Metrics.KILOMETERS);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = redisTemplate.opsForGeo().radius(CITY, member, distance, args);

        return geoResults;
    }



}
