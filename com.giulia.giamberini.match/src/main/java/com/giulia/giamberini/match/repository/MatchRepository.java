package com.giulia.giamberini.match.repository;

import java.util.List;

import com.giulia.giamberini.match.model.Match;

public interface MatchRepository {

	List<Match> findAll();

}
