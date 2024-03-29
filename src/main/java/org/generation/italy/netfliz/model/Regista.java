package org.generation.italy.netfliz.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Regista implements Comparable<Regista>{
	@Id		//è una chiave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
		private	int id;
	@Column(nullable = false, length = 50)
	private String nome;
	@Column(nullable = false, length = 50)
	private String cognome;
	@Column(nullable = false, length = 50)
	private String nazionalita;
	
	private String immagine;

	@JsonBackReference
	@OneToMany(mappedBy = "regista")		
	List<Contenuto> elencoContenuti;	
//----------------------------------------------------------------------------------------------------------------	
	
	public Regista() {
		super();
	}

	public Regista(int id, String nome, String cognome, String nazionalita) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
	}
//----------------------------------------------------------------------------------------------------------------	

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}
	public List<Contenuto> getElencoContenuti() {
		return elencoContenuti;
	}
	
		
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public void setElencoContenuti(List<Contenuto> elencoContenuti) {
		this.elencoContenuti = elencoContenuti;
	}
	

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	//----------------------------------------------------------------------------------------------------------------	
	@Override
	public String toString() {
		return "Regista [ id = " + id 
				+ ", nome = " + nome 
				+ ", cognome = " + cognome 
				+ ", nazionalita = " + nazionalita+"]";
	}

	@Override
	public int compareTo(Regista regista) {		
		return (this.nome+this.cognome).compareTo(regista.getNome()+regista.getCognome());
	}
	
	
}
	
	

