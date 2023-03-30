package no.hvl.dat107;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		AnsattDAO ansattDAO = new AnsattDAO();
		System.out.println(ansattDAO.finnAnsattMedAnsattID(1));

		System.out.println("Dette programmet lar deg lete etter ansatte i databasen");
		System.out.println("Skriv inn brukernavnet til den ansatte du ønsker å finne, eller trykk enter uten å skrive inn for å avslutte");
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Skriv inn et brukernavn: ");
			String userinput = scanner.nextLine();
			while (userinput.length() < 3 || userinput.length() > 5) {
				if (userinput.length() == 0) {
					System.out.println("Avbrutt av bruker");
					System.exit(0);
				}
				System.out.print("Brukernavn er på mellom 3 og 5 tegn, prøv igjen: ");
				userinput = scanner.nextLine();
			}
			Ansatt a = ansattDAO.finnAnsattMedBrukernavn(userinput);
			if (a != null) {
				System.out.println("Fant denne brukeren: " + a);
			} else {
				System.out.println("Fant ingen brukere med brukernavn: " + userinput);
			}
		}
	}

}
