package com.giulia.giamberini.match.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MatchRepositoryMongoTest {

	@ClassRule
	public static MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private MongoClient mongoClient;
	private MatchRepositoryMongo matchRepository;
	private MongoCollection<Document> matchCollection;
	
	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		matchRepository = new MatchRepositoryMongo(mongoClient, "tennis_matches", "matches");
		MongoDatabase database = mongoClient.getDatabase("tennis_matches");
		database.drop();
		matchCollection = database.getCollection("matches");
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	
	@Test
	public void testFindAllWithNoElementInTheDatabase() {
		assertThat(matchRepository.findAll()).isEmpty();
	}
	
	

}
