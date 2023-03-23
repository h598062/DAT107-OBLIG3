package no.hvl.dat107;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProsjektDAO {

	private final EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}
}
