package star.xiaolei.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 周高磊
 * Date: 2017/5/2.
 * Email: gaoleizhou@gmail.com
 * Desc: 属性信息
 */
@Data
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    //host
    private String host;

    //端口号,默认6379
    private int port = 6379;

    //认证
    private String auth;

    //最大连接数
    private int maxTotal = 5;

    //最大空闲连接
    private int maxIdle = 0;

    //获取连接时最大等待毫秒数
    private int maxWaitMillis = 10000;

    //在获取连接的时候是否检查有效性
    private boolean testOnBorrow = true;

    //是否开启集群模式,默认关闭
    private boolean cluster = false;

    //Sentinel Instance 列表
    private String SentinelHosts;

    //Sentinel Master Name
    private String SentinelMasterName;

}
