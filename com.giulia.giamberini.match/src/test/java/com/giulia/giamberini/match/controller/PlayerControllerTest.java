package com.giulia.giamberini.match.controller;

import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.giulia.giamberini.match.model.Player;
import com.giulia.giamberini.match.repository.PlayerRepository;
import com.giulia.giamberini.match.view.MatchesView;

public class PlayerControllerTest {

	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private MatchesView matchesView;
	
	@InjectMocks
	private PlayerController playerController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllPlayers() {
		List<Player> players = Arrays.asList(new Player());
		when(playerRepository.findAll()).thenReturn(players);
		playerController.allPlayers();
		verify(matchesView).showAllPlayers(players);
	}
	
	@Test
	public void testNewPlayerIsAddedIfItIsNotAlreadyPresent() {
		Player player = new Player("1", "name test","surname test");
		when(playerRepository.findById("1")).thenReturn(null);
		playerController.newPlayer(player);
		InOrder inOrder = inOrder(playerRepository, matchesView);
		inOrder.verify(playerRepository).save(player);
		inOrder.verify(matchesView).playerAdded(player);
	}
	
	@Test
	public void testNewPlayerIsNotAddedIfItIsAlreadyPresent() {
		Player existingPlayer = new Player("1", "name test","surname test");
		Player newPlayertoAdd = new Player("1", "new name","new surname");
		when(playerRepository.findById("1")).thenReturn(existingPlayer);
		playerController.newPlayer(newPlayertoAdd);
		verify(matchesView).showError("Player already present with id 1", existingPlayer);
		verifyNoMoreInteractions(ignoreStubs(playerRepository));
	}

}
