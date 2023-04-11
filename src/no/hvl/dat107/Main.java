package no.hvl.dat107;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Scanner     scanner     = new Scanner(System.in);
	private static final AnsattDAO   ansattDAO   = new AnsattDAO();
	private static final AvdelingDAO avdelingDAO = new AvdelingDAO();

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
			                   5. - Legge inn en ny ansatt
			                   6. - Søk etter avdeling med id
			                   7. - Liste ut alle avdelinger
			                   8. - Oppdatere en ansatt sin avdeling
			                   9. - Legge til ny avdeling""");
			System.out.print("Skriv inn et tall fra 0-9: ");
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
					case 6 -> sokAvdelingMedId();
					case 7 -> skrivUtAlleAvdelinger();
					case 8 -> oppdaterEnAnsattSinAvdeling();
					case 9 -> leggeTilNyAvdeling();
					default -> System.out.println("Ingen funksjoner er registrert på menyvalg: " + valgInt);
				}
			} catch (NumberFormatException e) {
				System.out.println("Input " + valg +
				                   " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
			System.out.println();
		}
	}

	private static void skrivUtAlleAvdelinger() {
		List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
		System.out.println("Liste over alle avdelinger: ");
		for (Avdeling a : avdelinger) {
			System.out.println(a);
		}
		System.out.println();
	}

	private static void leggeTilNyAvdeling() {
		System.out.print("Skriv inn navnet på den nye avdelingen: ");
		String skrivInn = scanner.nextLine();
		while (skrivInn == null || skrivInn.length() < 1 || skrivInn.length() > 20) {
			System.out.println("Navnet på avdelingen må være på mellom 1 og 20 tegn");
			System.out.print("Prøv igjen: ");
			skrivInn = scanner.nextLine();
		}
		System.out.println(
				"Den nye avdelingen må ha en sjef,\nvelg en ansatt fra listen under som skal bli sjef for den nye avdelingen:");
		List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
		for (int i = 0; i < ansatte.size(); i++) {
			Ansatt a = ansatte.get(i);
			if (!avdelingDAO.erAvdelingsleder(a)) {
				System.out.println(i + ":\n" + a);
			}
		}
		String skrivInn2    = scanner.nextLine();
		int    skrivInnInt2 = -1;
		while (skrivInnInt2 < 0 || skrivInnInt2 >= ansatte.size()) {
			try {
				skrivInnInt2 = Integer.parseInt(skrivInn2);
			} catch (NumberFormatException e) {
				System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn2);
			}
		}
		Ansatt a = ansatte.get(skrivInnInt2);
		if (a == null) {
			return;
		}
		Avdeling nyAvd = new Avdeling(skrivInn, a);
		avdelingDAO.leggTilAvdeling(nyAvd);
		ansattDAO.oppdaterAvdeling(a.getId(), nyAvd.getId());
	}

	private static void oppdaterEnAnsattSinAvdeling() {
		System.out.print("Skriv inn en ansatt sin id: ");
		String skrivInn    = scanner.nextLine();
		int    skrivInnInt = -1;

		try {
			skrivInnInt = Integer.parseInt(skrivInn);
		} catch (NumberFormatException e) {
			System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn);
		}
		Ansatt a = ansattDAO.finnAnsattMedAnsattID(skrivInnInt);
		if (a == null) {
			System.out.println("Fant ingen ansatt med id: " + skrivInnInt);
			return;
		}
		if (avdelingDAO.erAvdelingsleder(a)) {
			System.out.println(
					"Kan ikke endre avdeling for valgt ansatt da de er sjef for avdeling " + a.getAvdeling());
			return;
		}
		List<Avdeling> avds = avdelingDAO.finnAlleAvdelinger();
		System.out.println("Velg hvilken avdeling den ansatte skal flyttes til:");
		for (int i = 0; i < avds.size(); i++) {
			Avdeling avd = avds.get(i);
			if (avd.equals(a.getAvdeling())) {
				System.out.println("* " + i + ". - " + avd.getNavn() + "\tid: " + avd.getId());
			} else {
				System.out.println("  " + i + ". - " + avd.getNavn() + "\tid: " + avd.getId());
			}
		}
		System.out.print("Velg en avdeling: ");
		String skrivInn2    = scanner.nextLine();
		int    skrivInnInt2 = -1;
		while (skrivInnInt2 < 0 || skrivInnInt2 >= avds.size()) {
			try {
				skrivInnInt2 = Integer.parseInt(skrivInn2);
			} catch (NumberFormatException e) {
				System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn2);
				System.out.print("Prøv igjen: ");
			}
		}
		Avdeling avd = avds.get(skrivInnInt2);
		ansattDAO.oppdaterAvdeling(skrivInnInt, avd.getId());
	}

	private static void sokAvdelingMedId() {
		System.out.print("Skriv inn avdelingsid : ");
		String skrivInn = scanner.nextLine();
		try {
			int skrivInnInt = Integer.parseInt(skrivInn);

			System.out.println(avdelingDAO.finnAvdelingMedID(skrivInnInt));
			System.out.println();

		} catch (NumberFormatException e) {
			System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn);
		}
	}

	private static void avslutt() {
		System.out.println("Avbrutt av bruker");
		System.exit(0);
	}

	private static void leggeTilNyAnsatt() {
		System.out.println("Dette er en veiviser for å opprette en ny bruker," +
		                   " skriv opplysningene du får fra 1881!");
		System.out.print("Skriv inn Fornavn: ");
		String fornavn = scanner.nextLine();

		System.out.print("Skriv inn Etternavn: ");
		String etternavn = scanner.nextLine();

		System.out.print("Skriv inn ansettelsesdato på denne måten 'yyyy-MM-dd': ");
		String ansettelsesdato = scanner.nextLine();

		System.out.print("Skriv inn stilling: ");
		String stilling = scanner.nextLine();

		System.out.print("Skriv inn månedslønn: ");
		String lonn = scanner.nextLine();
		int    lonnint;
		try {
			lonnint = Integer.parseInt(lonn);
		} catch (NumberFormatException e) {
			System.out.println("Input " + lonn +
			                   " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			leggeTilNyAnsatt();
			return;
		}

		//		Sjekker om alle inputs er gyldig
		boolean alleGyldig = false;

		//		Kanskje legge til bedre feilmelding / catch?
		if (fornavn.length() <= 20 && fornavn.length() > 0) {
			if (etternavn.length() <= 30 && etternavn.length() > 0) {
				if (erValid(ansettelsesdato)) {
					if (stilling.length() <= 20 && stilling.length() > 0) {
						if (lonnint > 0) {
							alleGyldig = true;
							System.out.println("Alle inputs er gyldig!");
						} else {
							System.out.println("Lønn er ikke gyldig");
						}
					} else {
						System.out.println("Stilling er ikke gyldig");
					}
				} else {
					System.out.println("Ansettelsesdato er ikke gylidg");
				}
			} else {
				System.out.println("Etternavn er ikke gyldig");
			}
		} else {
			System.out.println("Fornavn er ikke gyldig");
		}

		//		Sender ny ansatt til metode i AnsattDAO for å bli opprettet
		if (alleGyldig) {
			System.out.println("Velg hvilken avdeling den anssatte skal jobbe i:");
			List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
			for (int i = 0; i < avdelinger.size(); i++) {
				System.out.println(i + ". - " + avdelinger.get(i)
				                                          .getNavn() + " - id: " + avdelinger.get(i)
				                                                                             .getId());
			}
			System.out.print("Skriv inn avdelingsid : ");
			int skrivInnInt = -1;
			while (skrivInnInt < 0 || skrivInnInt >= avdelinger.size()) {
				String skrivInn = scanner.nextLine();
				try {
					skrivInnInt = Integer.parseInt(skrivInn);

					System.out.println(avdelingDAO.finnAvdelingMedID(skrivInnInt));
					System.out.println();

				} catch (NumberFormatException e) {
					System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + skrivInn);
					System.out.println("Prøv igjen: ");
				}
			}
			Avdeling avd = avdelinger.get(skrivInnInt);
			Ansatt nyAnsatt = new Ansatt(fornavn, etternavn, LocalDate.parse(ansettelsesdato), stilling,
					lonnint, avd);
			// Legg til en ansatt med riktige parameter
			ansattDAO.leggTilNyAnsatt(nyAnsatt);
		}

	}

	private static boolean erValid(String input) {
		String           formatString = "yyyy-MM-dd";
		SimpleDateFormat format       = new SimpleDateFormat(formatString);
		try {
			Date date = format.parse(input);
			return input.equals(format.format(date));
		} catch (Exception e) {
			return false;
		}
	}

	private static void oppdatereEnAnsattSinStillingEllerLonn() {
		System.out.println("Ønsker du å oppdatere Stilling eller Lønn?");
		System.out.println("\t0. - Gå tilbake\n\t1. - Stilling\n\t2. - Lønn");
		while (true) {
			System.out.print("Skriv inn et tall fra 0-2: ");
			String valg = scanner.nextLine();
			try {
				int     valgInt    = Integer.parseInt(valg);
				boolean validInput = false;
				switch (valgInt) {
					case 0 -> {
						System.out.println("Går tilbake til meny...");
						return;
					}
					case 1 -> {
						System.out.println("Du har valgt å oppdatere Stilling for en ansatt");
						while (!validInput) {
							System.out.print(
									"Skriv inn id (heltall) til den ansatte du ønsker å endre stilling på: ");
							String inputID = scanner.nextLine();
							System.out.print("Skriv inn ny stilling til denne ansatte: ");
							String inputStilling = scanner.nextLine();
							try {
								int inputIDInt = Integer.parseInt(inputID);
								if (inputStilling.length() < 1) {
									System.out.println("Stilling må være en tekststreng på minst ett tegn");
								} else {
									Ansatt a = ansattDAO.oppdaterStilling(inputIDInt, inputStilling);
									System.out.println("Oppdatert ansatt:\n" + a);
									validInput = true;
								}
							} catch (NumberFormatException e) {
								System.out.println("Ikke gyldig id: " + inputID + ", prøv igjen");
							}
						}
					}
					case 2 -> {
						System.out.println("Du har valgt å oppdatere Lønn for en ansatt");
						while (!validInput) {
							System.out.print(
									"Skriv inn id (heltall) til den ansatte du ønsker å endre lønn på: ");
							String inputID = scanner.nextLine();
							System.out.print("Skriv inn ny lønn til denne ansatte: ");
							String inputLonn = scanner.nextLine();
							try {
								int    inputIDInt   = Integer.parseInt(inputID);
								int    inputLonnInt = Integer.parseInt(inputLonn);
								Ansatt a            = ansattDAO.oppdaterLonn(inputIDInt, inputLonnInt);
								System.out.println("Oppdatert ansatt:\n" + a);
								validInput = true;
							} catch (NumberFormatException e) {
								System.out.println(
										"Ikke gyldig id og/eller lønn, prøv igjen: id: " + inputID +
										", lønn: " + inputLonn);
							}
						}
					}
					default -> System.out.println("Ingen funksjoner er registrert på menyvalg: " + valgInt);

				}
				System.out.println();
				return;
			} catch (NumberFormatException e) {
				System.out.println("Input " + valg +
				                   " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
		}

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
