package com.giulia.giamberini.match.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.giulia.giamberini.match.model.Match;
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

}
