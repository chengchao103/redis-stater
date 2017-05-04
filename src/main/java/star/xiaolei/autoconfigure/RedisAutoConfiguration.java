package star.xiaolei.autoconfigure;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;
import redis.clients.util.Pool;
import star.xiaolei.client.JedisTemplate;
import star.xiaolei.properties.RedisProperties;

import java.util.Set;

/**
 * Created by 周高磊
 * Date: 2017/5/2.
 * Email: gaoleizhou@gmail.com
 * Desc: 自动组装Redis配置信息
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @Autowired
    private RedisProperties properties;

    /**
     * 构建连接池配置信息
     * @return 连接池配置
     */
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(properties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(properties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(properties.isTestOnBorrow());
        return jedisPoolConfig;
    }

    /**
     * 构建连接池
     * @param jedisPoolConfig 连接池配置信息
     * @return Pool<Jedis>: JedisPool/JedisSentinelPool 连接池
     */
    @Bean
    public Pool<Jedis> getJedisPool(JedisPoolConfig jedisPoolConfig) {
        if(properties.isCluster()) {
            String[] sentinelProps =  properties.getSentinelHosts().split(",");
            Set<String> sentinelHosts = Sets.newHashSet(sentinelProps);
            if(Strings.isNullOrEmpty(properties.getAuth())) {
                return new JedisSentinelPool(properties.getSentinelMasterName(), sentinelHosts, jedisPoolConfig);
            }
            return new JedisSentinelPool(properties.getSentinelMasterName(), sentinelHosts, jedisPoolConfig, Protocol.DEFAULT_TIMEOUT, properties.getAuth());
        }

        if(Strings.isNullOrEmpty(properties.getAuth())) {
            return new JedisPool(jedisPoolConfig, properties.getHost(), properties.getPort());
        }
        return new JedisPool(jedisPoolConfig, properties.getHost(), properties.getPort(), Protocol.DEFAULT_TIMEOUT, properties.getAuth());
    }

    /**
     * 构建连接客户端通用模板
     * @param jedisPool 连接池
     * @return 客户端可用连接
     */
    @Bean
    public JedisTemplate getRedisTemplate(Pool<Jedis> jedisPool) {
        return new JedisTemplate(jedisPool);
    }

}
