package com.giulia.giamberini.match.repository;

import java.util.List;

import com.giulia.giamberini.match.model.Player;

public interface PlayerRepository {

	List<Player> findAll();

	Player findById(String id);

	void save(Player player);

}
