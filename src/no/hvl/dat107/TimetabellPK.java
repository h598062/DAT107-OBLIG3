package no.hvl.dat107;

public class TimetabellPK {

	private int ansatt;
	private int prosjekt;

	public TimetabellPK() {}

    public TimetabellPK(int ansattId, int prosjektId) {
        this.ansatt = ansattId;
        this.prosjekt = prosjektId;
    }
}
