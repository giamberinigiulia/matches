package com.giulia.giamberini.match.repository;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
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

}
