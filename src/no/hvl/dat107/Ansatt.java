package no.hvl.dat107;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(schema = "OBLIG3")
public class Ansatt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autogenerert SERIAL (integer)
	private int       id;
	private String    brukernavn;
	private String    fornavn;
	private String    etternavn;
	private LocalDate ansettelsesdato;
	private String    stilling;
	private int       maanedslonn;

	@ManyToOne // en ansatt har en avdeling, en avdeling har mange ansatte
	@JoinColumn(name = "id", referencedColumnName = "id") // denne sin id e linket mot Avdeling.id
	private Avdeling avdeling; // ny i iterasjon 3

	public Ansatt() {
	}

	/**
	 * Konstruktør som automatisk genererer brukernavn
	 *
	 * @param fornavn         Fornavn
	 * @param etternavn       Etternavn
	 * @param ansettelsesdato Ansettelsesdato
	 * @param stilling        Stilling
	 * @param maanedslonn     Lønn
	 */
	public Ansatt(String fornavn, String etternavn, LocalDate ansettelsesdato, String stilling, int maanedslonn) {
		this.brukernavn      = genererBrukernavn(fornavn, etternavn);
		this.fornavn         = fornavn;
		this.etternavn       = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling        = stilling;
		this.maanedslonn     = maanedslonn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	/**
	 * Intern metode for å generere et brukernavn på 4 tegn.
	 *
	 * @param fornavn   Fornavn
	 * @param etternavn Etternavn
	 *
	 * @return Brukernavn på 4 tegn
	 */
	private String genererBrukernavn(String fornavn, String etternavn) {
		StringBuilder nyttBrukernavn = new StringBuilder();
		if (fornavn == null) {
			throw new IllegalArgumentException("Fornavn kan ikke være null");
		}
		if (etternavn == null) {
			throw new IllegalArgumentException("Etternavn kan ikke være null");
		}
		switch (fornavn.length()) {
			case 0 -> throw new IllegalArgumentException("Fornavn må være på minst ett tegn");

			case 1 -> nyttBrukernavn.append(fornavn.charAt(0))
			                        .append("x");

			default -> nyttBrukernavn.append(fornavn.charAt(0))
			                         .append(fornavn.charAt(1));
		}
		switch (etternavn.length()) {
			case 0 -> throw new IllegalArgumentException("Etternavn må være på minst ett tegn");

			case 1 -> nyttBrukernavn.append(fornavn.charAt(0))
			                        .append("x");

			default -> nyttBrukernavn.append(fornavn.charAt(0))
			                         .append(fornavn.charAt(1));
		}
		return nyttBrukernavn.toString();
	}


	@Override
	public String toString() {
		return fornavn + " " + etternavn + "\n\tAnsatt ID:\t" + id + "\n\tBrukernavn:\t" + brukernavn + "\n\t" + "Ansettelsdato:\t" +
		       ansettelsesdato + "\n\t" + "Stiling:\t" + stilling + "\n\tMånedslønn:\t" + maanedslonn;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public void setMaanedslonn(int maanedslonn) {
		this.maanedslonn = maanedslonn;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public LocalDate getAnsettelsesdato() {
		return ansettelsesdato;
	}

	public String getStilling() {
		return stilling;
	}

	public int getMaanedslonn() {
		return maanedslonn;
	}
}
