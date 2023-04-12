package no.hvl.dat107.entity;

import java.io.Serializable;

/**
 * Dette er en klasse som brukes for å lage en kombinert nøkkel for JPA
 */
public class ProsjektdeltagelsePK implements Serializable { // intellij krever at denne må implementere Serializable

	private int ansatt;
	private int prosjekt;

	public ProsjektdeltagelsePK() {}

    public ProsjektdeltagelsePK(int ansattId, int prosjektId) {
        this.ansatt = ansattId;
        this.prosjekt = prosjektId;
    }
}
