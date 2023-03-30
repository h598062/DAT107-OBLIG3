package no.hvl.dat107;

import java.util.Scanner;

public class Main {
	private static final Scanner   scanner   = new Scanner(System.in);
	private static final AnsattDAO ansattDAO = new AnsattDAO();

	public static void main(String[] args) {
		System.out.println("Hello World!");
		System.out.println(ansattDAO.finnAnsattMedAnsattID(1));

		System.out.println("Dette programmet lar deg lete etter ansatte i databasen");

		while (true) {
			System.out.println("Hva ønsker du å søke etter?");
			System.out.println("""
			                   0. - Avslutt
			                   1. - Søke etter ansatt på ansatt-id
			                   2. - Søke etter ansatt på brukernavn (initialer)
			                   3. - Utlisting av alle ansatte
			                   4. - Oppdatere en ansatt sin stilling og/eller lønn
			                   5. - Legge inn en ny ansatt""");
			System.out.print("Skriv inn et tall fra 0-5: ");
			String valg = scanner.nextLine();

			try {
				int valgInt = Integer.parseInt(valg);
				switch (valgInt) {
					case 0 -> avslutt();
					case 1 -> sokMedAnsattId();
					case 2 -> sokMedBrukernavn();
					case 3 -> skrivUtAlleAnsatte();
					case 4 -> oppdatereEnAnsattSinStillingEllerLonn();
					case 5 -> leggeTilNyAnsatt();
					default -> {
						System.out.println("Ingen funksjoner er registrert på menyvalg: " + valgInt);
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Input " + valg +
				                   " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
		}
	}

	private static void avslutt() {
		System.out.println("Avbrutt av bruker");
		System.exit(0);
	}

	private static void leggeTilNyAnsatt() {
	}

	private static void oppdatereEnAnsattSinStillingEllerLonn() {
	}

	private static void skrivUtAlleAnsatte() {
	}

	private static void sokMedBrukernavn() {
		System.out.println(
				"Skriv inn brukernavnet til den ansatte du ønsker å finne, eller trykk enter uten å skrive inn for å avslutte ansattsøk");

		while (true) {
			System.out.print("Skriv inn et brukernavn: ");
			String userinput = scanner.nextLine();
			while (userinput.length() < 3 || userinput.length() > 5) {
				if (userinput.length() == 0) {
					System.out.println("Avbrutt ansatt søk fra bruker");
					return;
				}
				System.out.print("Brukernavn er på mellom 4 og 5 tegn, prøv igjen: ");
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

	private static void sokMedAnsattId() {
	}

}
