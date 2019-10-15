package com.lv.cloud.jedis;

import com.lv.cloud.utils.HessionSerializeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;


/**
 * redis集群工具
 * @author xiaoyulin
 *
 */
public class JedisClusterAdapter {

	private final static Logger log = LoggerFactory.getLogger(JedisClusterAdapter.class);
	
	// Distribution lock
	private static final int DEFAULT_LOCK_SECCONDS = 5;// 默认锁有效时间
	private static final int DEFAULT_TRYLOCK_TIMEOUT_SECONDS = 10;// 线程默认等待时间
	private static final String OK_CODE = "OK";// 返回成功
	private static final String OK_MULTI_CODE = "+OK";// 事务返回成功
	
	private JedisCluster jedisCluster;
	/**
	 * redis缓存是否启用
	 */
	private Boolean cacheEnabled;
	
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	} 

	public void setCacheEnabled(Boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}
	
	private boolean isCacheEnabled() {
		return this.cacheEnabled;
	}
	
	/**
	 * 放
	 * @param key
	 * @param seconds 过期秒数
	 * @param value
	 * @return
	 */
	public boolean set(String key, int seconds, String value) {
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
		
		try{
			String result = getJedisCluster().setex(key, seconds, value);
			if(log.isDebugEnabled()) {
				log.debug("SET A OBJECT: KEY:" + key + ", seconds:" + seconds + ", result:" + result);
			}
			if(StringUtils.isEmpty(result)){
				return false;
			}
			return true;
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return false;
	}
	
	/**
	 * 放(持久化)
	 * key存在时，不做任何操作
	 * @param key
	 * @param value
	 * @return
	 * @author ltwangwei
	 * @date 2016-6-28 下午7:48:21
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean setnx(String key, String value) {
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		
		try{
			Long result = getJedisCluster().setnx(key, value);
			if(log.isDebugEnabled()) {
				log.debug("SET A OBJECT: KEY:" + key + ", result:" + result);
			}
			if (result != 1L) {
	            if (log.isInfoEnabled()) log.info("setnx error : " + key);
	            return false;
	        }
			return true;
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return false;
	}
	
	/**
	 * 覆盖(持久化)
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
	 * 当 key 存在但不是字符串类型时，返回一个错误
	 * key 不存在时，返回 null
	 * @param key
	 * @param value
	 * @return
	 * @author ltwangwei
	 * @date 2016-7-6 下午2:25:49
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean getSet(String key, String value){
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
		
		try{
			String result = getJedisCluster().getSet(key, value);
			if(log.isDebugEnabled()) {
				log.debug("SET A OBJECT: KEY:" + key + ", result:" + result);
			}
			if(StringUtils.isEmpty(result)){
				return false;
			}
			return true;
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return false;
	}

	/**
	 * 判断key是否存在对应的值
	 * @param key
	 * @return
	 */
	public boolean keyExists(String key){
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		
		try {
			String obj = getJedisCluster().get(key);
			if(log.isDebugEnabled()) {
				log.debug("GET A OBJECT: KEY:" + key ) ;
			}
			if(obj != null){
				return true;
			}
		}catch (Exception e){
			log.error("Exception:{}", e);
		}
		return  false;
	}
	
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * @param key
	 * @return
	 * @author ltwangwei
	 * @date 2016-7-6 下午2:25:49
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public long ttl(String key){
		long result = -2;
		if (!isCacheEnabled()) {
			return result;
		}
		
		assert (key != null) : "key is null";
		
		try{
			result = getJedisCluster().ttl(key);
			if(log.isDebugEnabled()) {
				log.debug("SET A OBJECT: KEY:" + key + ", result:" + result);
			}
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return result;
	}
	
	/**
	 * 取
	 * @param key
	 * @return
	 */
	public String get(String key) {
		if (!isCacheEnabled()) {
			return null;
		}
		
		assert (key != null) : "key is null";
		
		try{
			String obj = getJedisCluster().get(key);
			if(log.isDebugEnabled()) {
				log.debug("GET A OBJECT: KEY:" + key ) ;
			}
			return obj;
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return null;
	}
	/**
	 * 删除缓存
	 * @param key
	 */
	public boolean del(String key) {
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		
		try{
			if(log.isDebugEnabled()) {
				log.debug("DEL A OBJECT: KEY:" + key ) ;
			}
			Long isOk = getJedisCluster().del(key);
			if(isOk == 1L){
				return true;
			}
			else{
				if(log.isDebugEnabled()) {
					log.debug("DEL A OBJECT ERROR: KEY:" + key ) ;
				}
			}
		}catch(Exception e) {
			log.error("Exception:{}", e);
		}
		return false;
	}
	
	/**
	 * 获取具体类型的值
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String key, Class<E> clazz){
		if (!isCacheEnabled()) {
            return null;
        }

		assert (key != null) : "key is null";
		assert (clazz != null) : "clazz is null";
		
        byte[] obj = getJedisCluster().get(key.getBytes());
        if(obj == null){
        	return null;
        }
        return (E) HessionSerializeUtil.deserialize(obj);
	} 

	/**
	 * 存非String类型value
	 * @param key
	 * @param seconds 过期秒数
	 * @param value
	 * @return
	 */
	public <E> boolean set(String key, int seconds, E value){
		if (!isCacheEnabled()) {
			return false;
		}
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
        assert (seconds > 0) : "seconds less than 0";
        
        String isOk = getJedisCluster().setex(key.getBytes(), seconds, HessionSerializeUtil.serialize(value));
        if (!"OK".equals(isOk)) {
            if (log.isInfoEnabled()) log.info("set error : " + key);
            return false;
        }
        return true;
		
	}
	
	/**
	 * 存非String类型value(持久化)
	 * @param key
	 * @param value
	 * @return
	 */
	public <E> boolean setnx(String key, E value){
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
		
		Long isOk = getJedisCluster().setnx(key.getBytes(), HessionSerializeUtil.serialize(value));
        if (isOk != 1L) {
            if (log.isInfoEnabled()) log.info("setnx error : " + key);
            return false;
        }
		return true;
	}
	
	/**
	 * 存String类型的值，综合setNX与setEx的效果。
	 * @param key
	 * @param value
	 * @param seconds 过期秒数
	 * @return
	 * @author yangjiabin
	 * @date 2018年8月28日 下午5:13:22
	 */
	public Boolean setnxex(final String key, final String value, final int seconds) {
		if (!isCacheEnabled()) {
			return false;
		}
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
        assert (seconds > 0) : "seconds less than 0";
		// NX – 只有键key不存在的时候才会设置key的值，EX seconds – 设置键key的过期时间，单位时秒
        String result = getJedisCluster().set(key, value, "NX", "EX", seconds);
        if (OK_CODE.equals(result) || OK_MULTI_CODE.equals(result)) {
        	return true;
        } else {
        	return false;
        }
	}
	
	/**
	 * 覆盖(持久化)
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
	 * 当 key 存在但不是字符串类型时，返回一个错误
	 * key 不存在时，返回 null
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E getSet(String key, E value){
		if (!isCacheEnabled()) {
			return null;
		}
		
		assert (key != null) : "key is null";
		assert (value != null) : "value is null";
		
		byte[] rs = getJedisCluster().getSet(key.getBytes(), HessionSerializeUtil.serialize(value));
        if (rs == null) {
            if (log.isInfoEnabled()) log.info("getSet error : " + key);
            return null;
        }
		return (E) HessionSerializeUtil.deserialize(rs);
	}
	
	/**
	 * 哈希表存值hset key field value<br/>
	 * 存字符串
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean hset(String key, String field, String value){
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		assert (field != null) : "field is null";
		assert (value != null) : "value is null";
		
		Long code = getJedisCluster().hset(key, field, value);
		if(code != null && code == 1){
			return true;
		}
		if (log.isInfoEnabled()) log.info("hset error : " + key);
		return false;
	}
	
	/**
	 * 哈希表存值hset key field value<br/>
	 * 存对象类型
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public <E> boolean hset(String key, String field, E value){
		if (!isCacheEnabled()) {
			return false;
		}
		
		assert (key != null) : "key is null";
		assert (field != null) : "field is null";
		assert (value != null) : "value is null";
		
		Long code = getJedisCluster().hset(key.getBytes(), field.getBytes(), HessionSerializeUtil.serialize(value));
		if(code != null && code == 1){
			return true;
		}
		if (log.isInfoEnabled()) log.info("hset error : " + key);
		return false;
	}
	
	/**
	 * 哈希表取值hget key field<br/>
	 * 取对象类型
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E hget(String key, String field, Class<E> clazz){
		if (!isCacheEnabled()) {
            return null;
        }

		assert (key != null) : "key is null";
		assert (key != null) : "field is null";
		assert (clazz != null) : "clazz is null";
		
		byte[] obj = getJedisCluster().hget(key.getBytes(), field.getBytes());
        if(obj == null){
        	return null;
        }
        return (E) HessionSerializeUtil.deserialize(obj);
		
	}
	
	/**
	 * 哈希表取值hget key field<br/>
	 * 取字符串
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field){
		if (!isCacheEnabled()) {
            return null;
        }

		assert (key != null) : "key is null";
		assert (key != null) : "field is null";
		
		return getJedisCluster().hget(key, field);
	}

	/**
	 * 设置key过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public boolean expire(final String key, final int seconds){
		if (!isCacheEnabled()) {
			return false;
		}

		assert (key != null) : "key is null";
		assert (seconds > 0) : "seconds less than 0";

		Long isOk = getJedisCluster().expire(key, seconds);

		if (isOk != 1L) {
			if (log.isInfoEnabled()) log.info("setnx error : " + key);
			return false;
		}
		return true;
	}

	public boolean achieveLock(String key,Long KEEP_TIME,int EXPIRE_TIME) {
		long value = System.currentTimeMillis() + KEEP_TIME;
		boolean acquired = setnx(key, String.valueOf(value));
		// 返回1表示获取到锁，返回0表示获取失败
		if (acquired) {
			expire(key, EXPIRE_TIME);
			return true;
		} else {
			long oldValue = 0;
			String oldValueStr = get(key);
			// 防止在这之前有其他线程获取到锁并且完成操作后删除了锁
			if (oldValueStr != null) {
				oldValue = Long.valueOf(oldValueStr);
			}
			// 超时
			if (oldValue < System.currentTimeMillis()) {
				String getValue = getJedisCluster().getSet(key, String.valueOf(value));
				if (getValue == null) {
					getValue = "0";
				}
				// 获取锁成功
				if (Long.valueOf(getValue) == oldValue) {
					return true;
				} else {// 已被其他进程捷足先登了
					return false;
				}
			} else { // 未超时，则直接返回失败
				return false;
			}
		}
	}
	
	/**
	 * Redis分布式锁，缓存值、失效时间、等待时间使用默认配置，方法返回值是一个uuid，
	 * 客户端根据返回值调用releaseLock方法释放锁，如果返回的是null表示没有加锁成功
	 * @param lockKey
	 * @return
	 * @author yangjiabin
	 * @date 2018年8月28日 下午6:07:48
	 */
	public String tryLock(String lockKey){
		// 生成UUID作为value
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		boolean lockFlag = tryLock(lockKey, uuid, DEFAULT_LOCK_SECCONDS, DEFAULT_TRYLOCK_TIMEOUT_SECONDS);
    	return lockFlag ? uuid : null;
    }
	
	/**
	 * Redis分布式锁 
	 * @param lockKey
	 * @param lockValue 为了便于加锁客户端取消自己的锁，建议这里使用UUID
	 * @param lockSec value有效时间
	 * @param timeOutSec 线程等待最大时间
	 * @return
	 * @author yangjiabin
	 * @date 2018年8月28日 下午5:28:13
	 */
	public boolean tryLock(String lockKey, String lockValue, int lockSec, int timeOutSec) {
        if (jedisCluster == null) {
            return false;
        }
        // 当前时间毫秒值
        long start = System.currentTimeMillis();
        while (true) {
        	// 获取锁
            boolean locked = this.setnxex(lockKey, lockValue, lockSec);
            if (locked) {
                return true;
            } else {
            	// 当前时间的毫秒值
                long now = System.currentTimeMillis();
                // 当前线程等待的时间
                long costed = now - start;
                // 如果等待时间超过过期时间，则返回false
                if (costed >= timeOutSec * 1000) {
                	log.info("RedisLock tryLock :" + costed + ", LockKey: " + lockKey + ", LockValue: " + lockValue);
                    return false;
                }
            }
        }
	}
	
	/**
	 * 线程主动释放锁，而不是等到锁失效 
	 * @param lockKey
	 * @param lockValue
	 * @author yangjiabin
	 * @date 2018年8月28日 下午5:55:28
	 */
	public void releaseLock(String lockKey, String lockValue) {
		if (lockKey == null || lockValue == null) {
			return;
		}
		// 判断加锁与解锁是不是同一个客户端
	    if (lockValue.equals(this.get(lockKey))) {
	        // 若在此时，这把锁突然不是这个客户端的，则会误解锁
	        this.del(lockKey);
	    }
	}
	
	/**
	 * 判断给定键值是否在redis缓存当中
	 * @param key
	 * @return
	 */
	public boolean exists(final String key){
		return getJedisCluster().exists(key);
	}
	
}
