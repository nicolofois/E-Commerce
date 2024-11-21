import ecommerce.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Esempio {

	public static void main(String[] args) throws EccezioneUtenteInesistente, EccezioneUtenteGiaRegistrato, FileNotFoundException, IOException {

		System.out.println("/****** R1. CATEGORIE E PRODOTTI ******/");
		
		Sito s = new Sito();
		
		s.nuovaCategoria("Elettronica");
		s.nuovaCategoria("Musica");
		s.nuovaCategoria("Libri");

		System.out.println("\nNuove categorie");

		System.out.println("\nCategorie:");
		LinkedList<String> categorie;
		categorie = new LinkedList<String>(s.elencoCategorie());
		for(String categoria : categorie)
			System.out.println(" "+categoria);

		System.out.println("\nNuovo prodotto");
		String c1 = s.nuovoProdotto("Libri", "Il dio del deserto", "L'ultimo lavoro di Wilbur Smith.", 21.00);
		
		System.out.println("\nCodice assegnato");
		System.out.println(" "+c1);
		
		System.out.println("\nCerca prodotto con quel codice");
		Prodotto p1 ;
		p1 = s.cercaProdotto(c1);
		System.out.println(" "+p1.getCodice()+"; "+p1.getNome()+"; "+p1.getDescrizione()+"; "+p1.getPrezzo());

		System.out.println("\nCerca prodotto che contiene \"smith\"");
		p1 = s.cercaProdotto(c1);
		System.out.println(" "+p1.getCodice()+"; "+p1.getNome()+"; "+p1.getDescrizione()+"; "+p1.getPrezzo());
		
		System.out.println("\nNuovi prodotti");

		s.nuovoProdotto("Libri", "I pilastri della terra", "Avventure nel medioevo.", 24.00);
		s.nuovoProdotto("Elettronica", "Macbook Air 13\"", "Il portatile leggero della Apple.", 1299.00);
		s.nuovoProdotto("Elettronica", "Macbook Air 11\"", "Il portatile superleggero della Apple.", 999.00);
		s.nuovoProdotto("Libri", "Il cammino di Santiago", "Il viaggio, il pellegrinaggio, secondo Coelho.", 22.00);
		
		System.out.println("\nElenco prodotti (tutti, per nome)");
		LinkedList<Prodotto> prodotti;
		prodotti = new LinkedList<Prodotto>(s.elencoProdottiPerNome());
		for(Prodotto p : prodotti)
			System.out.println(" "+p.getCodice()+"; "+p.getNome()+"; "+p.getDescrizione()+"; "+p.getPrezzo());
		
		System.out.println("\nElenco prodotti (categoria \"Libri\", per prezzo)");
		prodotti = new LinkedList<Prodotto>(s.elencoProdottiPerPrezzo("Libri"));
		for(Prodotto p : prodotti)
			System.out.println(" "+p.getCodice()+"; "+p.getNome()+"; "+p.getDescrizione()+"; "+p.getPrezzo());
		
		

		System.out.println("\n/****** R2. UTENTI ******/");
		
		System.out.println("\nNuovo utente");
		s.nuovoUtente("Mario", "Rossi", "mario.rossi@gmail.com", "Via Verdi 25, 10129, Torino");

		System.out.println("\nCerca utente 1");
		Utente u1 = s.cercaUtente(1);
		System.out.println(" "+u1.getCodice()+"; "+u1.getNome()+"; "+u1.getCognome()+"; "+u1.getEmail()+"; "+u1.getIndirizzo());

		System.out.println("\nNuovo utente registrato");
		s.nuovoUtente("Gianni", "Bianchi", "gianni.bianchi@aol.com", "Via Blu 34, 00196, Roma","gnn","bnc34196");

		UtenteRegistrato u2 = (UtenteRegistrato) s.cercaUtente(2);
		System.out.println(" "+u2.getCodice()+"; "+u2.getNome()+"; "+u2.getCognome()+"; "+u2.getEmail()+"; "+u2.getIndirizzo()+"; "+u2.getUsername()+"; "+u2.getPassword());
		
		try {
			System.out.println("\nLogin con username e password");
			u2.login("gnn","bnc34196");

		} catch (EccezioneLoginFallito e) {
			System.out.println(" Login fallito, credenziali errate");
		}

		if(u2.isLoggato()==true)
			System.out.println(" Utente loggato");
		else
			System.out.println(" Utente non loggato");
		
		
		
		System.out.println("\n/****** R3. ACQUISTI ******/");
		
		System.out.println("\nProdotti nel carrello: ");
		u2.selezionaProdotto(p1);
		u2.selezionaProdotto(p1);
		Prodotto p2 = s.cercaProdotto("superleggero");
		u2.selezionaProdotto(p2);
		System.out.println(""+s.dettagliCarrello(2));

		System.out.println("\nPagamento prodotti");
		double pagati = u2.paga("20150130");
		System.out.println(" "+pagati+" euro");
		
		Prodotto p3 = s.cercaProdotto("pilastri");
		u2.selezionaProdotto(p3);
		u2.paga("20150130");
		
		System.out.println("\nStorico acquisti:");
		System.out.println(" "+u2.storicoAcquisti());

		
		
		
		System.out.println("\n/****** R4. CARICAMENTO DA FILE ******/");

		System.out.println("\nCaricamento informazioni dal file input.txt");
		s.leggiFile("/Users/nicolofois/Desktop/OOP/Exams-OOP/exams/E-Commerce/input.txt");

		System.out.println("\nElenco prodotti (tutti, per prezzo)");
		prodotti = new LinkedList<Prodotto>(s.elencoProdottiPerPrezzo());
		for(Prodotto p : prodotti)
			System.out.println(" "+p.getCodice()+"; "+p.getNome()+"; "+p.getDescrizione()+"; "+p.getPrezzo());

	}

}
