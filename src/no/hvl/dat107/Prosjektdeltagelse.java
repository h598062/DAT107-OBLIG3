package no.hvl.dat107;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "OBLIG3")
@IdClass(ProsjektdeltagelsePK.class)
public class Prosjektdeltagelse {

	@Id
    @ManyToOne
    @JoinColumn(name="ansattid")
    private Ansatt ansatt;

    @Id
    @ManyToOne
    @JoinColumn(name="prosjektid")
    private Prosjekt prosjekt;

	private String rolle;
	private BigDecimal timer;

	public Prosjektdeltagelse() {}

	public Prosjektdeltagelse(Ansatt a, Prosjekt p) {
		this.ansatt = a;
		this.prosjekt = p;
		a.registrerDeltagelse(this);
		p.registrerDeltagelse(this);
	}

	@Override
	public String toString() { // todo fix denne
		return "Prosjektdeltagelse{" + "ansatt=" + ansatt + ", prosjekt=" + prosjekt + '}';
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
}
