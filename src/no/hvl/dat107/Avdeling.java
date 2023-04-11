package no.hvl.dat107;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "OBLIG3")
public class Avdeling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autogenerert SERIAL (integer)
	private int id;

	private String navn;

	// må mappe denne mot Ansatt.id
	private int avdelingsleder;

	// en ansatt har en avdeling, en avdeling har mange ansatte
	@OneToMany(mappedBy = "avdeling", fetch = FetchType.EAGER, cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JoinColumn
	private List<Ansatt> ansatte;

	public String getNavn() {
		return navn;
	}

	public int getAvdelingsleder() {
		return avdelingsleder;
	}

	public int getId() {
		return id;
	}

	public List<Ansatt> getAnsatte() {
		return ansatte;
	}

	@Override
	public String toString() {
		return "Avdeling{" + "id=" + id + ", navn='" + navn + '\'' + ", avdelingsleder=" + avdelingsleder +
		       ", ansatte=" + ansatte + '}';
	}
}
