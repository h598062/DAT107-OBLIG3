package no.hvl.dat107;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltagelse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Scanner     scanner     = new Scanner(System.in);
	private static final AnsattDAO   ansattDAO   = new AnsattDAO();
	private static final AvdelingDAO avdelingDAO = new AvdelingDAO();

	private static final ProsjektDAO prosjektDAO = new ProsjektDAO();

	public static void main(String[] args) {

		System.out.println("Dette programmet lar deg lete etter ansatte i databasen");
		boolean avslutt = false;
		while (!avslutt) {
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
			                    9. - Legge til ny avdeling
			                   10. - Legg til et nytt prosjekt
			                   11. - Registrer en ny prosjektdeltagelse for en ansatt på et prosjekt
			                   12. - Oppdater timer for en ansatt på et prosjekt (føre timer)
			                   13. - Skriv ut info om et prosjekt""");
			System.out.print("Skriv inn et tall fra 0-13: ");
			String valg = scanner.nextLine();

			try {
				int valgInt = Integer.parseInt(valg);
				switch (valgInt) {
					case 0 -> avslutt = true;
					case 1 -> sokMedAnsattId();
					case 2 -> sokMedBrukernavn();
					case 3 -> skrivUtAlleAnsatte();
					case 4 -> oppdatereEnAnsattSinStillingEllerLonn();
					case 5 -> leggeTilNyAnsatt();
					case 6 -> sokAvdelingMedId();
					case 7 -> skrivUtAlleAvdelinger();
					case 8 -> oppdaterEnAnsattSinAvdeling();
					case 9 -> leggeTilNyAvdeling();
					case 10 -> leggeTilNyttProsjekt();
					case 11 -> registrerProsjektDeltagelse();
					case 12 -> oppdatereTimerForProsjektDeltagelse();
					case 13 -> skrivUtProsjektInfo();
					default -> System.out.println("Ingen funksjoner er registrert på menyvalg: " + valgInt);
				}
			} catch (NumberFormatException e) {
				System.out.println("Input " + valg + " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
			System.out.println();
		}
		System.out.println("Avbrutt av bruker");
	}

	/**
	 * Metode for å lese en id fra bruker via scanner
	 *
	 * @param type Hvilken type id, dette vil stå der det er '***' "Skriv inn id til ***: "
	 *
	 * @return id integer
	 */
	private static int hentOgValiderIntId(String type) {
		System.out.printf("Skriv inn id til %s: ", type);
		String input = scanner.nextLine();
		int    id    = -1;
		while (id < 1) {
			try {
				id = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Det du har skrevet inn inneholder annet enn tall, prøv igjen: " + input);
			}
			if (id < 1) {
				System.out.println("Id må være >= 1");
				System.out.print("Prøv igjen: ");
				input = scanner.nextLine();
			}
		}
		return id;
	}

	/**
	 * Utskrift av info om prosjekt, inkl. liste av deltagere med rolle og timer, og totalt timetall for prosjektet
	 */
	private static void skrivUtProsjektInfo() {

		int      pid = hentOgValiderIntId("prosjekt");
		Prosjekt p   = prosjektDAO.finnProsjektMedId(pid);
		if (p == null) {
			System.out.println("Fant ingen prosjekt med id " + pid);
			return;
		}
		System.out.println("Valgt prosjekt:\n" + p);
		List<Prosjektdeltagelse> deltagelser = p.getDeltagelser();
		System.out.println("Deltagelser:");
		BigDecimal timeSum = new BigDecimal(0);
		for (Prosjektdeltagelse pd : deltagelser) {
			System.out.println(pd.getAnsatt() + "\n\tRolle: " + pd.getRolle() + "\n\tTimer: " + pd.getTimer().toString());
			timeSum = timeSum.add(pd.getTimer());
		}
		System.out.println("Totalt antall timer for alle deltagere: " + timeSum);
	}

	/**
	 * Føre timer for en ansatt på et prosjekt
	 */
	private static void oppdatereTimerForProsjektDeltagelse() {
		System.out.println("Veiviser for å legge til flere timer for en ansatt på et prosjekt");
		int aid = hentOgValiderIntId("ansatt");
		int pid = hentOgValiderIntId("prosjekt");
		System.out.println("Skriv inn antall timer som skal legges til." +
		                   "\nDette må være på formatet h.h, altså hvis en ansatt har brukt 2t 30 min så skriver du 2.5");
		System.out.print("Antall timer: ");
		String timer = scanner.nextLine();
		while (!validerTimerForProsjektdeltagelse(timer)) {
			System.out.println("Feil i format for timer");
			System.out.print("Prøv igjen: ");
			timer = scanner.nextLine();
		}

		ansattDAO.leggTilTimer(aid, pid, timer);
	}

	/**
	 * Registrere prosjektdeltagelse (ansatt med rolle i prosjekt)
	 */
	private static void registrerProsjektDeltagelse() {
		System.out.println("Veiviser for å registere en ny prosjektdeltagelse for en ansatt");
		int aid = hentOgValiderIntId("ansatt");
		int pid = hentOgValiderIntId("prosjekt");
		System.out.println("Skriv inn rollen den ansatte har på prosjektet: ");
		String rolle = hentOgValiderTekststreng(20, "rolle");
		System.out.println("Skriv inn antall timer den ansatte har brukt på prosjektet så langt." +
		                   "\nDette må være på formatet h.h, altså hvis en ansatt har brukt 2t 30 min så skriver du 2.5");
		System.out.print("Antall timer: ");
		String timer = scanner.nextLine();
		while (!validerTimerForProsjektdeltagelse(timer)) {
			System.out.println("Feil i format for timer");
			System.out.print("Prøv igjen: ");
			timer = scanner.nextLine();
		}

		ansattDAO.registrerProsjektdeltagelse(aid, pid, rolle, timer);
	}

	/**
	 * metode for å hente inn en tekststreng med gitt makslengde fra bruker via scanner
	 *
	 * @param lengde maks lengde, eller -1 for uendelig
	 * @param type   Hvilken type tekststreng, dette vil stå der det er '***' "Skriv inn tekststteng ***: "
	 *
	 * @return Tekststreng
	 */
	private static String hentOgValiderTekststreng(int lengde, String type) {
		System.out.printf("Skriv inn tekststreng %s: ", type);
		String input = scanner.nextLine();
		String s = type.substring(0, 1)
		               .toUpperCase() + type.substring(1);
		if (lengde == -1) {
			while (input.length() < 1) {
				System.out.printf("%s må være på mist ett tegn%n", s);
				System.out.print("Prøv igjen: ");
				input = scanner.nextLine();
			}
		} else {
			while (input.length() < 1 || input.length() > lengde) {
				System.out.printf("%s må være på mist ett tegn og maks %d%n", s, lengde);
				System.out.print("Prøv igjen: ");
				input = scanner.nextLine();
			}
		}
		return input;
	}

	/**
	 * Hjelpemetode for å validere timestring for prosjektdeltagelse konstruktør.<br>
	 * Det er nødvendig med timer som en string for å unngå floatinfpoint feil ved konvertering
	 *
	 * @param timer String på format h.h, f.eks "2.5" timer aka. 2t 30min, eller "2" aka 2t
	 *
	 * @return True hvis input streng er ok
	 */
	private static boolean validerTimerForProsjektdeltagelse(String timer) {
		if (timer == null || timer.length() < 1) {
			return false;
		}

		if (timer.contains(".")) {
			String[] parts = timer.split("\\.");
			if (parts.length != 2) {
				return false;
			}
			try {
				Integer.parseInt(parts[0]);
				Integer.parseInt(parts[1]);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			try {
				Integer.parseInt(timer);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Legge inn et nytt prosjekt
	 */
	private static void leggeTilNyttProsjekt() {

		System.out.println("Veiviser for å legge til nytt prosjekt");
		System.out.println("Skriv inn navn på prosjektet");
		String navn = hentOgValiderTekststreng(20, "navn");
		System.out.println("Skriv inn beskrivelse til prosjektet");
		String   beskrivelse = hentOgValiderTekststreng(-1, "beskrivelse");
		Prosjekt p           = new Prosjekt(navn, beskrivelse);
		prosjektDAO.leggTilProsjekt(p);
		System.out.println("Lagt til nytt prosjekt: " + p.getNavn());
	}

	/**
	 * Skriver ut alle avdelinger
	 */
	private static void skrivUtAlleAvdelinger() {
		List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
		System.out.println("Liste over alle avdelinger: ");
		for (Avdeling a : avdelinger) {
			System.out.println(a);
		}
		System.out.println();
	}

	/**
	 * Legge inn en ny avdeling. NB! Siden en avdeling MÅ ha en sjef, må man velge blant en av de allerede
	 * eksisterende ansatte (som da jobber i en annen avdeling). Den ansatte som blir sjef i den nye
	 * avdelingen skal automatisk overføres til avdelingen vedkommende blir sjef for.
	 */
	private static void leggeTilNyAvdeling() {
		System.out.println("Veiviser for å legge til ny avdeling");
		System.out.println("Skriv inn navnet på den nye avdelingen");
		String navn = hentOgValiderTekststreng(20, "navn");
		System.out.println("Den nye avdelingen må ha en sjef,\nvelg en ansatt fra listen under som skal bli sjef for den nye avdelingen:");
		List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
		for (int i = 0; i < ansatte.size(); i++) {
			Ansatt a = ansatte.get(i);
			if (!avdelingDAO.erAvdelingsleder(a)) {
				System.out.println(i + ":\n" + a);
			}
		}
		String input    = scanner.nextLine();
		int    inputInt = -1;
		while (inputInt < 0 || inputInt >= ansatte.size()) {
			try {
				inputInt = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + input);
			}
		}
		Ansatt a = ansatte.get(inputInt);
		if (a == null) {
			return;
		}
		Avdeling nyAvd = new Avdeling(navn, a);
		avdelingDAO.leggTilAvdeling(nyAvd);
		ansattDAO.oppdaterAvdeling(a.getId(), nyAvd.getId());
		System.out.println("Lagt til ny avdeling: " + nyAvd.getNavn());
	}

	/**
	 * Oppdatere hvilken avdeling en ansatt jobber på. Man kan ikke bytte avdeling hvis man er sjef!
	 */
	private static void oppdaterEnAnsattSinAvdeling() {
		System.out.println("Veiviser for å oppdatere en ansatt sin avdeling.");
		int    aid = hentOgValiderIntId("ansatt");
		Ansatt a   = ansattDAO.finnAnsattMedAnsattID(aid);
		if (a == null) {
			System.out.println("Fant ingen ansatt med id: " + aid);
			return;
		}
		if (avdelingDAO.erAvdelingsleder(a)) {
			System.out.println("Kan ikke endre avdeling for valgt ansatt da de er sjef for avdeling " + a.getAvdeling());
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
		String input    = scanner.nextLine();
		int    inputInt = -1;
		while (inputInt < 0 || inputInt >= avds.size()) {
			try {
				inputInt = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Dette innholder noe anna en kun tal, prøv noko anno: " + input);
				System.out.print("Prøv igjen: ");
			}
		}
		Avdeling avd = avds.get(inputInt);
		ansattDAO.oppdaterAvdeling(aid, avd.getId());
		System.out.printf("Oppdatert %s %s sin avdeling%n", a.getFornavn(), a.getEtternavn());
	}

	/**
	 * Søke etter avdeling på avdeling-id<br>
	 * Utlisting av alle ansatte på en avdeling inkl. utheving av hvem som er sjef
	 */
	private static void sokAvdelingMedId() {
		int avdid = hentOgValiderIntId("avdeling");
		System.out.println(avdelingDAO.finnAvdelingMedID(avdid));
	}

	/**
	 * Legge inn en ny ansatt må nå også angi hvilken avdeling vedkommende skal jobbe på
	 */
	private static void leggeTilNyAnsatt() {
		System.out.println("Dette er en veiviser for å opprette en ny bruker," + " skriv opplysningene du får fra 1881!");
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
			System.out.println("Input " + lonn + " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
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
			Avdeling avd      = avdelinger.get(skrivInnInt);
			Ansatt   nyAnsatt = new Ansatt(fornavn, etternavn, LocalDate.parse(ansettelsesdato), stilling, lonnint, avd);
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

	/**
	 * Oppdatere en ansatt sin stilling og/eller lønn
	 */
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
						int    aid      = hentOgValiderIntId("ansatt");
						String stilling = hentOgValiderTekststreng(20, "stilling");
						Ansatt a        = ansattDAO.oppdaterStilling(aid, stilling);
						System.out.println("Oppdatert ansatt:\n" + a);
					}
					case 2 -> {
						System.out.println("Du har valgt å oppdatere Lønn for en ansatt");
						int aid = hentOgValiderIntId("ansatt");
						while (!validInput) {

							System.out.print("Skriv inn ny lønn til denne ansatte: ");
							String inputLonn = scanner.nextLine();
							try {
								int    inputLonnInt = Integer.parseInt(inputLonn);
								Ansatt a            = ansattDAO.oppdaterLonn(aid, inputLonnInt);
								System.out.println("Oppdatert ansatt:\n" + a);
								validInput = true;
							} catch (NumberFormatException e) {
								System.out.println("Ikke gyldig lønn, prøv igjen: " + inputLonn);
							}
						}
					}
					default -> System.out.println("Ingen funksjoner er registrert på menyvalg: " + valgInt);

				}
				System.out.println();
				return;
			} catch (NumberFormatException e) {
				System.out.println("Input " + valg + " inneholder andre ting enn tall, prøv igjen eller skriv '0' for å avslutte");
			}
		}

	}

	/**
	 * Utlisting av alle ansatte
	 */
	private static void skrivUtAlleAnsatte() {
		List<Ansatt> al = ansattDAO.finnAlleAnsatte();
		System.out.println("Liste over alle ansatte: ");
		for (Ansatt a : al) {
			System.out.println(a);
		}
		System.out.println();
	}

	/**
	 * Søke etter ansatt på brukernavn (initialer)
	 */
	private static void sokMedBrukernavn() {
		System.out.println("Skriv inn hele eller deler av brukernavnet til den ansatte du ønsker å finne");
		String input = hentOgValiderTekststreng(5, "brukernavn");
		Ansatt a     = ansattDAO.finnAnsattMedBrukernavn(input);
		if (a == null) {
			System.out.println("Fant ingen ansatte med dette brukernavnet");
		} else {
			System.out.println("Fant denne ansatte: " + a);
		}
	}

	/**
	 * Søke etter ansatt på ansatt-id
	 */
	private static void sokMedAnsattId() {
		int aid = hentOgValiderIntId("ansatt");
		System.out.println(ansattDAO.finnAnsattMedAnsattID(aid));
	}

}
