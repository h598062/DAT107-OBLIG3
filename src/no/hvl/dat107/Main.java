package no.hvl.dat107;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		AnsattDAO ansattDAO = new AnsattDAO();
		System.out.println(ansattDAO.finnAnsattMedAnsattID(1));
	}

}
