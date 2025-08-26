package com.giulia.giamberini.match.repository.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.MatchRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class MatchRepositoryMongo implements MatchRepository {

	private MongoCollection<Document> matchCollection;
	private MongoCollection<Document> playerCollection;

	public MatchRepositoryMongo(MongoClient client, String dbName, String matchCollectionName,
			String playerCollectionName) {
		matchCollection = client.getDatabase(dbName).getCollection(matchCollectionName);
		playerCollection = client.getDatabase(dbName).getCollection(playerCollectionName);
	}

	@Override
	public List<Match> findAll() {
		return StreamSupport.stream(matchCollection.find().spliterator(), false).map(this::fromDocumentToMatch)
				.collect(Collectors.toList());
	}

	@Override
	public Match findByMatchInfo(Player winner, Player loser, LocalDate dateOfTheMatch) {
		Document existingMatchDoc = matchCollection.find(and(eq("winnerId", winner.getId()), 
															 eq("loserId", loser.getId()), 
															 eq("date", dateOfTheMatch)))
													.first();

		if (existingMatchDoc != null) {
			return fromDocumentToMatch(existingMatchDoc);
		}
		return null;

	}

	@Override
	public void save(Match match) {
	}

	@Override
	public void delete(Player winner, Player loser, LocalDate dateOfTheMatchToDelete) {
	}

	private Match fromDocumentToMatch(Document d) {
		String winnerId = d.getString("winnerId");
		String loserId = d.getString("loserId");

		// find relative winner and loser players
		Document winnerDoc = playerCollection.find(eq("_id", winnerId)).first();
		Document loserDoc = playerCollection.find(eq("_id", loserId)).first();

		Player winner = fromDocumentToPlayer(winnerDoc);
		Player loser = fromDocumentToPlayer(loserDoc);

		LocalDate date = d.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return new Match(winner, loser, date);
	}

	private Player fromDocumentToPlayer(Document d) {
		return new Player(d.getString("_id"), d.getString("name"), d.getString("surname"));
	}

}
