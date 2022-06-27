package com.example.clouddisk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootConfiguration
@PropertySource(value = {"classpath:redis/jedis.properties"})
public class RedisConfiguration {
    @Value("${maxTotal}")
    private Integer maxTotal;
    @Value("${host}")
    private String host;
    @Value("${port}")
    private Integer port;
    @Value("${maxIdle}")
    private Integer maxIdle;

    public JedisPoolConfig jedisPoolConfig(){    //这个是修改redis性能的时候需要的对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        return jedisPoolConfig;
    }

    @Bean  //这个注解注入工厂的名称是方法名
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = jedisPoolConfig();
        return new JedisPool(jedisPoolConfig,host,port);
    }

}
