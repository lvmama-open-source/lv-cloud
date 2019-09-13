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
    
    