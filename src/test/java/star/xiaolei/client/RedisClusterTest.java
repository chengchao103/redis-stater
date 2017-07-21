package star.xiaolei.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import redis.clients.jedis.JedisCluster;

/**
 * Author: gaoleizhou@gmail.com
 * Created At 2017/7/16.
 * Desc: default
 */
@ActiveProfiles("cluster")
public class RedisClusterTest extends BaseTest {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void get(){
        System.out.println("=============="+jedisCluster.get("foo"));
    }

}
