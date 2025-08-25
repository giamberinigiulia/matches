package com.giulia.giamberini.match.controller;

import javax.swing.text.View;

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

}
