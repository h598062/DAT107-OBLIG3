package no.hvl.dat107;

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
	public String toString() { // todo fix denne
		return "Prosjekt{" + "id=" + id + ", navn='" + navn + '\'' + ", beskrivelse='" + beskrivelse + '\'' + ", deltagelser=" + deltagelser + '}';
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
