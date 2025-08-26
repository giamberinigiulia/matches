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
		Match existingMatchAsGiven = matchRepository.findByMatchInfo(match.getWinner(), match.getLoser(),
				match.getDateOfTheMatch());
		if (existingMatchAsGiven != null) {
			matchesView.showError("Those players have already played against each other on the selected date",
					existingMatchAsGiven);
			return;
		}
		Match existingMatchAsOppositeResult = matchRepository.findByMatchInfo(match.getLoser(), match.getWinner(), match.getDateOfTheMatch());
		if (existingMatchAsOppositeResult != null) {
			matchesView.showError("Those players have already played against each other on the selected date",
					existingMatchAsOppositeResult);
			return;
		}
		matchRepository.save(match);
		matchesView.matchAdded(match);
	}

	public void deleteMatch(Match matchToDelete) {
		if (matchRepository.findByMatchInfo(matchToDelete.getWinner(), matchToDelete.getLoser(), matchToDelete.getDateOfTheMatch()) == null) {
			matchesView.showError("No existing  match with selected winner, loser and date", matchToDelete);
			return;
		}
		matchRepository.delete(matchToDelete.getWinner(), matchToDelete.getLoser(), matchToDelete.getDateOfTheMatch());
		matchesView.matchRemoved(matchToDelete);
	}

}
