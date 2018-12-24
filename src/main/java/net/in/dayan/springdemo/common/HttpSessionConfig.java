package net.in.dayan.springdemo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

    @Autowired
    Environment env;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("app.redis.port")));
        jedisConnectionFactory.setHostName(env.getProperty("app.redis.host"));
//        jedisConnectionFactory.setTimeout(60);
//        jedisConnectionFactory.setPassword("password");
        return jedisConnectionFactory;
    }

}