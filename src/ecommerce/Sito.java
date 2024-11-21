package ecommerce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sito {
	TreeSet<String> categorie = new TreeSet<>();
	int codiceProdotto = 100;
	int codiceUtente = 1;
	TreeMap<String, Prodotto> prodotti = new TreeMap<>();
	TreeMap<Integer, Utente> utenti = new TreeMap<>();

	
	public void nuovaCategoria(String nomeCategoria){
		categorie.add(nomeCategoria);
	}

	public Collection<String> elencoCategorie(){
		return categorie;
	}
	
	public String nuovoProdotto(String nomeCategoria, String nomeProdotto, String descrizione, double prezzo){
		if (!categorie.contains(nomeCategoria)) {
			nuovaCategoria(nomeCategoria);
		}
		Prodotto p = new Prodotto(codiceProdotto++, nomeCategoria, nomeProdotto, descrizione, prezzo);
		prodotti.put(p.getCodice(), p);
		return p.getCodice();
	}
	
	public Prodotto cercaProdotto(String stringaRicerca){
		for (Prodotto p : prodotti.values()) {
			if (p.getCodice().contains(stringaRicerca)) {
				return p;
			}
			else if (p.getDescrizione().contains(stringaRicerca)) {
				return p;
			}
			else if (p.getNome().contains(stringaRicerca)) {
				return p;
			}
		}
		return null;
	}
	
	public Collection<Prodotto> elencoProdottiPerNome(){
		return prodotti.values().stream()
								.sorted(Comparator.comparing(Prodotto::getNome))
								.toList();
	}

	public Collection<Prodotto> elencoProdottiPerPrezzo(){
		return prodotti.values().stream()
								.sorted(Comparator.comparing(Prodotto::getPrezzo))
								.toList();
	}
	
	public Collection<Prodotto> elencoProdottiPerNome(String nomeCategoria){
		return prodotti.values().stream()
								.filter(p -> p.getCategoria().equals(nomeCategoria))
								.sorted(Comparator.comparing(Prodotto::getNome))
								.toList();
	}

	public Collection<Prodotto> elencoProdottiPerPrezzo(String nomeCategoria){
		return prodotti.values().stream()
								.filter(p -> p.getCategoria().equals(nomeCategoria))
								.sorted(Comparator.comparing(Prodotto::getPrezzo))
								.toList();
	}
	
	public void nuovoUtente(String nome, String cognome, String email, String indirizzo) throws EccezioneUtenteGiaRegistrato{
		Utente u = new Utente(codiceUtente, nome, cognome, email, indirizzo);
		for (Utente ut : utenti.values()) {
			if (ut.getEmail().equals(email)) {
				throw new EccezioneUtenteGiaRegistrato();
			}
		}
		utenti.put(codiceUtente++, u);
	}
	
	public void nuovoUtente(String nome, String cognome, String email, String indirizzo, String username, String password) throws EccezioneUtenteGiaRegistrato{
		UtenteRegistrato u = new UtenteRegistrato(codiceUtente, nome, cognome, email, indirizzo);
		for (Utente ut : utenti.values()) {
			if (ut.getEmail().equals(email)) {
				throw new EccezioneUtenteGiaRegistrato();
			}
		}
		u.setUsername(username);
		u.setPassword(password);
		utenti.put(codiceUtente++, u);
	}
	
	public Utente cercaUtente(int codiceUtente) throws EccezioneUtenteInesistente{
		if (!utenti.containsKey(codiceUtente)) {
			throw new EccezioneUtenteInesistente();
		}
		return utenti.get(codiceUtente);
	}
	
	/**
	 Per conoscere il contenuto del carrello di un particolare utente è possibile utilizzare il metodo dettagliCarrello() della classe Sito,
	che riceve come parametro il codice dell’utente e restituisce una stringa che in ogni riga riporta, separati da spazio, 
	il codice prodotto, il prezzo e la quantita’ per ogni singolo prodotto selezionato. 
	Le righe sono ordinate per codice prodotto e, ad esclusione dell’ultima, sono terminate da \n.
	 */
	public String dettagliCarrello(int codiceUtente) throws EccezioneUtenteInesistente {
		if (!utenti.containsKey(codiceUtente)) {
			throw new EccezioneUtenteInesistente();
		}
		Utente u = utenti.get(codiceUtente);
	
		TreeMap<Prodotto, Integer> carrello = new TreeMap<>();
		for (Prodotto p : u.getCarrello()) {
			carrello.put(p, carrello.getOrDefault(p, 0) + 1);
		}
	
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Prodotto, Integer> entry : carrello.entrySet()) {
			sb.append(entry.getKey().getCodice())
			  .append(" ")
			  .append(entry.getKey().getPrezzo())
			  .append(" ")
			  .append(entry.getValue())
			  .append("\n");
		}
	
		// Rimuoviamo l'ultimo \n se presente
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
	
		return sb.toString();
	}
	
    public void leggiFile(String file) throws FileNotFoundException, IOException, EccezioneUtenteGiaRegistrato{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();

		while (line != null) {
			String[] tokens = line.split(";");
			if (tokens[0].equals("P")) {
				if (tokens.length == 5) {
					nuovoProdotto(tokens[1], tokens[2], tokens[3], Double.parseDouble(tokens[4]));
				}
			}
			if (tokens[0].equals("U")) {
				if (tokens.length > 5) {
					nuovoUtente(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
				}
				else if (tokens.length == 5) {
					nuovoUtente(tokens[1], tokens[2], tokens[3], tokens[4]);
				}
			}
			line = br.readLine();
		}
		br.close();
    }	
}
