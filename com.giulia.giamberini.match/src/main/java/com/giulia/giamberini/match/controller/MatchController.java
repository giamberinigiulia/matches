package com.giulia.giamberini.match.controller;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.repository.MatchRepository;
import com.giulia.giamberini.match.view.MatchesView;

public class MatchController {

	private MatchRepository matchRepository;
	private MatchesView matchesView;

	public MatchController(MatchRepository matchRepository, MatchesView matchesView) {
		this.matchRepository = matchRepository;
		this.matchesView = matchesView;
	}

	public void allMatches() {
		matchesView.showAllMatches(matchRepository.findAll());
	}

	public void newMatch(Match match) {
		// TODO Auto-generated method stub
		
	}

}
