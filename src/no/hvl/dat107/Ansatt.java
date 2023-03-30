package no.hvl.dat107;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(schema = "OBLIG3")
public class Ansatt {

	@Id
	private int       ansattid;
	private String    brukernavn;
	private String    fornavn;
	private String    etternavn;
	private LocalDate ansettelsesdato;
	private String    stilling;
	private int       maanedslonn;

	//	Ansatt + id + brukernavn
	//		Fornavn etternavn
	//		dato
	//		stilling og lønn


	public Ansatt() {
	}

	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato,
	              String stilling, int maanedslonn) {
		this.brukernavn      = brukernavn;
		this.fornavn         = fornavn;
		this.etternavn       = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling        = stilling;
		this.maanedslonn     = maanedslonn;
	}

	@Override
	public String toString() {
		return fornavn + " " + etternavn + "\n\tAnsatt ID:\t" + ansattid + "\n\tBrukernavn:\t" + brukernavn +
		       "\n\t" + "Ansettelsdato:\t" + ansettelsesdato + "\n\t" + "Stiling:\t" + stilling +
		       "\n\tMånedslønn:\t" + maanedslonn;
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
