package com.giulia.giamberini.match.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.giulia.giamberini.match.repository.PlayerRepositoryMongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PlayerRepositoryMongoTest {
	@ClassRule
	public static MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private MongoClient mongoClient;
	private PlayerRepositoryMongo playerRepository;
	private MongoCollection<Document> playerCollection;

	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		playerRepository = new PlayerRepositoryMongo(mongoClient, "tennis_matches", "players");
		MongoDatabase database = mongoClient.getDatabase("tennis_matches");
		database.drop();
		playerCollection = database.getCollection("players");
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}
	
	@Test
	public void testFindAllWithNoElementInTheDatabase() {
		assertThat(playerRepository.findAll()).isEmpty();
	}

}
