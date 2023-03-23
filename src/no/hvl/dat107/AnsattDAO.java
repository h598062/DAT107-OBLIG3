package no.hvl.dat107;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class AnsattDAO {
	private final EntityManagerFactory emf;

	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}

	public Ansatt finnAnsattMedAnsattID(int ansattID) {

		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Ansatt.class, ansattID);
		}
	}
}
