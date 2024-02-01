package org.generation.italy.netfliz.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Attore implements Comparable<Attore> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	private String nome;
	@Column(nullable=false)
	private String cognome;
	@Column(nullable=false)
	private String nazionalita;
	@Column(nullable=false)
	private Integer annoDiNascita;
	
	@JsonBackReference
	@ManyToMany
	private List<Contenuto> elencoContenuti;	
//----------------------------------------------------------------------------------------------------------------	
	public Attore() {
		super();
	}
	
	public Attore(String nome, String cognome, String nazionalita, Integer annoDiNascita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.annoDiNascita = annoDiNascita;
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

	public Integer getAnnoDiNascita() {
		return annoDiNascita;
	}

	public List<Contenuto> getElencoContenuti() {
		return elencoContenuti;
	}
	
	
//----------------------------------------------------------------------------------------------------------------	

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public void setAnnoDiNascita(Integer annoDiNascita) {
		this.annoDiNascita = annoDiNascita;
	}

	public void setElencoContenuti(List<Contenuto> elencoContenuti) {
		this.elencoContenuti = elencoContenuti;
	}
//----------------------------------------------------------------------------------------------------------------	

	@Override
	public String toString() {
		return "Attore [nome = " + nome 
				+ ", cognome = " + cognome 
				+ ", nazionalita = " + nazionalita 
				+ ", annoDiNascita = "+ annoDiNascita + "]";
	}

	@Override
	public int compareTo(Attore attore) {		
		return (this.nome+this.cognome).compareTo(attore.getNome()+attore.getCognome());
	}
	
	
	
	
	
	
	
	
	
	
	
}
