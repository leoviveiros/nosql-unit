package com.lordofthejars.nosqlunit.infinispan;

import java.io.InputStream;

import org.infinispan.api.BasicCache;

import com.lordofthejars.nosqlunit.core.AbstractCustomizableDatabaseOperation;
import com.lordofthejars.nosqlunit.core.NoSqlAssertionError;

public class InfinispanOperation extends AbstractCustomizableDatabaseOperation<InfinispanConnectionCallback, BasicCache<Object, Object>> { 

	private BasicCache<Object, Object> cache;
	
	
	public InfinispanOperation(BasicCache<Object, Object> cache) {
		this.cache = cache;
		setInsertationStrategy(new DefaultInfinispanInsertationStrategy());
		setComparisionStrategy(new DefaultInfinispanComparisionStrategy());
	}
	
	@Override
	public void insert(InputStream dataScript) {
		insertData(dataScript);
	}

	private void insertData(InputStream dataScript) {
		try {
			executeInsertation(new InfinispanConnectionCallback() {
				
				@Override
				public BasicCache<Object, Object> basicCache() {
					return cache;
				}
			}, dataScript);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void deleteAll() {
		this.cache.clear();
	}

	@Override
	public boolean databaseIs(InputStream expectedData) {
		return compareData(expectedData);
	}

	private boolean compareData(InputStream expectedData) throws NoSqlAssertionError {
		try {
			return executeComparision(new InfinispanConnectionCallback() {
				
				@Override
				public BasicCache<Object, Object> basicCache() {
					return cache;
				}
			}, expectedData);
		} catch (NoSqlAssertionError e) {
			throw e;
		} catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public BasicCache connectionManager() {
		return this.cache;
	}

}
