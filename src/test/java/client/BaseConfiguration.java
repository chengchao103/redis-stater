package client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 周高磊
 * Date: 2017/5/3.
 * Email: gaoleizhou@gmail.com
 * Desc:
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"star.xiaolei.autoconfigure"})
public class BaseConfiguration {
}
