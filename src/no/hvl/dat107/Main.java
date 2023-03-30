package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final AnsattDAO ansattDAO = new AnsattDAO();

	public static void main(String[] args) {

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
				System.out.println(
						"Input " + valg + " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
		}
	}

	private static void avslutt() {
		System.out.println("Avbrutt av bruker");
		System.exit(0);
	}

	private static void leggeTilNyAnsatt() {
		System.out
				.println("Dette er en veiviser for å opprette en ny bruker," + " skriv opplysningene du får fra 1881!");
		System.out.print("Skriv inn Fornavn: ");
		String fornavn = scanner.nextLine();

		System.out.print("Skriv inn Etternavn: ");
		String etternavn = scanner.nextLine();

		System.out.print("Skriv inn ansettelsesdato: ");
		String ansettelsesdato = scanner.nextLine();

		System.out.print("Skriv inn stilling: ");
		String stilling = scanner.nextLine();

		System.out.print("Skriv inn månedslønn: ");
		String lonn = scanner.nextLine();
		int lonnint;
		try {
			lonnint = Integer.parseInt(lonn);
		} catch (NumberFormatException e) {
			System.out.println(
					"Input " + lonn + " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			leggeTilNyAnsatt();
			return;
		}

//		TODO 
//		Sjekke om inputs er gyldig 

	}

	private static void oppdatereEnAnsattSinStillingEllerLonn() {
	}

	private static void skrivUtAlleAnsatte() {
		List<Ansatt> al = ansattDAO.finnAlleAnsatte();
		System.out.println("Liste over alle ansatte: ");
		for (Ansatt a : al) {
			System.out.println(a);
		}
		System.out.println();
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

		System.out.print("Skriv inn ansattnummer : ");
		String skrivInn = scanner.nextLine();
		try {
			int skrivInnInt = Integer.parseInt(skrivInn);

			System.out.println(ansattDAO.finnAnsattMedAnsattID(skrivInnInt));
			System.out.println();

		} catch (NumberFormatException e) {
			System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn);
		}
	}

}
