package com.giulia.giamberini.match.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.giulia.giamberini.match.model.Player;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class PlayerRepositoryMongo implements PlayerRepository {

	private MongoCollection<Document> playerCollection;

	public PlayerRepositoryMongo(MongoClient client, String dbName, String collectionName) {
		playerCollection = client.getDatabase(dbName).getCollection(collectionName);
	}

	@Override
	public List<Player> findAll() {
		return StreamSupport.stream(playerCollection.find().spliterator(), false)
				.map(this::convertFromDocumentToPlayer)
				.collect(Collectors.toList());
	}

	@Override
	public Player findById(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Player player) {
		// TODO Auto-generated method stub

	}

	private Player convertFromDocumentToPlayer(Document d) {
		return new Player(d.getString("_id"), d.getString("name"), d.getString("surname"));

	}
}
