package com.giulia.giamberini.match.controller;

import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.giulia.giamberini.match.model.Match;
import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.MatchRepository;
import com.giulia.giamberini.match.repository.PlayerRepository;
import com.giulia.giamberini.match.view.MatchesView;

public class MatchControllerTest {

	@Mock
	private PlayerRepository playerRepository;
	@Mock
	private MatchRepository matchRepository;

	@Mock
	private MatchesView matchesView;

	@InjectMocks
	private MatchController matchController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		playerRepository.save(null);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllMatches() {
		List<Match> matches = Arrays.asList(new Match());
		when(matchRepository.findAll()).thenReturn(matches);
		matchController.allMatches();
		verify(matchesView).showAllMatches(matches);
	}

	@Test
	public void testNewMatchIsAddedIfNoMatchesAreAlreadyPresentWithThosePlayersInTheSameDate() {
		Player winner = new Player("1", "winner name", "winner surname");
		Player loser = new Player("2", "loser name", "loser surname");
		LocalDate dateOfTheMatch = LocalDate.of(2025, 07, 10);
		Match match = new Match(winner, loser, dateOfTheMatch);
		when(matchRepository.findByMatchInfo(winner, loser, dateOfTheMatch)).thenReturn(null);
		matchController.newMatch(match);
		InOrder inOrder = inOrder(matchRepository, matchesView);
		inOrder.verify(matchRepository).save(match);
		inOrder.verify(matchesView).matchAdded(match);
	}

	@Test
	public void testNewMatchIsNotAddedIfPlayersHaveAlreadyPlayedAgainstEachOtherOnTheSameDate() {
		Player winner = new Player("1", "winner name", "winner surname");
		Player loser = new Player("2", "loser name", "loser surname");
		LocalDate dateOfTheMatch = LocalDate.of(2025, 07, 10);
		Match matchAlreadyPresent = new Match(winner, loser, dateOfTheMatch);
		when(matchRepository.findByMatchInfo(winner, loser, dateOfTheMatch)).thenReturn(matchAlreadyPresent);
		Match newMatchToAdd = new Match(winner, loser, dateOfTheMatch);
		matchController.newMatch(newMatchToAdd);
		verify(matchesView).showError("Those players have already played against each other on the selected date",
				matchAlreadyPresent);
		verifyNoMoreInteractions(ignoreStubs(matchRepository));
	}
	
	@Test
	public void testDeleteAMatchIfItIsPresent() {
		Player winner = new Player("1", "winner name", "winner surname");
		Player loser = new Player("2", "loser name", "loser surname");
		LocalDate dateOfTheMatchToDelete = LocalDate.of(2025, 07, 10);
		Match matchToDelete = new Match(winner, loser, dateOfTheMatchToDelete);
		
		when(matchRepository.findByMatchInfo(winner, loser, dateOfTheMatchToDelete)).thenReturn(matchToDelete);
		matchController.deleteMatch(matchToDelete);
		InOrder inOrder = inOrder(matchRepository, matchesView);
		inOrder.verify(matchRepository).delete(winner, loser, dateOfTheMatchToDelete);
		inOrder.verify(matchesView).matchRemoved(matchToDelete);
	}
	
	@Test
	public void testDeleteANotExistingMatchShowsError() {
		Player winner = new Player("1", "winner name", "winner surname");
		Player loser = new Player("2", "loser name", "loser surname");
		LocalDate dateOfTheMatchToDelete = LocalDate.of(2025, 07, 10);
		Match matchToDelete = new Match(winner, loser, dateOfTheMatchToDelete);
		
		when(matchRepository.findByMatchInfo(winner, loser, dateOfTheMatchToDelete)).thenReturn(null);
		matchController.deleteMatch(matchToDelete);
		verify(matchesView).showError("No existing  match with selected winner, loser and date", matchToDelete);
		verifyNoMoreInteractions(ignoreStubs(matchRepository));
	}


}
