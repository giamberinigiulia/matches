package com.giulia.giamberini.match.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.model.Player;
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
	private MongoCollection<Document> playerCollection;

	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		matchRepository = new MatchRepositoryMongo(mongoClient, "tennis_matches", "matches", "players");
		MongoDatabase database = mongoClient.getDatabase("tennis_matches");
		database.drop();
		matchCollection = database.getCollection("matches");
		playerCollection = database.getCollection("players");
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Test
	public void testFindAllWithNoElementInTheDatabase() {
		assertThat(matchRepository.findAll()).isEmpty();
	}

	@Test
	public void testFindAllWhenThereAreElementsInTheDatabase() {
		Player winner = new Player("1", "winner name", "winner surname");
		Player loser = new Player("2", "loser name", "loser surname");
		insertPlayerInCollection(winner);
		insertPlayerInCollection(loser);

		LocalDate firstMatchDate = LocalDate.of(2025, 07, 10);
		LocalDate secondMatchDate = LocalDate.of(2025, 07, 15);

		insertMatchInCollection(winner, loser, firstMatchDate);
		insertMatchInCollection(winner, loser, secondMatchDate);

		assertThat(matchRepository.findAll()).containsExactly(new Match(winner, loser, firstMatchDate),
				new Match(winner, loser, secondMatchDate));
	}

	private void insertPlayerInCollection(Player player) {
		playerCollection.insertOne(new Document("_id", player.getId()).append("name", player.getName())
				.append("surname", player.getSurname()));
	}

	private void insertMatchInCollection(Player winner, Player loser, LocalDate matchDate) {
		matchCollection.insertOne(
				new Document("winnerId", winner.getId()).append("loserId", loser.getId()).append("date", matchDate));
	}

}
