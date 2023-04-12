package no.hvl.dat107.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "OBLIG3")
public class Prosjekt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autogenerert SERIAL (integer)
	private int id;

	private String navn;
	private String beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltagelse> deltagelser;

	public Prosjekt() {
	}

	public Prosjekt(String navn, String beskrivelse) {
		this.navn        = navn;
		this.beskrivelse = beskrivelse;
	}

	@Override
	public String toString() {
		return navn +"\n\tId: " + id + "\n\tBeskrivelse:\n\t" + formatterBeskrivelse();
	}

	private String formatterBeskrivelse() {
		String[] ord = beskrivelse.split(" ");
		StringBuilder ny = new StringBuilder();
		for (int i = 0; i < ord.length; i++) {
			ny.append(ord[i]).append(" ");
			if (i != 0 && i % 8 == 0) {
				ny.append("\n\t");
			}
		}
		return ny.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Prosjekt prosjekt = (Prosjekt) o;

		return id == prosjekt.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public int getId() {
		return id;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public List<Prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}

	public void registrerDeltagelse(Prosjektdeltagelse pd) {
		deltagelser.add(pd);
	}

	public void fjernProsjektdeltagelse(Prosjektdeltagelse pd) {
		deltagelser.remove(pd);
	}
}
