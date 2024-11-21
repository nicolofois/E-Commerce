package ecommerce;

import java.util.LinkedList;

public class Utente {
	private int codice;
	private String nome;
	private String cognome;
	private String email;
	private String indirizzo;
	protected LinkedList<Prodotto> carrello;

	public Utente(int codice, String nome, String cognome, String email, String indirizzo) {
		this.codice = codice;
		this.nome = nome;
		this.cognome = cognome;		
		this.email = email;
		this.indirizzo = indirizzo;
		this.carrello = new LinkedList<>();
	}

    public int getCodice() {
		return this.codice;
	}

	public String getNome() {
		return this.nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public String getEmail() {
		return this.email;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void selezionaProdotto(Prodotto prodotto){
		carrello.add(prodotto);
	}
	
	public double paga(String data){
		double somma = 0.0;
		for (Prodotto p : this.carrello) {
			somma += p.getPrezzo();
		}
		carrello.clear();
		return somma;
	}

	public LinkedList<Prodotto> getCarrello() {
		return this.carrello;
	}

}
