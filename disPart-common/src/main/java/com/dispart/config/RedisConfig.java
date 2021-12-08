package com.dispart.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * @author: wujie
 * @create: 2019-09-25 16:49
 **/
@Configuration
public class RedisConfig {

    /**
     * 对象模板自定义存储序列化
     *
     * @param redisConnectionFactory
     * @return RedisTemplate
     */
    /*@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }*/
    /**
     * redis序列化方式选择：
     * 1.默认的JdkSerializationRedisSerializer序列化方式，其编码是ISO-8859-1,会出现乱码问题
     * 2.StringRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题；序列化字符串无双引号
     * 3.Jackson2JsonRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题，但是字符串会有一个双引号
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置Key序列化要使用的模板，默认是JdkSerializationRedisSerializer
        redisTemplate.setKeySerializer(stringSerializer());
        //设置value序列化要使用的模板，默认是JdkSerializationRedisSerializer
        redisTemplate.setValueSerializer(jacksonSerializer());
        //设置次莫版要使用的哈希key(或field)序列化程序，默认是JdkSerializationRedisSerializer
        redisTemplate.setHashKeySerializer(stringSerializer());
        //设置此模板要使用的哈希值序列化程序，默认是JdkSerializationRedisSerializer
        redisTemplate.setHashValueSerializer(jacksonSerializer());
        return redisTemplate;
    }

    /**
     * 初始化string序列化对象
     * @return
     */
    @Bean
    public RedisSerializer stringSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 初始化jackson序列化对象
     */
    @Bean
    public RedisSerializer jacksonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        //更改自动检测的属性类
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }



    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    /**
     * 字符串模板
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
