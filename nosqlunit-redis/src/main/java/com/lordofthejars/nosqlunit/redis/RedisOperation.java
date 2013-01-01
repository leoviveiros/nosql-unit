package com.lordofthejars.nosqlunit.redis;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;

import com.lordofthejars.nosqlunit.core.AbstractCustomizableDatabaseOperation;
import com.lordofthejars.nosqlunit.core.NoSqlAssertionError;

public class RedisOperation extends AbstractCustomizableDatabaseOperation<RedisConnectionCallback, Jedis> {

	private Jedis jedis;
	
	public RedisOperation(Jedis jedis) {
		this.jedis = jedis;
		setInsertationStrategy(new DefaultRedisInsertationStrategy());
		setComparisionStrategy(new DefaultRedisComparisionStrategy());
	}
	
	
	@Override
	public void insert(InputStream dataScript) {
		insertData(dataScript);
	}


	private void insertData(InputStream dataScript) {
		try {
			executeInsertation(new RedisConnectionCallback() {
				
				@Override
				public List<Jedis> getAllJedis() {
					return Arrays.asList(jedis);
				}
				
				@Override
				public Jedis getActiveJedis(byte[] key) {
					return jedis;
				}

				@Override
				public BinaryJedisCommands insertationJedis() {
					return jedis;
				}
			}, dataScript);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void deleteAll() {
		this.jedis.flushAll();
	}

	@Override
	public boolean databaseIs(InputStream expectedData) {
		return compareData(expectedData);
	}


	private boolean compareData(InputStream expectedData) throws NoSqlAssertionError {
		try {
			return executeComparision(new RedisConnectionCallback() {
					
					@Override
					public List<Jedis> getAllJedis() {
						return Arrays.asList(jedis);
					}
					
					@Override
					public Jedis getActiveJedis(byte[] key) {
						return jedis;
					}

					@Override
					public BinaryJedisCommands insertationJedis() {
						return jedis;
					}
				}, expectedData);
		} catch (NoSqlAssertionError e) {
			throw e;
		} catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Jedis connectionManager() {
		return jedis;
	}

}
