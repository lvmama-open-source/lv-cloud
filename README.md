# lv-cloud(微服务架构集成)

   ## module介绍：
   
   1、lvcloud-commons:微服务基础框架、常用辅助工具（日志、深度bean copy、eventbus、其他辅助utils等）
   2、lvcloud-starters:spring集成jedis、druid、mybatis、sharding-jdbc、activiti、mongoDB、dubbo等开箱组件。
     
   ### 组件引用说明：
      
   #### 1、lvcloud-jedis（Spring集成redis-cluster）：
   
    1）引入配置：
    ###redis###
    lvcloud.jedis.enable=true
    lvcloud.jedis.server=10.200.4.76:6379,10.200.4.75:6379,10.200.4.74:6379
    lvcloud.jedis.timeout=2000
    lvcloud.jedis.maxRedirections=6
    
    lvcloud.jedis.maxWaitMillis=-1
    lvcloud.jedis.maxTotal=1000
    lvcloud.jedis.minIdle=8
    lvcloud.jedis.maxIdle=100
    
    2）加注解：@EnableJedisCluster
    
   ### 2、lvcloud-druid-mybatis
    
    1)引入配置：
    spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.datasource.url=jdbc:mysql://ip:3306/xxx_db?useSSL=false&characterEncoding=utf8
    spring.datasource.username=root
    spring.datasource.password=kaifa
    spring.datasource.druid.initial-size=10
    spring.datasource.druid.min-idle=10
    spring.datasource.druid.maxActive=100
    spring.datasource.druid.maxWait=60000
    spring.datasource.druid.timeBetweenEvictionRunsMillis=60000
    spring.datasource.druid.minEvictableIdleTimeMillis=300000
    spring.datasource.druid.validationQuery=SELECT 1
    spring.datasource.druid.testWhileIdle=true
    spring.datasource.druid.testOnBorrow=false
    spring.datasource.druid.testOnReturn=false
    spring.datasource.druid.poolPreparedStatements=true
    spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize=20
    #spring.datasource.druid.filters=stat,wall,log4j
    #spring.datasource.druid.connectionProperties=druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
    #spring.datasource.druid.web-stat-filter.enabled=true
    
    #配置.xml文件路径
    mybatis.config-location=classpath:sqlmap-config-mysql.xml
    mybatis.mapper-locations=classpath*:com/xxx/dao/map/*.xml
    #配置模型路径
    mybatis.type-aliases-package=com.xxx.po
    
   
