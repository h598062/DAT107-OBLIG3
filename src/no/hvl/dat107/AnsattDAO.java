package no.hvl.dat107;

import jakarta.persistence.*;

import java.security.InvalidParameterException;
import java.util.List;


public class AnsattDAO {
	private final EntityManagerFactory emf;

	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}

	/**
	 * Finner en Ansatt med gitt ID
	 *
	 * @param ansattID Integer id til ansatt
	 *
	 * @return Referanse til Ansatt
	 */
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
	 * Returnerer Ansatt med oppgitt brukernavn
	 *
	 * @param brukernavn brukernavn til ansatt
	 *
	 * @return Liste med Ansatt
	 */
	public List<Ansatt> finnAnsatteMedBrukernavn(String brukernavn) {
		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.brukernavn = '" + brukernavn + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt fornavn
	 *
	 * @param fornavn fornavn til ansatt
	 *
	 * @return Ansatt
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

	/**
	 * Returnerer Ansatt med oppgitt fornavn
	 *
	 * @param fornavn fornavn til ansatt
	 *
	 * @return Liste med Ansatt
	 */
	public List<Ansatt> finnAnsatteMedFornavn(String fornavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.fornavn = '" + fornavn + "' order by a.fornavn";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt etternavn
	 *
	 * @param etternavn etternavn til ansatt
	 *
	 * @return Ansatt
	 */
	public Ansatt finnAnsattMedEtternavn(String etternavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.etternavn = '" + etternavn + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt etternavn
	 *
	 * @param etternavn etternavn til ansatt
	 *
	 * @return Liste med Ansatt
	 */
	public List<Ansatt> finnAnsatteMedEtternavn(String etternavn) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.etternavn = '" + etternavn + "' order by a.etternavn";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt stilling
	 *
	 * @param stilling stilling til ansatt
	 *
	 * @return Ansatt
	 */
	public Ansatt finnAnsattMedStilling(String stilling) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.stilling = '" + stilling + "'";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Returnerer Ansatt med oppgitt stilling
	 *
	 * @param stilling stilling til ansatt
	 *
	 * @return Liste med Ansatt
	 */
	public List<Ansatt> finnAnsatteMedStilling(String stilling) {

		try (EntityManager em = emf.createEntityManager()) {
			String             ql = "select a from Ansatt as a where a.stilling = '" + stilling + "' order by a.stilling";
			TypedQuery<Ansatt> q  = em.createQuery(ql, Ansatt.class);
			return q.getResultList();
		}
	}

	/**
	 * Oppdaterer lønn til en ansatt med oppgitt id
	 *
	 * @param id     id til ansatt som skal endres
	 * @param nyLonn ny lønn den ansatte skal ha
	 *
	 * @return referanse til den oppdaterte Ansatt
	 */
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

	/**
	 * Oppdaterer stilling til en ansatt med oppgitt id
	 *
	 * @param id         id til ansatt som skal endres
	 * @param nyStilling ny stilling den ansatte skal ha
	 *
	 * @return referanse til den oppdaterte Ansatt
	 */
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

	/**
	 * Legger til en ny ansatt i database<br>
	 * Under oppbygning, PASS PÅ
	 *
	 * @param nyAnsatt Den nye ansatte som skal legges til
	 */
	public void leggTilNyAnsatt(Ansatt nyAnsatt) {

		while (!sjekkBrukernavn(nyAnsatt)) {
			nyAnsatt.setBrukernavn(lagNyttBrukernavn(nyAnsatt));
		}

		EntityManager     em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(nyAnsatt);
			tx.commit();
		} catch (Exception a) {
			a.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

	}

	/**
	 * Intern metode for å sjekke at brukernavn ikke er i bruk
	 *
	 * @param a en Ansatt
	 *
	 * @return true om brukernavnet er ledig
	 */
	private boolean sjekkBrukernavn(Ansatt a) {
		return finnAnsatteMedBrukernavn(a.getBrukernavn()).isEmpty();
	}

	/**
	 * Genererer et nytt brukernavn for en Ansatt<br>
	 * denne metoden gjør ingen direkte endringer, men returnerer det nye brukernavnet<br>
	 * legger til eller inkrementerer en integer på slutten av brukernavnet
	 *
	 * @param a en Ansatt
	 *
	 * @return Det nye brukernavnet til den ansatte
	 */
	private String lagNyttBrukernavn(Ansatt a) {
		String nyttBrukernavn = "";

		switch (a.getBrukernavn()
		         .length()) {
			case 0, 1, 2 -> throw new InvalidParameterException("Brukernavn må være på minst 3 tegn");

			case 3, 4 -> nyttBrukernavn = a.getBrukernavn() + "1"; // antar at det kun er bokstaver i brukernavn

			case 5 -> { // antar at siste tegn er en int
				int i = Character.getNumericValue(a.getBrukernavn()
				                                   .charAt(4));
				i++;
				nyttBrukernavn = a.getBrukernavn()
				                  .substring(0, 4) + i;
			}
			default -> throw new InvalidParameterException("Brukernavn kan ikke være mer enn 5 tegn langt");

		}
		return nyttBrukernavn;
	}
}
