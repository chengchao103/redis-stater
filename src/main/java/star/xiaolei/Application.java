package star.xiaolei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.xiaolei.client.JedisTemplate;

/**
 * Created by 周高磊
 * Date: 2017/4/28.
 * Email: gaoleizhou@gmail.com
 * Desc: Application
 */
@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private JedisTemplate jedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 有返回结果的执行赋值
     * @param key 键
     * @param value 值
     * @return 返回结果
     * @throws Exception 异常
     */
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(String key, String value) throws Exception{
        return jedisTemplate.execute(jedis -> {
            jedis.set(key, value);
            return jedis.get(key);
        });
    }

    /**
     * 有返回结果的获取
     * @param key 键
     * @return 返回结果
     * @throws Exception 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(String key) throws Exception {
        return jedisTemplate.execute(jedis -> {
            return jedis.get(key);
        });
    }

    /**
     * 无返回结果的获取
     * @param key 键
     * @throws Exception 异常
     */
    @RequestMapping(value = "getWithNoResult", method = RequestMethod.GET)
    public void getWithNoResult(String key) throws Exception {
        jedisTemplate.execute(jedis -> {
            jedis.get(key);
        });
    }
}