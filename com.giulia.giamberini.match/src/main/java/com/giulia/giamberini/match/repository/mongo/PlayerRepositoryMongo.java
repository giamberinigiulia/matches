package com.giulia.giamberini.match.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.PlayerRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

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
	public Player findById(String id) {
		Document retrievedPlayer = playerCollection.find(Filters.eq("_id",id)).first();
		if (retrievedPlayer != null) {
			return convertFromDocumentToPlayer(retrievedPlayer);
		}
		return null;
	}

	@Override
	public void save(Player player) {
		playerCollection.insertOne(new Document("_id", player.getId()).append("name", player.getName()).append("surname", player.getSurname()));
	}

	private Player convertFromDocumentToPlayer(Document d) {
		return new Player(d.getString("_id"), d.getString("name"), d.getString("surname"));

	}
}
