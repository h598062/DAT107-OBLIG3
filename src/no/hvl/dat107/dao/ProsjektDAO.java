package no.hvl.dat107.dao;

import jakarta.persistence.*;
import no.hvl.dat107.*;
import no.hvl.dat107.entity.*;

public class ProsjektDAO {

	private final EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}

	/**
	 * Legger til et nytt prosjekt
	 *
	 * @param p Prosjektet som skal legges til
	 */
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

	/**
	 * Finner et prosjekt med prosjekt id
	 *
	 * @param prosjektId id til prosjektet
	 *
	 * @return referanse til Prosjekt objekt
	 */
	public Prosjekt finnProsjektMedId(int prosjektId) {
		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Prosjekt.class, prosjektId);
		}
	}
}
