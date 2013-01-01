package com.lordofthejars.nosqlunit.couchdb;

import java.io.InputStream;

import com.lordofthejars.nosqlunit.core.NoSqlAssertionError;

public class DefaultCouchDbComparisionStrategy implements CouchDbComparisionStrategy {

	@Override
	public boolean compare(CouchDbConnectionCallback connection, InputStream dataset) throws NoSqlAssertionError, Throwable {
		CouchDbAssertion.strictAssertEquals(dataset, connection.couchDbConnector());
		return true;
	}

}
