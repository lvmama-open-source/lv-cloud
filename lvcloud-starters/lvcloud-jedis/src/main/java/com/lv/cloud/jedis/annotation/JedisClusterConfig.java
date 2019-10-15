package com.lv.cloud.jedis.annotation;

import com.lv.cloud.jedis.JedisClusterAdapter;
import com.lv.cloud.jedis.JedisClusterFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * JedisCluster 配置
 * @author xiaoyulin
 */
@ConditionalOnProperty(prefix="lvcloud.jedis",
		name = {"enable"},
		havingValue = "true")
@Configuration
public class JedisClusterConfig {
	
	private final static Log log = LogFactory.getLog(JedisClusterAdapter.class);
	
    /**
	 * redis缓存是否启用
	 */
    @Value("${lvcloud.jedis.enable}")
	private Boolean cacheEnabled;
	/**
	 * redis服务器列表（ip:port）
	 */
    @Value("${lvcloud.jedis.server}")
	private String jedisServer;
	/**
	 * 连接超时时间
	 */
    @Value("${lvcloud.jedis.timeout}")
	private Integer timeout; 
	/**
	 * 重连次数
	 */
    @Value("${lvcloud.jedis.maxRedirections}")
    private Integer maxRedirections;  
    
    @Value("${lvcloud.jedis.maxWaitMillis}")
    private Integer maxWaitMillis; 
 
    @Value("${lvcloud.jedis.maxTotal}")
    private Integer maxTotal; 
    
    @Value("${lvcloud.jedis.minIdle}")
    private Integer minIdle; 
    
    @Value("${lvcloud.jedis.maxIdle}")
    private Integer maxIdle;
    
    @Override
	public String toString() {
		return "JedisClusterConfig [cacheEnabled=" + cacheEnabled + ", jedisServer=" + jedisServer + ", timeout="
				+ timeout + ", maxRedirections=" + maxRedirections + ", maxWaitMillis=" + maxWaitMillis + ", maxTotal="
				+ maxTotal + ", minIdle=" + minIdle + ", maxIdle=" + maxIdle + "]";
	}

	@Bean
    public JedisClusterFactory getJedisClusterFactory(){
		log.info("JedisClusterConfig:" + this.toString());
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxIdle(maxIdle);
		JedisClusterFactory jedisClusterFactory = new JedisClusterFactory(config, cacheEnabled, jedisServer, timeout, maxRedirections);
        return jedisClusterFactory;
    }

    public Boolean getCacheEnabled() {
		return cacheEnabled;
	}

	public void setCacheEnabled(Boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	public String getJedisServer() {
		return jedisServer;
	}

	public void setJedisServer(String jedisServer) {
		this.jedisServer = jedisServer;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

}
