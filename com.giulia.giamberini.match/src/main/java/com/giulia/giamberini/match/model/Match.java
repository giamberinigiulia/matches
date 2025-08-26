package com.giulia.giamberini.match.model;

import java.time.LocalDate;
import java.util.Objects;

public class Match {
	private Player winner;
	private Player loser;
	private LocalDate dateOfTheMatch;

	public Match(Player winenr, Player loser, LocalDate dateOfTheMatch) {
		this.winner = winenr;
		this.loser = loser;
		this.dateOfTheMatch = dateOfTheMatch;
	}

	public Match() {
	}

	public Player getWinner() {
		return winner;
	}

	public Player getLoser() {
		return loser;
	}

	public LocalDate getDateOfTheMatch() {
		return dateOfTheMatch;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfTheMatch, loser, winner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		return Objects.equals(dateOfTheMatch, other.dateOfTheMatch) && Objects.equals(loser, other.loser)
				&& Objects.equals(winner, other.winner);
	}

	@Override
	public String toString() {
		return "Match [winner=" + winner + ", loser=" + loser + ", dateOfTheMatch=" + dateOfTheMatch + "]";
	}
}
