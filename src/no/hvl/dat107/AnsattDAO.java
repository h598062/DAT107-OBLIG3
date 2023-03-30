package no.hvl.dat107;

import jakarta.persistence.*;

import java.util.List;


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


	/**
	 * Returnerer en List med alle rader i tabellen
	 *
	 * @return List med alle Ansatt i tabell
	 */
	public List<Ansatt> finnAlleAnsatte() {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a order by a.brukernavn";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt brukernavn
	 *
	 * @param brukernavn brukernavn til ansatt
	 *
	 * @return Ansatt
	 */
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.brukernavn = '" + brukernavn + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * @param fornavn
	 *
	 * @return
	 */
	public Ansatt finnAnsattMedFornavn(String fornavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.fornavn = '" + fornavn + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Ansatt> finnAnsatteMedFornavn(String fornavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.fornavn = '" + fornavn +
			                        "' order by a.fornavn";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	public Ansatt finnAnsattMedEtternavn(String etternavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.etternavn = '" + etternavn + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Ansatt> finnAnsatteMedEtternavn(String etternavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.etternavn = '" + etternavn +
			                        "' order by a.etternavn";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	public Ansatt finnAnsattMedStilling(String stilling) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.stilling = '" + stilling + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Ansatt> finnAnsatteMedStilling(String stilling) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.stilling = '" + stilling +
			                        "' order by a.stilling";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	public Ansatt oppdaterLonn(int id, int nyLonn) {
		EntityManager     em              = emf.createEntityManager();
		EntityTransaction tx              = em.getTransaction();
		Ansatt            oppdatertAnsatt = null;
		try {
			tx.begin();
			oppdatertAnsatt = em.find(Ansatt.class, id); // Finne rad som skal oppdateres
			oppdatertAnsatt.setMaanedslonn(nyLonn); // Oppdatere managed oject a => sync med database
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return oppdatertAnsatt;
	}
	public Ansatt oppdaterStilling(int id, String nyStilling) {
		EntityManager     em              = emf.createEntityManager();
		EntityTransaction tx              = em.getTransaction();
		Ansatt            oppdatertAnsatt = null;
		try {
			tx.begin();
			oppdatertAnsatt = em.find(Ansatt.class, id); // Finne rad som skal oppdateres
			oppdatertAnsatt.setStilling(nyStilling); // Oppdatere managed oject a => sync med database
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return oppdatertAnsatt;
	}
}
