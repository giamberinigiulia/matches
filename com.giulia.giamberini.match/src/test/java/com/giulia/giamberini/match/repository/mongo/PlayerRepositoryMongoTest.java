package com.giulia.giamberini.match.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.giulia.giamberini.match.model.Player;
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

	@Test
	public void testFindAllWhenThereAreElementInTheDaatbase() {
		String testId1 = "1";
		String testName1 = "test name";
		String testSurname1 = "test surname";
		insertPlayerInCollection(testId1, testName1, testSurname1);
		
		String testId2 = "2";
		String testName2 = "another test name";
		String testSurname2 = "another test surname";
		insertPlayerInCollection(testId2, testName2, testSurname2);
		
		assertThat(playerRepository.findAll()).containsExactly(new Player(testId1,testName1,testSurname1), new Player(testId2,testName2,testSurname2));
	}

	private void insertPlayerInCollection(String testId1, String testName1, String testSurname1) {
		playerCollection.insertOne(new Document("_id", testId1).append("name", testName1).append("surname", testSurname1));
	}
	
	@Test
	public void testFindByIdWhenThePlayerDoesntExistInTheDatabase() {
		assertThat(playerRepository.findById("NotExistingID")).isNull();
	}
	
	@Test
	public void testFindByIdWhenThePlayerSearchedIsPresentInTheDatabase() { //test that also if more than one player is saved, it retrieve the correct one
		insertPlayerInCollection("1", "test name", "test surname");
		insertPlayerInCollection("2", "another test name", "another test surname");
		assertThat(playerRepository.findById("2")).isEqualTo(new Player("2", "another test name", "another test surname"));
	}
}
