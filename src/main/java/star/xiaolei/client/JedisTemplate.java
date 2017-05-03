package star.xiaolei.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by 周高磊
 * Date: 2017/5/2.
 * Email: gaoleizhou@gmail.com
 * Desc: 客户端连接通用模板
 */
public class JedisTemplate {

    private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

    private JedisPool jedisPool;

    public JedisTemplate(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取连接池
     * @return jedisPool
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 执行一个有返回结果的回调
     * @param jedisActionWithResult 有返回结果的回调接口
     * @param <T> 返回结果泛型
     * @return 返回结果
     * @throws JedisException 异常
     */
    public <T> T execute(JedisActionWithResult<T> jedisActionWithResult) throws JedisException {
        return execute(jedisActionWithResult, 0);
    }

    /**
     * 执行一个有返回结果的回调
     * @param jedisActionWithResult 有返回结果的回调接口
     * @param dbIndex 库位
     * @param <T> 返回结果泛型
     * @return 返回结果
     * @throws JedisException 异常
     */
    public <T> T execute(JedisActionWithResult<T> jedisActionWithResult, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            return jedisActionWithResult.action(jedis);
        } catch (Exception e) {
            logger.error("Redis lose connection.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    /**
     * 执行一个无返回结果的回调
     * @param jedisActionNoResult 回调Action
     * @throws JedisException
     */
    public void execute(JedisActionNoResult jedisActionNoResult) throws JedisException {
        execute(jedisActionNoResult, 0);
    }

    /**
     * 执行一个无返回结果的回调
     * @param jedisActionNoResult 无返回结果的回调接口
     * @param dbIndex 库位
     * @throws JedisException 异常
     */
    public void execute(JedisActionNoResult jedisActionNoResult, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            jedisActionNoResult.action(jedis);
        } catch (Exception e) {
            logger.error("Redis lose connection.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    /**
     * 有返回结果的回调接口
     */
    public interface JedisActionWithResult<T> {
        T action(Jedis jedis);
    }

    /**
     * 无返回结果的回调接口
     */
    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }

    /**
     * 关闭连接资源
     * @param jedis jedis客户端
     * @param connectionBroken connection status
     */
    public void closeResource(Jedis jedis, boolean connectionBroken) {
        if(jedis != null) {
            if(connectionBroken) {
                jedisPool.returnBrokenResource(jedis);
            } else {
                jedisPool.returnResource(jedis);
            }
        }
    }

}
