package com.example.clouddisk;

import com.example.clouddisk.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class ClouddiskApplicationTests {
    @Autowired
    private JedisPool jedisPool;

    @Test
    void contextLoads() {


    }

}
