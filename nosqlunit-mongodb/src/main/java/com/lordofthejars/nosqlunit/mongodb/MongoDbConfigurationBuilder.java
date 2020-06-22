package com.lordofthejars.nosqlunit.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


public class MongoDbConfigurationBuilder {

	public static MongoDbConfigurationBuilder mongoDb() {
		return new MongoDbConfigurationBuilder();
	}
	
	private final MongoDbConfiguration mongoDbConfiguration;
	
	private MongoDbConfigurationBuilder() {
		mongoDbConfiguration = new MongoDbConfiguration();
	}
	
	public MongoDbConfiguration build() {
		MongoClient mongo = null;
		if(this.mongoDbConfiguration.isAuthenticateParametersSet()) {
			ServerAddress serverAddress = new ServerAddress(this.mongoDbConfiguration.getHost(), this.mongoDbConfiguration.getPort());
			MongoCredential credential = MongoCredential.createCredential(this.mongoDbConfiguration.getUsername(),
					this.mongoDbConfiguration.getDatabaseName(),
					this.mongoDbConfiguration.getPassword().toCharArray());
			MongoClientOptions options = MongoClientOptions.builder().build();
			mongo = new MongoClient(serverAddress, credential, options);
			
		} else {
			mongo = new MongoClient(this.mongoDbConfiguration.getHost(), this.mongoDbConfiguration.getPort());
		}
		this.mongoDbConfiguration.setMongo(mongo);

		return mongoDbConfiguration;
	}
	
	public MongoDbConfigurationBuilder databaseName(String databaseName) {
		mongoDbConfiguration.setDatabaseName(databaseName);
		return this;
	}

	public MongoDbConfigurationBuilder port(int port) {
		mongoDbConfiguration.setPort(port);
		return this;
	}
	
	public MongoDbConfigurationBuilder username(String username) {
		mongoDbConfiguration.setUsername(username);
		return this;
	}
	
	public MongoDbConfigurationBuilder password(String password) {
		mongoDbConfiguration.setPassword(password);
		return this;
	}
	
	public MongoDbConfigurationBuilder host(String host) {
		mongoDbConfiguration.setHost(host);
		return this;
	}
	
	public MongoDbConfigurationBuilder connectionIdentifier(String identifier) {
		mongoDbConfiguration.setConnectionIdentifier(identifier);
		return this;
	}
	
}
