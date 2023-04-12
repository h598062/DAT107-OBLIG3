package no.hvl.dat107.dao;

import jakarta.persistence.*;
import no.hvl.dat107.*;
import no.hvl.dat107.entity.*;

public class ProsjektDAO {

	private final EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}

	public void leggTilProsjekt(Prosjekt p) {
		EntityManager     em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(p);
			tx.commit();
		} catch (Exception a) {
			a.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
