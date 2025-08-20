package com.giulia.giamberini.match.controller;

import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.PlayerRepository;
import com.giulia.giamberini.match.view.MatchesView;

public class PlayerController {

	private PlayerRepository playerRepository;
	private MatchesView matchesView;

	public PlayerController(PlayerRepository playerRepository, MatchesView matchesView) {
		this.playerRepository = playerRepository;
		this.matchesView = matchesView;
	}

	public void allPlayers() {
		matchesView.showAllPlayers(playerRepository.findAll());
	}

	public void newPlayer(Player player) {
		Player existingPlayer = playerRepository.findById(player.getId());
		if (existingPlayer != null) {
			matchesView.showError("Player already present with id " + existingPlayer.getId(), existingPlayer);
			return;
		}
		playerRepository.save(player);
		matchesView.playerAdded(player);
	}

}
