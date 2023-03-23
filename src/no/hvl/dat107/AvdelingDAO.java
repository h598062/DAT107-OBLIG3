package no.hvl.dat107;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AvdelingDAO {

	private final EntityManagerFactory emf;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}
}
