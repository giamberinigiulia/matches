package com.giulia.giamberini.match.repository;

import java.time.LocalDate;
import java.util.List;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.model.Player;

public interface MatchRepository {

	List<Match> findAll();

	Match findByMatchInfo(Player winner, Player loser, LocalDate dateOfTheMatch);

	void save(Match match);

}
