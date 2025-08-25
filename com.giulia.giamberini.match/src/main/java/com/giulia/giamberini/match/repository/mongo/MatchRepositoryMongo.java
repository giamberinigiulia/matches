package com.giulia.giamberini.match.repository.mongo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.MatchRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class MatchRepositoryMongo implements MatchRepository {

	private MongoCollection<Document> matchCollection;

	public MatchRepositoryMongo(MongoClient client, String dbName, String collectionName) {
		matchCollection = client.getDatabase(dbName).getCollection(collectionName);
	}

	@Override
	public List<Match> findAll() {
		return Collections.emptyList();
	}

	@Override
	public Match findByMatchInfo(Player winner, Player loser, LocalDate dateOfTheMatch) {
		return null;
	}

	@Override
	public void save(Match match) {
	}

	@Override
	public void delete(Player winner, Player loser, LocalDate dateOfTheMatchToDelete) {
	}

}
