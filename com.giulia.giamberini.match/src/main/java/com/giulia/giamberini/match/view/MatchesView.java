package com.giulia.giamberini.match.view;

import java.util.List;

import com.giulia.giamberini.match.model.Player;

public interface MatchesView {

	void showAllPlayers(List<Player> players);

	void playerAdded(Player player);

	void showError(String string, Player existingPlayer);

}
