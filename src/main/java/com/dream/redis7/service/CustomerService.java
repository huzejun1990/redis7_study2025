package com.dream.redis7.service;

import com.dream.redis7.entities.Customer;
import com.dream.redis7.mapper.CustomerMapper;
import com.dream.redis7.utils.CheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author huzejun
 * @Date 2025-07-29 18:27
 **/

@Service
@Slf4j
public class CustomerService {

    public static final String CACHA_KEY_CUSTOMER = "customer:";

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CheckUtils checkUtils;

    /**
     * 写操作
     * @param customer
     */
    public void addCustomer(Customer customer) {
        int i = customerMapper.insert(customer);
        if (i > 0) {
            //mysql插入成功，需要重新查询一次将数据捞出来，写进redis
            Customer result = customerMapper.selectByPrimaryKey(customer.getId());
            //redis缓存key
            String key = CACHA_KEY_CUSTOMER + customer.getId();
            //捞出来的数据写进redis
            redisTemplate.opsForValue().set(key, result);
        }
    }

    public Customer findCustomerById(Integer customerId) {
        Customer customer = null;
        //缓存redis的key名称
        String key = CACHA_KEY_CUSTOMER + customerId;
        //1 先去redis查询
        customer = (Customer) redisTemplate.opsForValue().get(key);
        //2 redis有直接返回，没有再进去查询mysql
        if (customer == null) {
            // 3 再去查询我们的mysql
            customerMapper.selectByPrimaryKey(customerId);
            //3.1 mysql有，redis无
            if (customer != null) {
                //3.2把mysql查询出来的数据，回写redis，保持双写一致性
                redisTemplate.opsForValue().set(key, customer);
            }
        }

        return customer;

    }

    /**
     * BloomFilter ->redis->mysql
     * 白名单：whitelistCustomer
     * @param customerId
     * @return
     */
    public Customer findCustomerByIdWithBloomFilter(Integer customerId) {
        Customer customer = null;
        //缓存key的名称
        String key = CACHA_KEY_CUSTOMER + customerId;

        //布隆运之掌上器check,无是绝对无，有是可能有
        //=============================================== // whitelistCustomer
        if (!checkUtils.checkWithBloomFilter("whitelistCustomer",key)){
            log.info("白名单无此顾客，不可以访问: "+key);
            return null;
        }
        //===============================================

        //1 查询redis
        customer = (Customer) redisTemplate.opsForValue().get(key);
        //redis无，进一步查询mysql
        if (customer == null) {
            //2 从mysql查出来customer
            customer = customerMapper.selectByPrimaryKey(customerId);
            // mysql有，redis无
            if (customer != null) {
                //3 把mysql捞到的数据写入redis,方便下次查询能redis命中
                redisTemplate.opsForValue().set(key, customer);
            }

        }
        return customer;
    }
}
