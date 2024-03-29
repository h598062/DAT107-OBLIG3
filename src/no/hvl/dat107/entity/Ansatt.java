package no.hvl.dat107.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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
	@JoinColumn(name = "avdeling") // Dette er navnet på kolonnen i ansatt tabellen
	private Avdeling avdeling; // ny i iterasjon 3

	// liste linket mot samletabell prosjektdeltagelser
	@OneToMany(mappedBy = "ansatt")
	private List<Prosjektdeltagelse> deltagelser; // ny i iterasjon 5

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
	public Ansatt(String fornavn, String etternavn, LocalDate ansettelsesdato, String stilling, int maanedslonn, Avdeling avdeling) {
		this.brukernavn      = genererBrukernavn(fornavn, etternavn);
		this.fornavn         = fornavn;
		this.etternavn       = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling        = stilling;
		this.maanedslonn     = maanedslonn;
		this.avdeling        = avdeling; // todo må fikse
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

			case 1 -> nyttBrukernavn.append(etternavn.charAt(0))
			                        .append("x");

			default -> nyttBrukernavn.append(etternavn.charAt(0))
			                         .append(etternavn.charAt(1));
		}
		return nyttBrukernavn.toString()
		                     .toLowerCase();
	}

	@Override
	public String toString() {
		return fornavn + " " + etternavn + "\n\tAnsatt ID:\t" + id + "\n\tBrukernavn:\t" + brukernavn + "\n\t" + "Ansettelsdato:\t" +
		       ansettelsesdato + "\n\t" + "Stiling:\t" + stilling + "\n\tMånedslønn:\t" + maanedslonn + "\n\tAvdeling:\t" + avdeling.getNavn();
	}

	public int getId() {
		return id;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
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

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public int getMaanedslonn() {
		return maanedslonn;
	}

	public void setMaanedslonn(int maanedslonn) {
		this.maanedslonn = maanedslonn;
	}

	public Avdeling getAvdeling() {
		return avdeling;
	}

	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Ansatt ansatt = (Ansatt) o;

		return id == ansatt.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public List<Prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}

	/**
	 * Registrerer en ny deltagelse i denne ansatte sin liste<br>
	 * OBS!!! Denne metoden antar at prosjektdeltagelsen er gyldig og linket mot riktig ansatt
	 * @param pd Ny prosjektdeltagelse
	 */
	public void registrerDeltagelse(Prosjektdeltagelse pd) {
		deltagelser.add(pd);
	}

	public void fjernProsjektdeltagelse(Prosjektdeltagelse pd) {
		deltagelser.remove(pd);
	}
}
