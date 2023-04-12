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
	private List<Timetabell> timeregistrering;
}
