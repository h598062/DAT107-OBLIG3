package no.hvl.dat107;

import jakarta.persistence.*;

@Entity
@Table(schema = "OBLIG3")
@IdClass(TimetabellPK.class)
public class Timetabell {

	@Id
    @ManyToOne
    @JoinColumn(name="ansattid")
    private Ansatt ansatt;

    @Id
    @ManyToOne
    @JoinColumn(name="prosjektid")
    private Prosjekt prosjekt;

	public Timetabell() {}
}
