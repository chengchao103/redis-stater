package star.xiaolei.autoconfigure;

import org.junit.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import star.xiaolei.client.JedisTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by 周高磊
 * Date: 2017/5/2.
 * Email: gaoleizhou@gmail.com
 * Desc: 自动配置测试
 */
@ActiveProfiles("test")
public class RedisAutoConfigurationTest {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Test
    public void testNoConfig() throws Exception {
        this.context.register(RedisAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class);
        this.context.refresh();
        assertThat(this.context.getBeanNamesForType(JedisPoolConfig.class).length, is(0));
    }

    @Test
    public void testWithFullConfig() {
        EnvironmentTestUtils.addEnvironment(this.context,
                "redis.host: 127.0.0.1",
                "redis.post: 6379",
                "redis.maxTotal: 5",
                "redis.maxIdle: 0",
                "redis.maxWaitMillis: 10000\n");
        this.context.register(
                RedisAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class);
        this.context.refresh();
        assertThat(this.context.getBeanNamesForType(JedisPoolConfig.class).length, is(1));
        assertThat(this.context.getBeanNamesForType(JedisPool.class).length, is(1));
        assertThat(this.context.getBeanNamesForType(JedisTemplate.class).length, is(1));
    }

}