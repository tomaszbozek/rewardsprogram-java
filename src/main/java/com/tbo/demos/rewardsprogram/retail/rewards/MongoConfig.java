package com.tbo.demos.rewardsprogram.retail.rewards;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration responsible for assuring transactionality in our application via the usage of Spring @Transactional.
 * This won't work with embedded mongo db without special config and version of embedded mongo >= 4.4.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tbo.demos.rewardsprogram.retail.rewards.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
			.applyConnectionString(connectionString)
			.build();
		return MongoClients.create(mongoClientSettings);
	}
}
