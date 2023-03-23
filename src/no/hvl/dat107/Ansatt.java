package no.hvl.dat107;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(schema = "OBLIG3")
public class Ansatt {

	@Id
	private int    ansattid;
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private Date   ansettelsesdato;
	private String stilling;
	private int    maanedslonn;

	@Override
	public String toString() {
		return "Ansatt{" + "ansattid=" + ansattid + ", brukernavn=" + brukernavn + ", fornavn=" + fornavn + ", etternavn=" + etternavn + ", ansettelsesdato=" + ansettelsesdato +
		       ", stilling=" + stilling + ", maanedslonn=" + maanedslonn + '}';
	}
}
