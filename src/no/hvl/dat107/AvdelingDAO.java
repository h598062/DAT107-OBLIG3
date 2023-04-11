package no.hvl.dat107;

import jakarta.persistence.*;

import java.util.List;

public class AvdelingDAO {

	private final EntityManagerFactory emf;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}

	/**
	 * Finner en Avdeling med gitt ID
	 *
	 * @param avdelingID Integer id til avdeling
	 *
	 * @return Referanse til Avdeling
	 */
	public Avdeling finnAvdelingMedID(int avdelingID) {

		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Avdeling.class, avdelingID);
		}
	}

	/**
	 * Returnerer en List med alle rader i tabellen
	 *
	 * @return List med alle Avdeling i tabell
	 */
	public List<Avdeling> finnAlleAvdelinger() {

		try (EntityManager em = emf.createEntityManager()) {
			String               ql = "select a from Avdeling as a where a.id > 1"; // ignorer avdeling med id 1 siden det er en test avdeling
			TypedQuery<Avdeling> q  = em.createQuery(ql, Avdeling.class);
			return q.getResultList();
		}
	}

	public void leggTilAvdeling(Avdeling avdeling) {
		EntityManager     em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(avdeling);
			tx.commit();
		} catch (Exception a) {
			a.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public boolean erAvdelingsleder(Ansatt a) {
		List<Avdeling> avds = finnAlleAvdelinger();
		for (Avdeling avd : avds) {
			if (avd.getAvdelingsleder()
			       .equals(a)) {
				return true;
			}
		}
		return false;
	}
}
