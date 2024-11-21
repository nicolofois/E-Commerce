package ecommerce;

public class Prodotto implements Comparable<Prodotto> {
	private int codice;
	private String categoria;
	private String nome;
	private String descrizione;
	private double prezzo;

	public Prodotto(int codice, String categoria, String nome, String descrizione, double prezzo) {
		this.codice = codice;
		this.categoria = categoria;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
	}

	
	@Override
	public int compareTo(Prodotto altro) {
		return this.getCodice().compareTo(altro.getCodice());
	}
	

	public String getCodice() {
		char m = categoria.charAt(0);
		String s = m + "00" + Integer.toString(this.codice);
		return s;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public double getPrezzo() {
		return this.prezzo;
	}
	
}


