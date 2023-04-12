package no.hvl.dat107.dao;

import jakarta.persistence.*;
import no.hvl.dat107.*;
import no.hvl.dat107.entity.*;

public class ProsjektDAO {

	private final EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PU", ServerSetup.getServerConfig());
	}
}
