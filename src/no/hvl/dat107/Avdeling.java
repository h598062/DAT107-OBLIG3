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

	@OneToOne
	@JoinColumn(name = "avdelingsleder")
	private Ansatt avdelingsleder;

	// en ansatt har en avdeling, en avdeling har mange ansatte
	@OneToMany(mappedBy = "avdeling", fetch = FetchType.EAGER, cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JoinColumn
	private List<Ansatt> ansatte;

	public String getNavn() {
		return navn;
	}

	public Ansatt getAvdelingsleder() {
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
		String s = "Avdeling:\t" + navn + "\n\tId:\t" + id + "\n\tAvdelingsleder:\t" +
		           avdelingsleder.getFornavn() + " " + avdelingsleder.getEtternavn() +
		           "\nAvdelingsmedlemmer:\n";
		for (Ansatt a : ansatte) {
			s += a.toString() + "\n";
		}
		return s;
	}
}
