package com.zcw.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisDemo1 {

	@Test
	/*
	 * 单例测试
	 */
	public void demo1() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("name", "masterzcw");
		String value = jedis.get("name");
		System.out.println(value);
		jedis.del("name");
		value = jedis.get("name");
		System.out.println(value);
		jedis.close();
	}
	@Test
	public void demo2() {
		// 获得连接池的配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(30);
		// 设置最大空闲连接数
		config.setMaxIdle(10);
		
		// 获得连接池
		JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
		
		// 获得核心对象
		Jedis jedis = null;
		try {
			// 通过连接池获得连接
			jedis = jedisPool.getResource();
			jedis.set("name", "张三疯");
			String value = jedis.get("name");
			System.out.println(value);
			jedis.del("name");
			value = jedis.get("name");
			System.out.println(value);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// 释放资源
			if(jedis != null) {
				jedis.close();
			}
			if(jedisPool != null) {
				jedisPool.close();
			}
		}
	}
}
