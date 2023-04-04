package no.hvl.dat107;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

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
			String             ql = "select a from Avdeling as a";
			TypedQuery<Avdeling> q  = em.createQuery(ql, Avdeling.class);
			return q.getResultList();
		}
	}

}
