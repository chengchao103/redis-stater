package star.xiaolei.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by 周高磊
 * Date: 2017/5/4.
 * Email: gaoleizhou@gmail.com
 * Desc: Sentinel模式测试
 */
@ActiveProfiles("sentinel")
public class JedisSentinelTemplateTest extends BaseTest {

    @Autowired
    private JedisTemplate jedisTemplate;

    @Before
    public void init() {
        jedisTemplate.execute(jedis -> {
            jedis.set("test", "1");
        });
    }

    @Test
    public void testGetWithResult() {
        String value = jedisTemplate.execute(jedis -> {
            return jedis.get("test");
        });
        assertThat(value, is("1"));
    }

}
