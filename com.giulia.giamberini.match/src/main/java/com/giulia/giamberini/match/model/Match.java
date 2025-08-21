package com.giulia.giamberini.match.model;

import java.time.LocalDate;
import java.util.Objects;

public class Match {
	private Player winenr;
	private Player loser;
	private LocalDate dateOfTheMatch;

	public Match(Player winenr, Player loser, LocalDate dateOfTheMatch) {
		this.winenr = winenr;
		this.loser = loser;
		this.dateOfTheMatch = dateOfTheMatch;
	}

	public Player getWinenr() {
		return winenr;
	}

	public Player getLoser() {
		return loser;
	}

	public LocalDate getDateOfTheMatch() {
		return dateOfTheMatch;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfTheMatch, loser, winenr);
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
				&& Objects.equals(winenr, other.winenr);
	}

	@Override
	public String toString() {
		return "Match [winenr=" + winenr + ", loser=" + loser + ", dateOfTheMatch=" + dateOfTheMatch + "]";
	}
}
