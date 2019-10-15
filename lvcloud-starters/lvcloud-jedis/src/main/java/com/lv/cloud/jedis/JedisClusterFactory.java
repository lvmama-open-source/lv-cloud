package com.lv.cloud.jedis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * jedis集群工厂
 * @author xiaoyulin
 *
 */
public class JedisClusterFactory implements FactoryBean<JedisClusterAdapter>, InitializingBean{
	private final static Logger log = LoggerFactory.getLogger(JedisClusterFactory.class);
	
	private JedisClusterAdapter jedisClusterAdapter; 
	/**
	 * redis缓存是否启用
	 */
	private Boolean cacheEnabled;
	/**
	 * redis服务器列表（ip:port）
	 */
	private String  jedisServer;
	/**
	 * 连接超时时间
	 */
	private Integer timeout; 
	/**
	 * 重连次数
	 */
    private Integer maxRedirections;  
    private GenericObjectPoolConfig genericObjectPoolConfig;  
    
    public JedisClusterFactory(){}
    
    public JedisClusterFactory(GenericObjectPoolConfig genericObjectPoolConfig, Boolean cacheEnabled, String jedisServer, Integer timeout, Integer maxRedirections){
    	this.genericObjectPoolConfig = genericObjectPoolConfig;
    	this.cacheEnabled = cacheEnabled;
    	this.jedisServer = jedisServer;
    	this.timeout = timeout;
    	this.maxRedirections = maxRedirections;
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

	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
		return genericObjectPoolConfig;
	}

	public void setGenericObjectPoolConfig(
			GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("JedisClusterFactory.afterPropertiesSet start");
		jedisClusterAdapter = new JedisClusterAdapter();
		jedisClusterAdapter.setCacheEnabled(this.cacheEnabled);
		Set<HostAndPort> jedisClusterNode = this.parseHostAndPort(); 
		if(jedisClusterNode != null){
			jedisClusterAdapter.setJedisCluster(new JedisCluster(jedisClusterNode, timeout, maxRedirections, genericObjectPoolConfig));
		}
	}

	private Set<HostAndPort> parseHostAndPort(){
		log.info("JedisClusterFactory.parseHostAndPort start,serverAdderss="+jedisServer);
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		String[] servers = jedisServer.replaceAll(" ", "").split(",");
		for(int i = 0;i < servers.length;i++){
			String[] ipAndPort = servers[i].split(":");
			jedisClusterNode.add(new HostAndPort(ipAndPort[0], Integer.valueOf(ipAndPort[1])));
		}
		return jedisClusterNode;
	}

	@Override
	public JedisClusterAdapter getObject() throws Exception {
		return jedisClusterAdapter; 
	}

	@Override
	public Class<?> getObjectType() {
		return (this.jedisClusterAdapter != null ? this.jedisClusterAdapter.getClass() : JedisClusterAdapter.class);  
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
