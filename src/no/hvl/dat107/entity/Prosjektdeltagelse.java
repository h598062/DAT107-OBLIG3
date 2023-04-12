package no.hvl.dat107.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "OBLIG3")
@IdClass(ProsjektdeltagelsePK.class)
public class Prosjektdeltagelse {

	@Id
	@ManyToOne
	@JoinColumn(name = "ansattid")
	private Ansatt ansatt;

	@Id
	@ManyToOne
	@JoinColumn(name = "prosjektid")
	private Prosjekt prosjekt;

	private String     rolle;
	private BigDecimal timer;

	public Prosjektdeltagelse() {
	}

	/**
	 * Oppretter en ny prosjektdeltagelse og linker den mot et prosjekt og en ansatt
	 *
	 * @param a     ansatt som jobber i prosjektet
	 * @param p     prosjektet den ansatte skal jobbe i
	 * @param rolle rollen den ansatte har i prosjektet
	 * @param timer tid som skal legges inn på den ansatte. Dette oppgis som en string, f.eks. "1.5". Dette er pga. å unngå floting point feil.
	 */
	public Prosjektdeltagelse(Ansatt a, Prosjekt p, String rolle, String timer) {
		this.ansatt   = a;
		this.prosjekt = p;
		this.rolle    = rolle;
		this.timer    = new BigDecimal(timer);
		a.registrerDeltagelse(this);
		p.registrerDeltagelse(this);
	}

	@Override
	public String toString() {
		return ansatt + "\n" + prosjekt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Prosjektdeltagelse that = (Prosjektdeltagelse) o;

		if (!ansatt.equals(that.ansatt)) {
			return false;
		}
		return prosjekt.equals(that.prosjekt);
	}

	@Override
	public int hashCode() {
		int result = ansatt.hashCode();
		result = 31 * result + prosjekt.hashCode();
		return result;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public String getRolle() {
		return rolle;
	}

	public BigDecimal getTimer() {
		return timer;
	}

	/**
	 * Legger til timer til denne prosjektdeltagelsen
	 * @param timer Riktig formatert string timer, se validerTimerForProsjektdeltagelse i Main
	 */
	public void leggTilTimer(String timer) {
		BigDecimal bdtimer = new BigDecimal(timer);
		this.timer = this.timer.add(bdtimer);
	}
}
