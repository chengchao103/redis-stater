package star.xiaolei.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by 周高磊
 * Date: 2017/5/3.
 * Email: gaoleizhou@gmail.com
 * Desc:
 */
public class JedisTemplateTest extends BaseTest {

    @Autowired
    private JedisTemplate jedisTemplate;

    @Before
    public void init() {
        jedisTemplate.execute(jedis -> {
           jedis.set("test", "1");
        });
    }

    /**
     * 测试有返回结果的回调接口 JedisActionWithResult
     */
    @Test
    public void testGetWithResult() {
        String value = jedisTemplate.execute(jedis -> {
            return jedis.get("test");
        });
        assertThat(value, is("1"));
    }

    /**
     * 测试无返回结果的回调接口 JedisActionNoResult
     */
    @Test
    public void testGetNoResult() {
        jedisTemplate.execute(jedis -> {
            jedis.set("test", "test");
        });
    }
}
