
### spring-boot-starter-redis

#### 一. 依赖
```
    <groupId>star.xiaolei</groupId>
    <artifactId>starter-redis</artifactId>
    <version>1.0-SNAPSHOT</version>
```

#### 二. 配置(application.yml)
```
redis:
  host: 127.0.0.1
  port: 6379
  maxTotal: 5
  maxIdle: 0
  maxWaitMillis: 10000
  testOnBorrow: true
```

### 三. 连接获取
通过star.xiaolei.client.JedisTemplate获取Redis连接