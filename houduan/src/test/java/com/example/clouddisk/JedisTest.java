package com.example.clouddisk;

import com.example.clouddisk.entity.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

@SpringBootTest
public class JedisTest {
    @Autowired
    JedisPool jedisPool;

    @Test
    public void test01(){
        Jedis resource = jedisPool.getResource();
        resource.set("test","sprigboot");
        String test = resource.get("test");
        System.out.println(test);
    }

    @Test
    public void test02(){
        List<UserInfo> userList = new ArrayList<>();
        Date date = new Date();
        UserInfo u1 = new UserInfo();
        u1.setId(1L);
        userList.add(u1);
        UserInfo u2 = new UserInfo();
        u1.setId(2L);
        userList.add(u2);
        UserInfo u3 = new UserInfo();
        u1.setId(3L);
        userList.add(u3);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(userList);
            Jedis resource = jedisPool.getResource();
            resource.set("users",json);

            String users = resource.get("users");
            System.out.println(users);

            List<UserInfo> ul = objectMapper.readValue(users,List.class);
            System.out.println(ul.size());
            System.out.println(Arrays.toString(ul.toArray()));

        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
