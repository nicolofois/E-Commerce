package ecommerce;

import java.util.Map;
import java.util.TreeMap;

public class UtenteRegistrato extends Utente{
	private String username;
	private String password;
	private boolean loggato;
	private TreeMap<String,Double> storicoAcquisti;
	
	public UtenteRegistrato(int codice, String nome, String cognome, String email, String indirizzo){
		super(codice, nome, cognome, email, indirizzo);
		this.username = null;
		this.password = null;
		this.loggato = false;
		this.storicoAcquisti = new TreeMap<String,Double>();
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public void login(String username, String password) throws EccezioneLoginFallito{
		if (this.username.equals(username) && this.password.equals(password)) {
			this.loggato = true;
		}
	}

	public void logout(){
		this.loggato = false;
	}
	
	public boolean isLoggato() {
		return this.loggato;
	}
	
	@Override
	public double paga(String data){
		double somma = 0.0;
		for (Prodotto p : this.carrello) {
			somma += p.getPrezzo();
		}
		this.storicoAcquisti.put(data, somma);
		carrello.clear();
		return somma;
	}
	

	public String storicoAcquisti() {
	    StringBuilder sb = new StringBuilder();

	    storicoAcquisti.entrySet().stream()
	        .sorted(Map.Entry.<String, Double>comparingByKey()
	                .thenComparing(Map.Entry.comparingByValue()))
	        .forEach(entry -> {
	            sb.append(entry.getKey())
	              .append(" ")
	              .append(entry.getValue())
	              .append(";");
	        });

	    if (sb.length() > 0) {
	        sb.deleteCharAt(sb.length() - 1);
	    }

	    return sb.toString();
}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
